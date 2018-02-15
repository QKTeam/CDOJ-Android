package cn.edu.uestc.acm.cdoj.contract;

import cn.edu.uestc.acm.cdoj.base.BasePresenter;
import cn.edu.uestc.acm.cdoj.base.BaseView;

/**
 * Created by lagranmoon on 2018/2/7.
 * 根据指定条件获取详情的契约类
 */

public interface GetDetailContract {
    interface Presenter<T> extends BasePresenter{
        T getDetail(String s);
    }
    interface View<T> extends BaseView<T>{
        void showDetail();
    }
}
