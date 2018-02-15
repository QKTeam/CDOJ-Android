package cn.edu.uestc.acm.cdoj.contract;

import android.content.Context;

import cn.edu.uestc.acm.cdoj.base.BasePresenter;
import cn.edu.uestc.acm.cdoj.base.BaseView;
import cn.edu.uestc.acm.cdoj.data.bean.User;

/**
 * Created by lagranmoon on 2018/2/7.
 * 用户登录的presenter与view的契约类
 */

public interface LoginContract {
    interface Presenter<T> extends BasePresenter<T>{
        boolean login();
        boolean checkFieldValidity(String userName,String password);
    }

    interface View<T> extends BaseView<T>{
        boolean checkNetWorkState(Context context);
    }

}
