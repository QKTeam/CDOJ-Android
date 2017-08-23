package cn.edu.uestc.acm.cdoj.user.model.bean;

/**
 * Created by lagranmoon on 2017/8/8.
 */

public class RegisterSuccessResponse {

    /**
     * email : test@test.com
     * result : success
     * type : 0
     * userName : test12
     */

    private String email;
    private String result;
    private int type;
    private String userName;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
