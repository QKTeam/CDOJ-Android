package cn.edu.uestc.acm.cdoj.data.bean.problem;

import java.util.List;

import cn.edu.uestc.acm.cdoj.data.bean.base.Problem;
import cn.edu.uestc.acm.cdoj.data.bean.base.ResposeSuccess;

/**
 * Created by lagranmoon on 2018/2/1.
 * 根据给定条件获取题目列表
 */

public class ProblemListResponse extends ResposeSuccess{

    /**
     * list : [{"difficulty":1,"isSpj":false,"isVisible":true,"problemId":1,"solved":2281,"source":"Classic Problem","title":"A+B Problem","tried":2365},{"difficulty":4,"isSpj":false,"isVisible":true,"problemId":2,"solved":2,"source":"The 11th UESTC Programming Contest Final","title":"All You Need is to Wait","tried":33}]
     * pageInfo : {"countPerPage":20,"currentPage":1,"displayDistance":2,"totalItems":1185,"totalPages":60}
     * result : success
     */

    private List<Problem> list;

    public List<Problem> getList() {
        return list;
    }

    public void setList(List<Problem> list) {
        this.list = list;
    }

}
