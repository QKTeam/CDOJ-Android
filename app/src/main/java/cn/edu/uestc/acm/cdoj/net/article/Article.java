package cn.edu.uestc.acm.cdoj.net.article;

/**
 * Created by 14779 on 2017-7-21.
 */

public class Article extends ArticleListItem {
    private int order;
    private int type;
    private int userId;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
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
}
