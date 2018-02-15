package cn.edu.uestc.acm.cdoj.contract;

import cn.edu.uestc.acm.cdoj.base.BasePresenter;
import cn.edu.uestc.acm.cdoj.base.BaseView;
import cn.edu.uestc.acm.cdoj.data.bean.User;

/**
 * Created by lagranmoon on 2018/2/14.
 * 获取用户信息的presenter与view的契约类
 */

public interface UserInfoContract {

    interface Presenter extends BasePresenter {
        User getUserInfo(String userName);
    }

    interface View<T> extends BaseView<T> {
        void setUserInfo();
        void setAvatar();
    }
}
