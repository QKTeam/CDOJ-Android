package cn.edu.uestc.acm.cdoj.user.login;

import cn.edu.uestc.acm.cdoj.base.BasePresenter;
import cn.edu.uestc.acm.cdoj.base.BaseView;

/**
 * Created by lagranmoon on 2017/8/21.
 * This specifies the contract between the view and the presenter.
 */

public interface LoginContract {

    interface View extends BaseView<Presenter>{
        void showNetworkErrorMessage();
        void showLoginErrorMessage();
    }

    interface Presenter extends BasePresenter{
        void initView();
        boolean checkFieldValidity();
        boolean checkNetWorkStatus();
        boolean login(String RequestContent);
        String getRequestContent(android.view.View view);
    }

}
