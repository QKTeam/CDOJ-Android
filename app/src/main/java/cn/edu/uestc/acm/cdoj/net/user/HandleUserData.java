package cn.edu.uestc.acm.cdoj.net.user;

import android.app.Activity;
import android.support.design.widget.TextInputEditText;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.utils.DigestUtil;
import cn.edu.uestc.acm.cdoj.utils.JsonUtil;

/**
 * Created by lagranmoon on 2017/7/25.
 */

public class HandleUserData {
    private Activity activity;

    public HandleUserData(Activity activity) {
        this.activity = activity;
    }

    public  String handle_login_json() {
        TextInputEditText input_username = activity.findViewById(R.id.input_username);
        TextInputEditText input_password = activity.findViewById(R.id.input_password);
        String username = input_username.getText().toString();
//        String password_encrypted = DigestUtil.sha1(input_password.getText().toString());
        String password_encrypted = "4afddabb3a1f7e8acde1eeec25ea06a77826f369";
        String[] key = new String[]{"userName", "password"};
        Object[] value = new Object[]{username, password_encrypted};
        return JsonUtil.getJsonString(key, value);
    }


}
