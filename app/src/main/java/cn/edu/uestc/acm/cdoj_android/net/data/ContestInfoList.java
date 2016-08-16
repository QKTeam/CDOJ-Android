package cn.edu.uestc.acm.cdoj_android.net.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by lenovo on 2016/8/8.
 */
public class ContestInfoList {
    public boolean result;
    public ArrayList<ContestInfo> list = new ArrayList<>(20);
    PageInfo pageInfo;
    public ContestInfoList(String json){
        if (json == null) {
            return;
        }
        try {
            JSONObject msg = new JSONObject(json);
            result = msg.getString("result").equals("success");
            pageInfo = new PageInfo(msg.getJSONObject("pageInfo"));
            JSONArray list0 = msg.getJSONArray("list");
            for (int i = 0; i < list0.length(); i++) {
                list.add(new ContestInfo(list0.getJSONObject(i)));
            }
        } catch (JSONException e) {
            result = false;
            e.printStackTrace();
        }
    }
    public ArrayList<ContestInfo> getContestInfo(){
        return list;
    }
}
