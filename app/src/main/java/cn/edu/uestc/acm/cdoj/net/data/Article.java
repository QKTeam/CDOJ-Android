package cn.edu.uestc.acm.cdoj.net.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by qwe on 16-8-15.
 */
public class Article {
    boolean result = false;
    String contentString = "";
    public Article(String json){
        if (json == null) {
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(json);
            result = jsonObject.optString("result", "fail").equals("success");
            contentString = jsonObject.getJSONObject("article").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String getContentString(){
        return contentString;
    }

}
