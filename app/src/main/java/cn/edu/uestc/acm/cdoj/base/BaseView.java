package cn.edu.uestc.acm.cdoj.base;

import android.view.View;

/**
 * Created by lagranmoon on 2017/8/21.
 * View层要实现的基本方法
 */

public interface BaseView<T> {
    void setPresenter(T presenter);
    void showProgress();
    void showError(int errorCode);
}
