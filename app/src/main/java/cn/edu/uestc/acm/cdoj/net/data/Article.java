package cn.edu.uestc.acm.cdoj.net.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import cn.edu.uestc.acm.cdoj.net.JsonUtils;

/**
 * Created by qwe on 16-8-15.
 */
public class Article {
    Map map;
    String contentString = "";
    public Article(JSONObject jsonObject){
        try {
            map = JsonUtils.getMapFromJson(jsonObject);
            contentString = jsonObject.getJSONObject("article").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String getContentString(){
        return contentString;
    }
    public Map getMap() {
        return map;
    }

}
