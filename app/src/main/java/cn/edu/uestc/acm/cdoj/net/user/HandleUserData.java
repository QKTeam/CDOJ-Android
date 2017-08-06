package cn.edu.uestc.acm.cdoj.net.user;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.utils.DigestUtil;
import cn.edu.uestc.acm.cdoj.utils.JsonUtil;

/**
 * Created by lagranmoon on 2017/7/25.
 */

class HandleUserData {
    private Fragment fragment;

    HandleUserData(Fragment fragment) {
        this.fragment = fragment;
    }

    String handle_login_json() {
        TextInputEditText input_username = fragment.getView().findViewById(R.id.input_username);
        TextInputEditText input_password = fragment.getView().findViewById(R.id.input_password);
        String username = input_username.getText().toString();
//        String password_encrypted = DigestUtil.sha1(input_password.getText().toString());
        String password_encrypted = "4afddabb3a1f7e8acde1eeec25ea06a77826f369";
        String[] key = new String[]{"userName", "password"};
        Object[] value = new Object[]{username, password_encrypted};
        return JsonUtil.getJsonString(key, value);
    }

    String[] get_register_simple_info() {
        String[] register_info_simple_info = new String[2];
        TextInputEditText register_username = fragment.getView().findViewById(R.id.register_name);
        TextInputEditText register_password = fragment.getView().findViewById(R.id.register_password);
        register_info_simple_info[0] = register_username.getText().toString();
        register_info_simple_info[1] = register_password.getText().toString();
        return register_info_simple_info;
    }

    String get_register_json(String[] register_simple_info) {
        String[] register_info = new String[14];
        String[] key = {"departmentId", "grade", "motto", "password", "passwordRepeat", "nickName", "phone", "school", "sex", "size", "studentId", "userName", "name", "email"};
        Object[] value = new Object[14];

        View view = fragment.getView();
        EditText register_name = view.findViewById(R.id.register_name);
        EditText register_nick_name = view.findViewById(R.id.register_nick_name);
        EditText register_email = view.findViewById(R.id.register_email);
        EditText register_motto = view.findViewById(R.id.register_motto);
        EditText register_school = view.findViewById(R.id.register_school);
        EditText register_student_id = view.findViewById(R.id.student_id);
        EditText register_phone = view.findViewById(R.id.register_phone);
        RadioGroup register_gender = view.findViewById(R.id.register_gender);
        Spinner register_department = view.findViewById(R.id.spinner_department);
        Spinner register_grade = view.findViewById(R.id.spinner_grade);
        Spinner register_size = view.findViewById(R.id.register_size);

        register_info[0] = getDepartment(register_department);
        register_info[1] = getGrade(register_grade);
        register_info[2] = register_motto.getText().toString();
        register_info[3] = DigestUtil.sha1(register_simple_info[1]);
        register_info[4] = DigestUtil.sha1(register_simple_info[1]);
        register_info[5] = register_nick_name.getText().toString();
        register_info[6] = register_phone.getText().toString();
        register_info[7] = register_school.getText().toString();
        register_info[8] = getGender(register_gender);
        register_info[9] = getSize(register_size);
        register_info[10] = register_student_id.getText().toString();
        register_info[11] = register_simple_info[0];
        register_info[12] = register_name.getText().toString();
        register_info[13] = register_email.getText().toString();

        for (int i = 0; i < register_info.length; i++) {
            if (TextUtils.isEmpty(register_info[i].trim())) {
                Toast.makeText(fragment.getActivity(), R.string.register_warning, Toast.LENGTH_SHORT).show();
                break;
            } else {
                value[i] = register_info[i];
            }
        }
        return JsonUtil.getJsonString(key, value);
    }

    private String getGender(RadioGroup register_gender) {
        final String[] gender = new String[1];
        register_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.gender_male:
                        gender[0] = "0";
                        break;
                    case R.id.gender_female:
                        gender[0] = "1";
                }
            }
        });
        return gender[0];
    }

    private String getDepartment(Spinner register_department) {
        final String[] departmentId = new String[1];
        register_department.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                departmentId[0] = String.valueOf(i);
            }
        });
        return departmentId[0];
    }

    private String getGrade(Spinner register_grade) {
        final String[] grade = new String[1];
        register_grade.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                grade[0] = String.valueOf(i);
            }
        });
        return grade[0];
    }

    private String getSize(Spinner register_size) {
        final String[] size = new String[1];
        register_size.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                size[0] = String.valueOf(i);
            }
        });
        return size[0];
    }

}
