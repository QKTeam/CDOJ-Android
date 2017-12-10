package cn.edu.uestc.acm.cdoj.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import cn.edu.uestc.acm.cdoj.MainActivity;
import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.UserInfoCallback;
import cn.edu.uestc.acm.cdoj.user.model.reserved.UserInfo;
import cn.edu.uestc.acm.cdoj.utils.DigestUtil;
import cn.edu.uestc.acm.cdoj.utils.FileUtil;
import cn.edu.uestc.acm.cdoj.utils.SharedPreferenceUtil;

/**
 * Created by lagranmoon on 2017/8/3.
 */

public class LoginFragment extends Fragment implements View.OnClickListener, UserInfoCallback {
    private static final String TAG = "LoginFragment";

    private final HandleUserData handleUserData = new HandleUserData(this);
    private FragmentManager fragmentManager;
    String login_request;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        Button button_login = view.findViewById(R.id.button_login);
        Button button_register = view.findViewById(R.id.button_register);
        button_login.setOnClickListener(this);
        button_register.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_register:
                fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.userinfo_container,new Register_Step1_Fragment());
                fragmentTransaction.commit();
                break;
            case R.id.button_login:
                login_request = handleUserData.handle_login_json();
                UserConnection.getInstance().login(login_request, LoginFragment.this);
                break;
        }
    }

    @Override
    public void loginStatus(Bundle bundle) {
        String[] data = bundle.getStringArray("data");
        if (data != null && data[0].equals("success")) {
            String userName = data[1];
            UserConnection.getInstance().getUserInfo(getActivity(), userName, this, 120);
        } else {
            Toast.makeText(getActivity(), "登陆失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void registerStatus(String s) {

    }


    @Override
    public void getUserInfo(UserInfo userInfo) {
        FileUtil.saveUserInfo(getActivity(), JSON.toJSONString(userInfo), userInfo.getUserName());
        UserInfo user_password = JSON.parseObject(login_request, UserInfo.class);
        save_user_password(user_password);
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra("userName", userInfo.getUserName());
        startActivity(intent);
    }

    @Override
    public void editStatus(String s) {

    }

    private void save_user_password(UserInfo user_password) {
        String[] key = {DigestUtil.md5(user_password.getUserName()),  DigestUtil.md5(user_password.getUserName()) + "_password" ,"current_user"};
        String[] value = {user_password.getUserName(),user_password.getPassword(),user_password.getUserName()};

        SharedPreferenceUtil.saveSharedPreference(getActivity(),"User", key, value);
    }

}
