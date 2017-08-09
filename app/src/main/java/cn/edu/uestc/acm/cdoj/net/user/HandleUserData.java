package cn.edu.uestc.acm.cdoj.net.user;

import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.utils.DigestUtil;
import cn.edu.uestc.acm.cdoj.utils.JsonUtil;

/**
 * Created by lagranmoon on 2017/7/25.
 */

class HandleUserData {

    private static final String TAG = "HandleUserData";
    private Fragment fragment;

    HandleUserData(Fragment fragment) {
        this.fragment = fragment;
    }

    String handle_login_json() {
        TextInputEditText input_username = fragment.getView().findViewById(R.id.input_username);
        TextInputEditText input_password = fragment.getView().findViewById(R.id.input_password);
        String username = input_username.getText().toString();
        String password_encrypted = DigestUtil.sha1(input_password.getText().toString());
//        String password_encrypted = "4afddabb3a1f7e8acde1eeec25ea06a77826f369";
        String[] key = new String[]{"userName", "password"};
        Object[] value = new Object[]{username, password_encrypted};
        return JsonUtil.getJsonString(key, value);
    }

    String[] get_register_simple_info() {
        String[] register_info_simple_info = new String[2];
        TextInputEditText register_username = fragment.getView().findViewById(R.id.register_username);
        TextInputEditText register_password = fragment.getView().findViewById(R.id.register_password);
        register_info_simple_info[0] = register_username.getText().toString();
        register_info_simple_info[1] = register_password.getText().toString();
        return register_info_simple_info;
    }

    String get_register_json(String[] register_simple_info) throws Exception {
        String[] register_info = new String[14];
        String[] key = {"motto","nickName","departmentId", "grade" ,"size", "sex", "password", "passwordRepeat",  "phone", "school",  "studentId", "userName", "name", "email"};
        Object[] value = new Object[14];

        View view = fragment.getView();
        EditText register_name = view.findViewById(R.id.register_name);
        EditText register_nick_name = view.findViewById(R.id.register_nick_name);
        EditText register_email = view.findViewById(R.id.register_email);
        EditText register_motto = view.findViewById(R.id.register_motto);
        EditText register_school = view.findViewById(R.id.register_school);
        EditText register_student_id = view.findViewById(R.id.student_id);
        EditText register_phone = view.findViewById(R.id.register_phone);

        if (register_simple_info[2]==null){
            register_info[2] = "1";
        }else {
            register_info[2] = register_simple_info[2];
        }

        for (int i =3;i<6;i++){
            if (register_simple_info[i]==null){
                register_info[i] = "0";
            }else {
                register_info[i] = register_simple_info[i];
            }
        }

        register_info[0] = register_motto.getText().toString();
        register_info[1] = register_nick_name.getText().toString();
        register_info[6] = DigestUtil.sha1(register_simple_info[1]);
        register_info[7] = DigestUtil.sha1(register_simple_info[1]);
        register_info[8] = register_phone.getText().toString();
        register_info[9] = register_school.getText().toString();
        register_info[10] = register_student_id.getText().toString();
        register_info[11] = register_simple_info[0];
        register_info[12] = register_name.getText().toString();
        register_info[13] = register_email.getText().toString();

        for (String s:register_info){
            Log.d(TAG, "get_register_json: "+s);
        }

        for (int i = 0; i < register_info.length; i++) {
            if (TextUtils.isEmpty(register_info[i].trim())) {
                throw new Exception("null info");
            } else {
                value[i] = register_info[i];
            }
        }



        return JsonUtil.getJsonString(key, value);
    }


}
