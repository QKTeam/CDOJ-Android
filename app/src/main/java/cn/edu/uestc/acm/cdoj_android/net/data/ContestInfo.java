package cn.edu.uestc.acm.cdoj_android.net.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lenovo on 2016/8/7.
 */
public class ContestInfo {
    public int contestId, length, time, type;
    public boolean isVisible;
    public String status, title, typeName;
    public ContestInfo(JSONObject jsonObject){
        if (jsonObject == null) {
            return;
        }
        try {
            length = jsonObject.getInt("length");
            time = jsonObject.getInt("time");
            type = jsonObject.getInt("type");
            contestId = jsonObject.getInt("contestId");

            isVisible = jsonObject.getBoolean("isVisible");

            status = jsonObject.getString("status");
            title = jsonObject.getString("title");
            typeName = jsonObject.getString("typeName");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
