package cn.lifuyi.modules.dao;

import cn.lifuyi.modules.entity.Menu;
import cn.lifuyi.modules.entity.Role;

import java.util.List;

/**
 * Created by lifuyi on 2018/1/15 0015.
 */
public interface MenuDao {

    List<Menu> findMenuByRole(Role role);
}
