package cn.edu.uestc.acm.cdoj.data.bean.base;

/**
 * Created by lagranmoon on 2018/2/1.
 * 请求失败时返回的信息
 */

public class ResponseError {

    /**
     * error_msg : No such article.
     * result : error
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
