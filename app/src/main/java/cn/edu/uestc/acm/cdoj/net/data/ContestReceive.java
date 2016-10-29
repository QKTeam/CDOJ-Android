package cn.edu.uestc.acm.cdoj.net.data;

import java.util.ArrayList;

/**
 * Created by Grea on 2016/10/25.
 */

public class ContestReceive {
    private ContestData contestData;
    private ArrayList<ProblemData> problemDataList;
    private String result;
    private String error_msg;

    public ContestData getContest() {
        return contestData;
    }

    public void setContest(ContestData contestData) {
        this.contestData = contestData;
    }

    public ArrayList<ProblemData> getProblemList() {
        return problemDataList;
    }

    public void setProblemList(ArrayList<ProblemData> problemDataList) {
        this.problemDataList = problemDataList;
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
