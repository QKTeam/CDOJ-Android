package cn.edu.uestc.acm.cdoj.net.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import cn.edu.uestc.acm.cdoj.net.JsonUtils;

/**
 * Created by qwe on 16-8-10.
 */
public class InfoList{
    public ArrayList<Map<String, Object>> list;
    public PageInfo pageInfo;
    public InfoList(JSONObject msg) {
        try {
            pageInfo = new PageInfo(msg.getJSONObject("pageInfo"));
            list = JsonUtils.getMapListFromJson(msg.getJSONArray("list"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Map<String, Object>> getInfoList() {
        return list;
    }
}
