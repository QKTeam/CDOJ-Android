package cn.edu.uestc.acm.cdoj.net.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.edu.uestc.acm.cdoj.net.JsonUtils;

/**
 * Created by qwe on 16-8-15.
 */
public class Problem {
    String contentString = "";
    Map<String, Object> map = new HashMap<>();
    public Problem(){
    }
    public Problem(JSONObject jsonObject){
        try {
            construct(this, jsonObject.getJSONObject("problem"));
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

    protected static Problem newInstance(JSONObject jsonObject) {
        Problem p = new Problem();
        construct(p, jsonObject);
        return p;
    }
    private static void construct(Problem p, JSONObject jsonObject){
        p.contentString = jsonObject.toString();
        p.map.put("problem", JsonUtils.getMapFromJson(jsonObject));
    }
}
