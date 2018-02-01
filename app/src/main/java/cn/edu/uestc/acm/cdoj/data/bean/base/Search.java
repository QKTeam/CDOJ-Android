package cn.edu.uestc.acm.cdoj.data.bean.base;

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

    private int currentPage;
    private int type;
    private String orderFields;
    private String orderAsc;
    private String userName;
    private String keyword;
    private String startId;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getStartId() {
        return startId;
    }

    public void setStartId(String startId) {
        this.startId = startId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getOrderFields() {
        return orderFields;
    }

    public void setOrderFields(String orderFields) {
        this.orderFields = orderFields;
    }

    public String getOrderAsc() {
        return orderAsc;
    }

    public void setOrderAsc(String orderAsc) {
        this.orderAsc = orderAsc;
    }
}
