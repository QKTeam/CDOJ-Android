package cn.edu.uestc.acm.cdoj.net.data;

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
            caseNumber = jsonObject.optInt("caseNumber");
            length = jsonObject.optInt("length");
            memoryCost = jsonObject.optInt("memoryCost");
            problemId = jsonObject.optInt("problemId");
            returnTypeId = jsonObject.optInt("returnTypeId");
            statusId = jsonObject.optLong("statusId");
            time = jsonObject.optLong("time");
            timeString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
            timeCost = jsonObject.optInt("timeCost");

            email = jsonObject.optString("email");
            language = jsonObject.optString("language");
            name = jsonObject.optString("name");
            nickName = jsonObject.optString("nickName");
            returnType = jsonObject.optString("returnType");
            userName = jsonObject.optString("userName");
        } catch (Exception e) {
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
