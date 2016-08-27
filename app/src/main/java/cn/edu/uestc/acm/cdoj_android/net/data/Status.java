package cn.edu.uestc.acm.cdoj_android.net.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

/**
 * Created by qwe on 16-8-21.
 */
public class Status {
    public final static int LANGUAGE_C = 1, LANGUAGE_CPP = 2, LANGUAGE_JAVA = 3;

    public int caseNumber, length, memoryCost, problemId, returnTypeId, timeCost;
    public long statusId , time;
    public String email, language,  name, nickName, returnType, userName;
    public String timeString;

    public Status(JSONObject jsonObject) {
        try {
            caseNumber = jsonObject.getInt("caseNumber");
            length = jsonObject.getInt("length");
            memoryCost = jsonObject.getInt("memoryCost");
            problemId = jsonObject.getInt("problemId");
            returnTypeId = jsonObject.getInt("returnTypeId");
            statusId = jsonObject.getLong("statusId");
            time = jsonObject.getLong("time");
            timeString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
            timeCost = jsonObject.getInt("timeCost");

            email = jsonObject.getString("email");
            language = jsonObject.getString("language");
            name = jsonObject.getString("name");
            nickName = jsonObject.getString("nickName");
            returnType = jsonObject.getString("returnType");
            userName = jsonObject.getString("userName");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static String getCode(String json){
        try {
            return new JSONObject(json).optString("code", null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
