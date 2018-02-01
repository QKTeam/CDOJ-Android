package cn.edu.uestc.acm.cdoj.data.bean.problem;

import cn.edu.uestc.acm.cdoj.data.bean.base.Problem;
import cn.edu.uestc.acm.cdoj.data.bean.base.ResposeSuccess;

/**
 * Created by lagranmoon on 2018/2/1.
 * 题目的详细信息
 */

public class ProblemDetailResponse extends ResposeSuccess{
    private Problem problem;

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }
}
