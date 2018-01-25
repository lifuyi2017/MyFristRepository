package cn.lifuyi.modules.entity;

/**
 * Created by lifuyi on 2018/1/16 0016.
 */
public class LoginNameCheck {
    private String oldLoginName;
    private String loginName;

    public LoginNameCheck() {
    }

    public String getOldLoginName() {
        return oldLoginName;
    }

    public void setOldLoginName(String oldLoginName) {
        this.oldLoginName = oldLoginName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}
