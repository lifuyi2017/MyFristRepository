package cn.lifuyi.modules.entity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色类
 */
public class Role implements Serializable{
    /**
     * 角色ID
     */
    private String id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 排序字段
     */
    private Integer order;
    /**
     * 角色所能操控的菜单
     */
    List<Menu> Menus =new ArrayList<Menu>();

    public Role() {
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

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public List<Menu> getMenus() {
        return Menus;
    }

    public void setMenus(List<Menu> menus) {
        Menus = menus;
    }
}
