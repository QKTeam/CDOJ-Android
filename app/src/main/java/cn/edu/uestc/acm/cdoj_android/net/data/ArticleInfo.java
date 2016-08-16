package cn.edu.uestc.acm.cdoj_android.net.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by qwe on 16-8-10.
 */
public class ArticleInfo {
    public int articleId, clicked, time;
    public boolean hasMore, isVisible;
    public String content, ownerEmail, ownerName, title;
    public ArticleInfo(JSONObject jsonObject){
        if (jsonObject == null) {
            return;
        }
        try {
            articleId = jsonObject.getInt("articleId");
            time = jsonObject.getInt("time");
            clicked = jsonObject.getInt("clicked");

            hasMore = jsonObject.getBoolean("hasMore");
            isVisible = jsonObject.getBoolean("isVisible");

            content = jsonObject.getString("content");
            title = jsonObject.getString("title");
            ownerEmail = jsonObject.getString("ownerEmail");
            ownerName = jsonObject.getString("ownerName");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

