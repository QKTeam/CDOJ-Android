package cn.edu.uestc.acm.cdoj.net.article;

import cn.edu.uestc.acm.cdoj.genaralData.ListReceived;
import cn.edu.uestc.acm.cdoj.net.ReceivedCallback;

/**
 * Created by 14779 on 2017-7-21.
 */

public interface ObtainArticle {
        void getArticleContent(int id, ReceivedCallback<Article> callback);

        void getArticleList(int page, ReceivedCallback<ListReceived<ArticleListItem>> callback);

        void searchArticle(int page, String orderFields, ReceivedCallback<ListReceived<ArticleListItem>> callback);

        void searchArticle(int page, String orderFields, int type, ReceivedCallback<ListReceived<ArticleListItem>> callback);

        void searchArticle(int page, String orderFields, int type, boolean orderAsc, ReceivedCallback<ListReceived<ArticleListItem>> callback);

}
