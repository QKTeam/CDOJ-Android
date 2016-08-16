package cn.edu.uestc.acm.cdoj_android.net.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by qwe on 16-8-15.
 */
public class Problem {
    boolean result;
    String contentString = "";
    public Problem(){

    }
    public Problem(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            result = jsonObject.optString("result", "fail").equals("success");
            contentString = jsonObject.getJSONObject("problem").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String getContentString(){
        return contentString;
    }
    public static Problem newInstance(JSONObject jsonObject) {
        Problem p = new Problem();
        p.result = true;
        p.contentString = jsonObject.toString();
        return p;
    }
}
