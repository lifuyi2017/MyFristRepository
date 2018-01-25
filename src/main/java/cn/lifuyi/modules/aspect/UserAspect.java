package cn.lifuyi.modules.aspect;

import cn.lifuyi.modules.dao.MenuDao;
import cn.lifuyi.modules.dao.RoleDao;
import cn.lifuyi.modules.dao.RoleMenuDao;
import cn.lifuyi.modules.entity.Menu;
import cn.lifuyi.modules.entity.Role;
import cn.lifuyi.modules.entity.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lifuyi on 2018/1/15 0015.
 * Spring中的AOP（四）——在Advice方法中获取目标方法的参数:http://blog.csdn.net/caomiao2006/article/details/51287200
 */
@Aspect
@Component
public class UserAspect {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private MenuDao menuDao;
    /**
    *@Author:lifuyi
    *@Descripution:登录方法的环绕通知
    */
    @Around("execution(* cn.lifuyi.modules.service.UserServiceImpl.login(..))")
    public Object processLogin(ProceedingJoinPoint point) throws Throwable {
        //访问目标方法的参数：
        Object[] args=point.getArgs();
        User user= (User) point.proceed(args);
        //进行相关内容的封装
        Role roleInfo=new Role();
        roleInfo.setId(user.getRoleId());
        List<Role> role=roleDao.findByParam(roleInfo);
        user.setRole(role.get(0));
        List<Menu> menuList=menuDao.findMenuByRole(role.get(0));
        user.setMenuList(menuList);
        return user;
    }
    /**
    *@Author:lifuyi
    *@Descripution:查询所有用户的环绕通知
    */
    @Around("execution(* cn.lifuyi.modules.service.UserServiceImpl.findAll(..))")
    public Object afterFindALL(ProceedingJoinPoint point) throws Throwable {
        //执行方法：
        List<User> userList= (List<User>)point.proceed();
        //封装用户的角色属性
        Role role;
        for (User user:userList) {
            role=new Role();
            role.setId(user.getRoleId());
            List<Role> roleList=roleDao.findByParam(role);
            if(roleList!=null && roleList.size()!=0){
                user.setRole(roleList.get(0));
            }
        }
        //过滤管理员
        List<User> returnList=new ArrayList<>();
        for (User user:userList) {
            if (!(user.getRole().getName().equals("管理员"))){
                returnList.add(user);
            }
        }
        return returnList;
    }
}
