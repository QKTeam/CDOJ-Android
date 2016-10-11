package cn.edu.uestc.acm.cdoj.net.data;

import org.json.JSONObject;

/**
 * Created by qwe on 16-8-19.
 */
public class UserTypeAheadItem {
/*    "email": "acm@uestc.edu.cn",
            "nickName": "administrator",
            "userId": 1,
            "userName": "administrator"*/
    public int userId;
    public String email, nickName, userName;
    public UserTypeAheadItem(JSONObject jsonObject) {
        JSONObject user = jsonObject.optJSONObject("user");
        email = user.optString("email");
        nickName = user.optString("nickName");
        userId = user.optInt("userId");
        userName = user.optString("userName");
    }
}
