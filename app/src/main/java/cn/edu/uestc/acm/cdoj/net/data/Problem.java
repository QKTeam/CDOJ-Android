package cn.edu.uestc.acm.cdoj.net.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by qwe on 16-8-15.
 */
public class Problem {
    public int problemId;
    String contentString = "";
    public Problem(){
    }
    public Problem(JSONObject jsonObject){
        try {
            contentString = jsonObject.getJSONObject("problem").toString();
            problemId = jsonObject.optInt("problemId");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String getContentString(){
        return contentString;
    }
    public static Problem newInstance(JSONObject jsonObject) {
        Problem p = new Problem();
        p.contentString = jsonObject.toString();
        p.problemId = jsonObject.optInt("problemId");
        return p;
    }
}
