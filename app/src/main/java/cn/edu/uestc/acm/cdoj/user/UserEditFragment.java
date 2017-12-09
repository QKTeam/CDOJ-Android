package cn.edu.uestc.acm.cdoj.user;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.UserInfoCallback;
import cn.edu.uestc.acm.cdoj.user.model.reserved.UserInfo;

/**
 * Created by lagranmoon on 2017/8/9.
 */

public class UserEditFragment extends Fragment implements UserInfoCallback {

    private static final String TAG = "UserEditFragment";

    private Button button;
    private RadioGroup edit_gender;
    private Spinner edit_department;
    private Spinner edit_grade;
    private Spinner edit_size;
    private HandleUserData handleUserData = new HandleUserData(this);
    private String request_json;
    private final String[] edit_simple_info = new String[6];

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_userinfo_edit,container,false);
        button = view.findViewById(R.id.button_submit);
        edit_gender = view.findViewById(R.id.edit_gender);
        edit_department = view.findViewById(R.id.edit_spinner_department);
        edit_grade = view.findViewById(R.id.edit_spinner_grade);
        edit_size = view.findViewById(R.id.edit_size);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edit_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.gender_male:
                        edit_simple_info[5] = "0";
                        break;
                    case R.id.gender_female:
                        edit_simple_info[5] = "1";
                        break;
                }
            }
        });
        edit_department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               edit_simple_info[2] = String.valueOf(i+1);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        edit_grade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                edit_simple_info[3] = String.valueOf(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        edit_size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                edit_simple_info[4] = String.valueOf(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    request_json = handleUserData.get_edit_json(edit_simple_info);
                    UserConnection.getInstance().edit(request_json,UserEditFragment.this);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), R.string.register_warning, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public void loginStatus(Bundle bundle) {

    }

    @Override
    public void registerStatus(String s) {

    }

    @Override
    public void getUserInfo(UserInfo userInfo) {

    }

    @Override
    public void editStatus(String s) {

        Log.d(TAG, "editStatus: "+s);

        if (TextUtils.equals(s,"success")){
            Toast.makeText(getActivity(),R.string.edit_success,Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getActivity(),R.string.password_error,Toast.LENGTH_SHORT).show();
        }
    }
}
