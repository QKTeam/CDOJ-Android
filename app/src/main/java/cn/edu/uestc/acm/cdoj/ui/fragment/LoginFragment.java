package cn.edu.uestc.acm.cdoj.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.base.BaseApplication;
import cn.edu.uestc.acm.cdoj.contract.LoginContract;
import cn.edu.uestc.acm.cdoj.net.Constant;
import cn.edu.uestc.acm.cdoj.presenter.LoginPresenter;
import cn.edu.uestc.acm.cdoj.util.NetworkUtil;

/**
 * Created by lagranmoon on 2018/2/14.
 * 用于登录的Fragment
 */

public class LoginFragment extends Fragment implements LoginContract.View<LoginPresenter> {

    private View view;
    private LoginPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.attachView(this);
    }

    @Override
    public void onDestroyView() {
        presenter.detachView();
        super.onDestroyView();
    }

    @Override
    public void setPresenter(LoginPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void showError(int errorCode) {
        switch (errorCode) {
            case Constant.NETWORK_DISCONNECTED:
                Toast.makeText(BaseApplication.getContext(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                break;
            case Constant.LOGIN_ERROR:
                Toast.makeText(BaseApplication.getContext(), getString(R.string.login_error), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public boolean checkNetWorkState(Context context) {
        return NetworkUtil.isConnect(context);
    }
}
