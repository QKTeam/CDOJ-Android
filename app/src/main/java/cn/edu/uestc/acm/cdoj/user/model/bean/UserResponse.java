package cn.edu.uestc.acm.cdoj.user.model.bean;

/**
 * Created by lagranmoon on 2017/7/25.
 */

public class UserResponse {


    /**
     * result : success
     * user : {"departmentId":3,"email":"tanliqun1999@outlook.com","grade":3,"motto":"","name":"檀利群","nickName":"檀利群","phone":"18156907253","school":"UESTC","sex":0,"size":2,"studentId":"2016100104001","type":0,"userName":"2016100104001"}
     */

    private String result;
    private UserBean user;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }
    public static class UserBean extends UserInfo {
    }
}
