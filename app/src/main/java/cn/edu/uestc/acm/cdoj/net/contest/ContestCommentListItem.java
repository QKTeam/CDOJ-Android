package cn.edu.uestc.acm.cdoj.net.contest;

/**
 * Created by 14779 on 2017-8-3.
 */

public class ContestCommentListItem {

    /**
     * articleId : 223687
     * clicked : 0
     * content : - -
     * isVisible : true
     * ownerEmail : 972023182@qq.com
     * ownerName : 2016060106021
     * time : 1494141299000
     * title : Comment
     */

    private int articleId;
    private int clicked;
    private String content;
    private boolean isVisible;
    private String ownerEmail;
    private String ownerName;
    private long time;
    private String title;

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
}
