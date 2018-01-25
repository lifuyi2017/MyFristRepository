package cn.lifuyi.modules.entity;


import java.util.Date;
import java.util.List;

/**
 * 用户实体类
 */
public class User {
    /**
     * 角色
     */
    private Role role;
    /**
     * 用户ID
     */
    private String id;
    /**
     * 用户真实姓名
     */
    private String realName;
    /**
     * 用户登录名
     */
    private String loginName;
    /**
     * 登录密码
     */
    private String password;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 登录时间
     */
    private Date loginTime;
    /**
     * 状态：0停用1正常，默认1正常
     */
    private Integer status;
    /**
     * 注册时间
     */
    private Date registTime;
    /**
     * 角色Id
     */
    private String roleId;
    /**
     * 菜单
     */
    private List<Menu> menuList;

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public User() {
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getRegistTime() {
        return registTime;
    }

    public void setRegistTime(Date registTime) {
        this.registTime = registTime;
    }
}
