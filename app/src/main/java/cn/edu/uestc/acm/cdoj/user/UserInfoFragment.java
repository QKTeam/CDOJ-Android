package cn.edu.uestc.acm.cdoj.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.io.File;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.utils.DigestUtil;
import cn.edu.uestc.acm.cdoj.utils.FileUtil;
import cn.edu.uestc.acm.cdoj.utils.SharedPreferenceUtil;

/**
 * Created by lagranmoon on 2017/8/4.
 */

public class UserInfoFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "UserInfoFragment";

    private Button button_logout;
    private Button button_edit;
    private TextView profile_username;
    private TextView profile_name;
    private TextView profile_nickname;
    private TextView profile_motto;
    private TextView profile_gender;
    private TextView profile_email;
    private TextView profile_phone;
    private TextView profile_size;
    private TextView profile_school;
    private TextView profile_studentID;
    private TextView profile_department;
    private TextView profile_grade;

    private UserInfo userInfo;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_userinfo_profile, container, false);
        initView(view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userInfo = getUserInfo();
        setUserInfo(userInfo);
        button_logout.setOnClickListener(this);
        button_edit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (view.getId()) {
            case R.id.button_logout:
                UserConnection.getInstance().logout();
//                String[] User = {DigestUtil.md5(userInfo.getUserName()),DigestUtil.md5(userInfo.getUserName())+"_password","current_user"};
//                SharedPreferenceUtil.deleteSharedPreference(getActivity(),DigestUtil.md5("User"),User);
                SharedPreferenceUtil.clearSharedPreference(getActivity(),"User");
                transaction.replace(R.id.userinfo_container, new LoginFragment());
                transaction.commit();
                break;
            case R.id.button_edit:
                transaction.replace(R.id.userinfo_container, new UserEditFragment());
                transaction.addToBackStack(null);
                transaction.commit();
                break;
        }
    }

    private void initView(View view) {
        button_logout = view.findViewById(R.id.button_logout);
        button_edit = view.findViewById(R.id.button_edit);
        profile_username = view.findViewById(R.id.profile_username);
        profile_name = view.findViewById(R.id.profile_name);
        profile_nickname = view.findViewById(R.id.profile_nickname);
        profile_motto = view.findViewById(R.id.profile_motto);
        profile_gender = view.findViewById(R.id.profile_gender);
        profile_email = view.findViewById(R.id.profile_email);
        profile_phone = view.findViewById(R.id.profile_phone);
        profile_size = view.findViewById(R.id.profile_size);
        profile_school = view.findViewById(R.id.profile_school);
        profile_studentID = view.findViewById(R.id.profile_studentID);
        profile_department = view.findViewById(R.id.profile_department);
        profile_grade = view.findViewById(R.id.profile_grade);
    }

    public UserInfo getUserInfo() {
        UserInfo userInfo = null;
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(DigestUtil.md5("User"), Context.MODE_APPEND);
        if (sharedPreferences.contains("current_user")) {
            String current_user = sharedPreferences.getString("current_user", null);
            if (new File(getActivity().getFilesDir() + "/UserInfo/" + current_user).exists()) {
                userInfo = JSON.parseObject(
                        FileUtil.readFile(getActivity(), "UserInfo", current_user),
                        UserInfo.class);
            }
        }
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo){
        Resources resources = getResources();
        String [] department = resources.getStringArray(R.array.department);
        String [] grade = resources.getStringArray(R.array.grade);
        String [] size = resources.getStringArray(R.array.size);
        profile_username.setText(userInfo.getUserName());
        profile_name.setText(userInfo.getName());
        profile_nickname.setText(userInfo.getNickName());
        profile_motto.setText(userInfo.getMotto());
        profile_email.setText(userInfo.getEmail());
        profile_phone.setText(userInfo.getPhone());
        profile_size.setText(size[userInfo.getSize()]);
        profile_school.setText(userInfo.getSchool());
        profile_studentID.setText(userInfo.getStudentId());
        profile_department.setText(department[userInfo.getDepartmentId()-1]);
        profile_grade.setText(grade[userInfo.getGrade()]);
        switch (userInfo.getSex()){
            case 0:
                profile_gender.setText("男");
                break;
            case 1:
                profile_gender.setText("女");
        }

    }

}
