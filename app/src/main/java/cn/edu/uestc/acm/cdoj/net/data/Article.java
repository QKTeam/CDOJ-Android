package cn.edu.uestc.acm.cdoj.net.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by qwe on 16-8-15.
 */
public class Article {
    String contentString = "";
    public Article(JSONObject jsonObject){
        try {
            contentString = jsonObject.getJSONObject("article").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String getContentString(){
        return contentString;
    }

}
