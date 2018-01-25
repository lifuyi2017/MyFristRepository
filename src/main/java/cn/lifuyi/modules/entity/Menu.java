package cn.lifuyi.modules.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单实体类
 */
public class Menu implements Serializable{
    /**
     * 菜单ID
     */
    private String id;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单地址
     */
    private String url;

    private List<User> userList;

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public Menu() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
