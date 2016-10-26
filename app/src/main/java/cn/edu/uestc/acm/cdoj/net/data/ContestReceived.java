package cn.edu.uestc.acm.cdoj.net.data;

import java.util.ArrayList;

/**
 * Created by Grea on 2016/10/25.
 */

public class ContestReceived {
    private Contest contest;
    private ArrayList<Problem> problemList;
    private String result;
    private String error_msg;

    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }

    public ArrayList<Problem> getProblemList() {
        return problemList;
    }

    public void setProblemList(ArrayList<Problem> problemList) {
        this.problemList = problemList;
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
