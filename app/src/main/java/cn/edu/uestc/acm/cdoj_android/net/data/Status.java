package cn.edu.uestc.acm.cdoj_android.net.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by qwe on 16-8-21.
 */
public class Status {
    public int caseNumber, length, memoryCost, problemId, returnTypeId, statusId, time, timeCost;
    public String email, language,  name, nickName, returnType, userName;

    public Status(JSONObject jsonObject) {
        try {
            caseNumber = jsonObject.getInt("caseNumber");
            length = jsonObject.getInt("length");
            memoryCost = jsonObject.getInt("memoryCost");
            problemId = jsonObject.getInt("problemId");
            returnTypeId = jsonObject.getInt("returnTypeId");
            statusId = jsonObject.getInt("statusId");
            time = jsonObject.getInt("time");
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
}
