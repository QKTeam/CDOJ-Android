package cn.edu.uestc.acm.cdoj.net.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

/**
 * Created by qwe on 16-8-10.
 */
public class ArticleInfo {
    public int articleId, clicked;
    public long time;
    public boolean hasMore, isVisible;
    public String content, ownerEmail, ownerName, title, timeString;
    public ArticleInfo(JSONObject jsonObject){
        if (jsonObject == null) {
            return;
        }
        try {
            articleId = jsonObject.getInt("articleId");
            time = jsonObject.getLong("time");
            timeString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
            clicked = jsonObject.getInt("clicked");

            isVisible = jsonObject.getBoolean("isVisible");
            hasMore = jsonObject.optBoolean("hasMore", false);

            content = jsonObject.getString("content");
            title = jsonObject.getString("title");
            ownerEmail = jsonObject.getString("ownerEmail");
            ownerName = jsonObject.getString("ownerName");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

