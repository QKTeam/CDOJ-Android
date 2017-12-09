package cn.edu.uestc.acm.cdoj.user.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.PrintWriter;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.user.Register_Step1_Fragment;
import cn.edu.uestc.acm.cdoj.user.UserConnection;
import cn.edu.uestc.acm.cdoj.utils.DigestUtil;
import cn.edu.uestc.acm.cdoj.utils.JsonUtil;

/**
 * Created by lagranmoon on 2017/8/21.
 * 用户登录的View层
 */

public class LoginFragment extends Fragment implements LoginContract.View,View.OnClickListener {

    private LoginContract.Presenter mLoginPresenter;

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

                break;
            case R.id.button_login:

                break;
        }
    }



    public void setPresenter(@NonNull LoginContract.Presenter presenter) {
        mLoginPresenter = presenter;
    }

    @Override
    public void initView(View view) {

    }


    @Override
    public void showNetworkErrorMessage() {

    }

    @Override
    public void showLoginErrorMessage() {

    }


}
