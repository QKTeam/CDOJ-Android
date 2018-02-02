package cn.edu.uestc.acm.cdoj.data.bean;

/**
 * Created by lagranmoon on 2018/2/1.
 * 首页公告
 */

public class Notice {

    /**
     * articleId : 整数,表示公告的ID
     * clicked : 整数，表示公告的点击量
     * content : 字符串，表示公告的内容
     * isVisible : 布尔值，表示公告是否公开可见
     * order : 顺序？不知道是表示什么
     * ownerEmail : 字符串，表示发布者的邮箱
     * ownerName : 字符串，表示发布者的用户名
     * time : Unix时间戳，表示 发布时间
     * title : 字符串，表示公告的标题
     * type : 类型？不知道表示什么？
     * userId : 整数，表示发布者账号ID
     * hasMore : 布尔值，表示文章是否折叠显示
     */

    public int articleId;
    public int clicked;
    public String content;
    public boolean isVisible;
    public int order;
    public String ownerEmail;
    public String ownerName;
    public long time;
    public String title;
    public int type;
    public int userId;
    public boolean hasMore;

}
