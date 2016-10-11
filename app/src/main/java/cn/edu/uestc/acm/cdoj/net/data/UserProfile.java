package cn.edu.uestc.acm.cdoj.net.data;

import org.json.JSONObject;

/**
 * Created by qwe on 16-9-29.
 */
public class UserProfile {
/*    "departmentId": 1,
            "email": "acm@uestc.edu.cn",
            "grade": 0,
            "motto": "这个人很屌，什么都没写。。。",
            "name": "管理员",
            "nickName": "administrator",
            "phone": "0800090000",
            "school": "UESTC",
            "sex": 0,
            "size": 0,
            "studentId": "2010013100008",
            "type": 1,
            "userName": "administrator"*/
    public int departmentId, grade, sex, size, type;
    public String email, motto, name, nickName, phone, school, studentId, userName;
    public UserProfile(JSONObject jsonObject) {
        JSONObject user = jsonObject.optJSONObject("user");
        departmentId = user.optInt("departmentId");
        grade = user.optInt("grade");
        sex = user.optInt("sex");
        size = user.optInt("size");
        type = user.optInt("type");
        email = user.optString("email");
        motto = user.optString("motto");
        name = user.optString("name");
        nickName = user.optString("nickName");
        phone = user.optString("phone");
        school = user.optString("school");
        studentId = user.optString("studentId");
        userName = user.optString("userName");
    }
}
