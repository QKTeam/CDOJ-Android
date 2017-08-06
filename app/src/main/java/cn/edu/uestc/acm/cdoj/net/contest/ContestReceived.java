package cn.edu.uestc.acm.cdoj.net.contest;

import java.util.List;

import cn.edu.uestc.acm.cdoj.genaralData.ContentReceived;
import cn.edu.uestc.acm.cdoj.net.contest.problem.ContestProblem;

/**
 * Created by 14779 on 2017-7-24.
 */

public class ContestReceived extends ContentReceived{
    private Contest contest;
    private List<ContestProblem> problemList;

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
}
