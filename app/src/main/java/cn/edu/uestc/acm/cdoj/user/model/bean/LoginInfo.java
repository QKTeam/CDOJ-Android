package cn.edu.uestc.acm.cdoj.user.model.bean;

import cn.edu.uestc.acm.cdoj.utils.DigestUtil;

/**
 * Created by lagranyan on 2017/12/6.
 * 登录所需要的用户名 密码
 * 密码经过sha1加密后传输
 */

public class LoginInfo {

    /**
     * userName : 2016100104001
     * password : 加密前的密码
     * getPassword返回的是加密之后的密码
     */

    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = DigestUtil.sha1(password);
    }
}
