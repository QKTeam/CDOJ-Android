package cn.edu.uestc.acm.cdoj.net.article;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import cn.edu.uestc.acm.cdoj.genaralData.ListReceived;
import cn.edu.uestc.acm.cdoj.utils.JsonUtil;
import cn.edu.uestc.acm.cdoj.utils.Request;

/**
 * Created by 14779 on 2017-7-21.
 */

public class ArticleConnection {
    private static ArticleConnection instance = new ArticleConnection();

    private static final String TAG = "ArticleConnection";
    private String url = "http://acm.uestc.edu.cn";
    private String dataPath = "/article/data/";
    private String searchPath = "/article/search/";
    private String[] key = {"currentPage", "type", "orderFields", "orderAsc"};

    public static ArticleConnection getInstance(){
        return instance;
    }

    private String getContentJson(int articleId){
        byte[] dataRecieved =  Request.get(url, dataPath+articleId);

        if (dataRecieved != null){
            return  new String(dataRecieved);
        }
        return "";
    }

    private String getSearchJson(int currentPage, int type, String orderFields, boolean orderAsc){
        String request;
        Object[] value;
        value = new Object[]{currentPage, type, orderFields, orderAsc};
        request = JsonUtil.getJsonString(key, value);
        String searchRecieved = new String(Request.post(url, searchPath, request));
        if (searchRecieved != null){
            return searchRecieved;
        }
        return "";
    }

    private Article handleContentJson(String jsonString){
        ArticleReceived articleReceived = new ArticleReceived();
        articleReceived = JSON.parseObject(jsonString, ArticleReceived.class);
        return articleReceived.getArticle();
    }

    private ListReceived<ArticleListItem> handleSearchJson(String jsonString){
        ListReceived<ArticleListItem> listReceived = JSON.parseObject(jsonString, new TypeReference<ListReceived<ArticleListItem>>(){});
        return listReceived;
    }

    public ListReceived<ArticleListItem> getSearch(int currentPage, int type, String orderFields, boolean orderAsc){
        return handleSearchJson(getSearchJson(currentPage, type, orderFields, orderAsc));
    }

    public Article getContent(int id){
        return handleContentJson(getContentJson(id));
    }
}
