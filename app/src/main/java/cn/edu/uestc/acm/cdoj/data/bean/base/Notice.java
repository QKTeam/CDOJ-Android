package cn.edu.uestc.acm.cdoj.data.bean.base;

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

    private int articleId;
    private int clicked;
    private String content;
    private boolean isVisible;
    private int order;
    private String ownerEmail;
    private String ownerName;
    private long time;
    private String title;
    private int type;
    private int userId;
    private boolean hasMore;

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public int getClicked() {
        return clicked;
    }

    public void setClicked(int clicked) {
        this.clicked = clicked;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isIsVisible() {
        return isVisible;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }
}
