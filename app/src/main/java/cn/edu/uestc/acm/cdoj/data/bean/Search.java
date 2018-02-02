package cn.edu.uestc.acm.cdoj.data.bean;

/**
 * Created by lagranmoon on 2018/2/1.
 * 搜索的一些参数
 */

public class Search {

    /**
     * currentPage : 1
     * type : 0
     * orderFields : 排序的标准
     * orderAsc : 是否为正序排列
     * userName : "administrator"
     * keyword : 题目的关键词
     * startId : 搜题目用的，具体用途未知
     */

    public int currentPage;
    public int type;
    public String orderFields;
    public String orderAsc;
    public String userName;
    public String keyword;
    public String startId;
}
