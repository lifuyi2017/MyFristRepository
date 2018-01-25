package cn.lifuyi.modules.service;

import cn.lifuyi.modules.dao.UserDao;
import cn.lifuyi.modules.entity.User;
import cn.lifuyi.utils.MD5Util;
import cn.lifuyi.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lifuyi on 2018/1/15 0015.
 */
@Service("userServiceImpl")
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;
    /**
    *@Author:lifuyi
    *@Descripution:登录
    */
    @Override
    public User login(User user) {
        user.setPassword(MD5Util.GetMD5Code(user.getPassword()));
        List<User> userList=userDao.findByParam(user);
        if (userList.size()==1){
            return userList.get(0);
        }
        return null;
    }
    /**
    *@Author:lifuyi
    *@Descripution:查询所有用户
    */
    @Override
    @Cacheable(value = "DEFAULT_CACHE",key = "0")
    public List<User> findAll() {
        List<User> userList=userDao.findByParam(new User());
        return userList;
    }
    /**
    *@Author:lifuyi
    *@Descripution:校验用户名是否存在
    */
    @Override
    public boolean checkLoginName(User user) {
        List<User> userList=userDao.findByParam(user);
        return userList.size()>0;
    }
    /**
    *@Author:lifuyi
    *@Descripution:添加用户
    */
    @Override
    @CacheEvict(value = "DEFAULT_CACHE",key = "0")
    public Result addUser(User user) {
        int rs=userDao.add(user);
        Result result=new Result();
        if(rs>0){
            //添加成功
            result.setState(true);
            result.setMessage("添加成功");
            return result;
        }
        result.setState(false);
        result.setMessage("添加失败");
        return result;
    }
    /**
    *@Author:lifuyi
    *@Descripution:激活用户
     * @param id
    */
    @Override
    @CacheEvict(value = "DEFAULT_CACHE",key = "0")
    public Result active(String id) {
        User user=new User();
        user.setId(id);
        user.setStatus(1);
        int rs=userDao.updateById(user);
        Result result=new Result();
        if(rs>0){
            //激活成功
            result.setState(true);
            result.setMessage("激活成功");
            return result;
        }
        result.setState(false);
        result.setMessage("激活失败");
        return result;
    }
    /**
    *@Author:lifuyi
    *@Descripution:删除用户
    */
    @Override
    @CacheEvict(value = "DEFAULT_CACHE",key = "0")
    public Result delete(String id) {
        User user=new User();
        user.setId(id);
        user.setStatus(0);
        int rs=userDao.updateById(user);
        Result result=new Result();
        if(rs>0){
            //删除成功
            result.setState(true);
            result.setMessage("删除成功");
            return result;
        }
        result.setState(false);
        result.setMessage("删除失败");
        return result;
    }
    /**
    *@Author:lifuyi
    *@Descripution:通过参数查询用户
    */
    @Override
    public List<User> findByParam(User findUser) {
        List<User> userList=userDao.findByParam(findUser);
        return userList;
    }
    /**
    *@Author:lifuyi
    *@Descripution:修改用户
    */
    @Override
    @CacheEvict(value = "DEFAULT_CACHE",key = "0")
    public Result update(User user) {
        int rs=userDao.updateById(user);
        Result result=new Result();
        if(rs>0){
            //修改成功
            result.setState(true);
            result.setMessage("修改成功");
            return result;
        }
        result.setState(false);
        result.setMessage("修改失败");
        return result;
    }
    /**
    *@Author:lifuyi
    *@Descripution:查询用户，支持模糊查询
    */
    @Override
    public List<User> findByParamVer(User user) {
        List<User> userList=userDao.findByParamVer(user);
        return userList;
    }
}
