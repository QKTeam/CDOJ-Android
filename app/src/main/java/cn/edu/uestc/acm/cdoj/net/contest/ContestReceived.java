package cn.edu.uestc.acm.cdoj.net.contest;

import java.util.List;

/**
 * Created by 14779 on 2017-7-24.
 */

public class ContestReceived {
    private Contest contest;
    private List<ContestProblem> problemList;
    private String result;

    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }

    public List<ContestProblem> getProblemList() {
        return problemList;
    }

    public void setProblemList(List<ContestProblem> problemList) {
        this.problemList = problemList;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
