package cn.edu.uestc.acm.cdoj_android.net.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

/**
 * Created by lenovo on 2016/8/7.
 */
public class ContestInfo {
    public int contestId, type;
    public long time, length;
    public boolean isVisible;
    public String status, title, typeName, timeString, lenthString;
    public ContestInfo(JSONObject jsonObject){
        if (jsonObject == null) {
            return;
        }
        try {
            length = jsonObject.getLong("length");
            lenthString = new SimpleDateFormat("HH:mm:ss").format(length);
            time = jsonObject.getLong("time");
            timeString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
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
