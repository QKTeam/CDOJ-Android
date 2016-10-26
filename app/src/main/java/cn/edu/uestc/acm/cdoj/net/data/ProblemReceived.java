package cn.edu.uestc.acm.cdoj.net.data;

/**
 * Created by Grea on 2016/10/25.
 */

public class ProblemReceived {
    private Problem problem;
    private String result;
    private String error_msg;

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }
}
