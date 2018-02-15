package cn.edu.uestc.acm.cdoj.base;

import android.view.View;

/**
 * Created by lagranmoon on 2017/8/21.
 * Presenter必须实现的一些方法
 */

public interface BasePresenter<T> {
    void attachView(T view);
    void detachView();
    void start();
}
