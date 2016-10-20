package cn.edu.uestc.acm.cdoj.net.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import cn.edu.uestc.acm.cdoj.net.JsonUtils;

/**
 * Created by lenovo on 2016/8/7.
 */
public class Contest {
    Map<String, Object> map;
    String contentString = "";
    public ArrayList<Problem> problemList = new ArrayList<>(15);
    public Contest(JSONObject jsonObject){
        try {
            map = JsonUtils.getMapFromJson(jsonObject);
            JSONObject contestObject = jsonObject.getJSONObject("contest");
            contentString = contestObject.toString();
            JSONArray plist = jsonObject.getJSONArray("problemList");
            for (int i = 0; i < plist.length(); i++) {
                problemList.add(Problem.newInstance(plist.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Problem> getProblemList(){
        return problemList;
    }
    public String getContentString(){
        return contentString;
    }
    public Map<String, Object> getMap() {
        return map;
    }
}
