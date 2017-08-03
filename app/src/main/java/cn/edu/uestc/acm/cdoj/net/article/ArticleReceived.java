package cn.edu.uestc.acm.cdoj.net.article;

import cn.edu.uestc.acm.cdoj.genaralData.ContentReceived;

/**
 * Created by 14779 on 2017-7-21.
 */

public class ArticleReceived extends ContentReceived{
    private Article article;

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
