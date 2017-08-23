package cn.edu.uestc.acm.cdoj.user.login;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;

import java.io.PrintWriter;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.utils.DigestUtil;
import cn.edu.uestc.acm.cdoj.utils.JsonUtil;
import cn.edu.uestc.acm.cdoj.utils.NetworkUtil;

/**
 * Created by lagranmoon on 2017/8/21.
 *
 * Listens to user actions from the UI ({@link LoginFragment}), retrieves the data and updates
 * the UI as required.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private final LoginContract.View mLoginView;
    private  TextInputEditText input_username;
    private TextInputEditText input_password;
    private  View view;
    public LoginPresenter(@NonNull LoginContract.View mLoginView) {
        this.mLoginView = mLoginView;
        view = (View) mLoginView;
        mLoginView.setPresenter(this);
    }

    /**
     * the entrance of the presenter
     */
    public void start() {
        initView();
        if (checkFieldValidity()){
            if (checkNetWorkStatus()){
                if (login(getRequestContent(view))){

                }else {
                    mLoginView.showLoginErrorMessage();
                }
            }else {
                mLoginView.showNetworkErrorMessage();
            }
        }
    }

    /**
     * init the TextInputEditText in order to get its content
     */
    @Override
    public void initView() {
        input_username = view.findViewById(R.id.input_username);
        input_password = view.findViewById(R.id.input_password);
    }


    /**
     * check  network availability
     * @return true means network is available
     */
    @Override
    public boolean checkNetWorkStatus() {
        return NetworkUtil.isConnect(view.getContext());
    }

    /**
     *
     * @param RequestContent Json string which contains username and password.
     * @return true means login success
     */
    @Override
    public boolean login(String RequestContent) {
        return false;
    }

    /**
     * To check the field validity
     * @return true means everything is valid
     */
    @Override
    public boolean checkFieldValidity() {
        if (TextUtils.isEmpty(input_username.getText().toString())){
            input_username.setError(view.getResources().getString(R.string.username_null_error));
            return false;
        }else if (TextUtils.isEmpty(input_password.getText().toString())){
            input_password.setError(view.getResources().getString(R.string.password_null_error));
            return false;
        }else {
            return true;
        }
    }


    /**
     * @param view the LoginFragment
     * @return the JsonString which contains username and encrypted password
     */
    @Override
    public String getRequestContent(View view) {
        String username = input_username.getText().toString();
        String password_encrypted = DigestUtil.sha1(input_password.getText().toString());
        String[] key = new String[]{"userName", "password"};
        Object[] value = new Object[]{username, password_encrypted};
        return JsonUtil.getJsonString(key, value);
    }
}
