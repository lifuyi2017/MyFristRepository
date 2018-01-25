package cn.lifuyi.modules.service;

import cn.lifuyi.modules.dao.RoleDao;
import cn.lifuyi.modules.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lifuyi on 2018/1/16 0016.
 */
@Service("roleServiceImpl")
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    /**
     * @Author:lifuyi
     * @Descripution:名称查询
     */
    @Override
    public Role findByName(String str) {
        Role roleInfo = new Role();
        roleInfo.setName(str);
        List<Role> roleList = roleDao.findByParam(roleInfo);
        if(roleList.size()>0 && roleList!=null){
            return roleList.get(0);
        }
        return null;
    }
    /**
    *@Author:lifuyi
    *@Descripution:查询所有角色
    */
    @Override
    public List<Role> findAll() {
        List<Role> roleList = roleDao.findByParam(new Role());
        return roleList;
    }
}
