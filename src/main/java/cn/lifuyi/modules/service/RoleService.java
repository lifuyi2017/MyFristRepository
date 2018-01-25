package cn.lifuyi.modules.service;

import cn.lifuyi.modules.entity.Role;

import java.util.List;

/**
 * Created by lifuyi on 2018/1/16 0016.
 */
public interface RoleService {
    Role findByName(String str);

    List<Role> findAll();
}
