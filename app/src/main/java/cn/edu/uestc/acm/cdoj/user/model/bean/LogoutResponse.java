package cn.edu.uestc.acm.cdoj.user.model.bean;

/**
 * Created by lagranyan on 2017/12/6.
 * 退出登录之后返回的状态信息
 */

public class LogoutResponse {

    /**
     * error_msg : Please login first.
     * result : error
     * 退出登录成功只返回result
     */

    private String error_msg;
    private String result;

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
