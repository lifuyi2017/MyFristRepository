package cn.lifuyi.modules.service;

import cn.lifuyi.modules.entity.User;
import cn.lifuyi.utils.Result;

import java.util.List;

/**
 * Created by lifuyi on 2018/1/15 0015.
 */
public interface UserService {
    User login(User user);

    List<User> findAll();

    boolean checkLoginName(User user);

    Result addUser(User user);

    Result active(String id);

    Result delete(String id);

    List<User> findByParam(User findUser);

    Result update(User user);

    List<User> findByParamVer(User user);
}
