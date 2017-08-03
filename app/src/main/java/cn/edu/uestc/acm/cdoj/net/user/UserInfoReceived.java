package cn.edu.uestc.acm.cdoj.net.user;

/**
 * Created by lagranmoon on 2017/7/25.
 */

public class UserInfoReceived {

    /**
     * result : success
     * user : {"departmentId":3,"email":"tanliqun1999@outlook.com","grade":3,"motto":"","name":"檀利群","nickName":"檀利群","phone":"18156907253","school":"UESTC","sex":0,"size":2,"studentId":"2016100104001","type":0,"userName":"2016100104001"}
     */

    private String result;
    private UserInfo userInfo;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
