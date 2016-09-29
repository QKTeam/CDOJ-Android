package cn.edu.uestc.acm.cdoj.net.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

import cn.edu.uestc.acm.cdoj.net.DateTemp;

/**
 * Created by lenovo on 2016/8/7.
 */
public class ContestInfo {
    public final static int TYPE_PUBLIC = 0, TYPE_PRIVATE = 1, TYPE_DIY = 2, TYPE_INVITED = 3, TYPE_ONSITE = 5;
    public int contestId, type;
    public long time, length;
    public boolean isVisible;
    public String status, title, typeName, timeString, lengthString;
    public ContestInfo(JSONObject jsonObject){
        if (jsonObject == null) {
            return;
        }
        try {
            length = jsonObject.getLong("length");
//            lengthString = new SimpleDateFormat("HH:mm:ss").format(length);
            lengthString = DateTemp.format(length);
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
