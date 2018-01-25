package cn.lifuyi.modules.dao;

import cn.lifuyi.modules.entity.User;

import java.util.List;

/**
 * Created by lifuyi on 2018/1/15 0015.
 */
public interface UserDao {
    /**
    *@Author:lifuyi
    *@Descripution:查询
    */
    List<User> findByParam(User user);

    int add(User user);

    int updateById(User user);

    List<User> findByParamVer(User user);
}
