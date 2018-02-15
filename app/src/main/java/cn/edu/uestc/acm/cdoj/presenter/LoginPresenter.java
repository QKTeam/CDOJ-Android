package cn.edu.uestc.acm.cdoj.presenter;

import android.app.Application;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.base.BaseApplication;
import cn.edu.uestc.acm.cdoj.contract.LoginContract;
import cn.edu.uestc.acm.cdoj.data.source.repo.UserDataSource;
import cn.edu.uestc.acm.cdoj.data.source.repo.UserRepository;
import cn.edu.uestc.acm.cdoj.net.Constant;

/**
 * Created by lagranmoon on 2018/2/14.
 * 用于登录的Presenter
 */

public class LoginPresenter implements LoginContract.Presenter<LoginContract.View> {

    private LoginContract.View mLoginView;
    private UserRepository mUserRepository;
    private Unbinder unbinder;
    private int errorCode;
    private View view;

    private String userName;
    private String password;

    @BindView(R.id.input_username)
    TextInputEditText inputUsername;
    @BindView(R.id.input_password)
    TextInputEditText inputPassword;



    public LoginPresenter(UserRepository userRepository) {
        this.mUserRepository = userRepository;
        mLoginView.setPresenter(this);
    }

    @Override
    public void attachView(LoginContract.View view) {
        this.mLoginView = view;
    }

    @Override
    public void detachView() {
        unbinder.unbind();
        mLoginView = null;
    }

    @Override
    public void start() {
        if (login()){
            errorCode = Constant.LOGIN_ERROR;
            mLoginView.showError(errorCode);
        }
    }

    @Override
    @OnClick(R.id.button_login)
    public boolean login() {
        userName = inputUsername.getText().toString();
        password = inputPassword.getText().toString();
        if (!mLoginView.checkNetWorkState(BaseApplication.getContext())){
            errorCode = Constant.NETWORK_DISCONNECTED;
            mLoginView.showError(errorCode);
        }else if (!checkFieldValidity(userName,password)){
           errorCode = Constant.FIELD_INVALID;
        }else {
            return mUserRepository.login(userName,password);
        }
        return false;
    }

    @Override
    public boolean checkFieldValidity(String userName, String password) {
        if (TextUtils.isEmpty(userName)) {
            inputUsername.setError(view.getResources().getString(R.string.username_null_error));
            return false;
        } else if (TextUtils.isEmpty(password)) {
            inputPassword.setError(view.getResources().getString(R.string.password_null_error));
            return false;
        } else {
            return true;
        }
    }

}
