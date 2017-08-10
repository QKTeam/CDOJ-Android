package cn.edu.uestc.acm.cdoj.user;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
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

/**
 * Created by lagranmoon on 2017/8/4.
 */

public class Register_Step2_Fragment extends Fragment implements UserInfoCallback {

    private static final String TAG = "Register_Step2_Fragment";

    private Button button;
    private RadioGroup register_gender;
    private Spinner register_department;
    private Spinner register_grade;
    private Spinner register_size;
    private HandleUserData handleUserData = new HandleUserData(this);
    private final String[] register_info_simple_info = new String[6];
    private String request_json;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_step2, container, false);
        button = view.findViewById(R.id.button_register_step2);
        register_gender = view.findViewById(R.id.register_gender);
        register_department = view.findViewById(R.id.register_spinner_department);
        register_grade = view.findViewById(R.id.register_spinner_grade);
        register_size = view.findViewById(R.id.register_size);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        register_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.gender_male:
                        register_info_simple_info[5] = "0";
                        break;
                    case R.id.gender_female:
                        register_info_simple_info[5] = "1";
                        break;
                }
            }
        });
        register_department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                register_info_simple_info[2] = String.valueOf(i+1);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        register_grade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                register_info_simple_info[3] = String.valueOf(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        register_size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                register_info_simple_info[4] = String.valueOf(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    request_json = handleUserData.get_register_json(register_info_simple_info);
                    UserConnection.getInstance().register(request_json, Register_Step2_Fragment.this);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), R.string.register_warning, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void registerStatus(String s) {
        if (TextUtils.equals(s, "success")) {
            Toast.makeText(getActivity(), R.string.registerSuccess, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), R.string.registerError, Toast.LENGTH_SHORT).show();
        }
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.userinfo_container, new LoginFragment());
        fragmentManager.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentTransaction.commit();
    }

    public void setRegister_info_simple_info(String[] register_info_simple_info_data) {
        for (int i = 0; i < register_info_simple_info_data.length; i++) {
            register_info_simple_info[i] = register_info_simple_info_data[i];
        }
    }


    @Override
    public void loginStatus(Bundle bundle) {

    }

    @Override
    public void getUserInfo(UserInfo userInfo) {

    }

    @Override
    public void editStatus(String s) {

    }

}
