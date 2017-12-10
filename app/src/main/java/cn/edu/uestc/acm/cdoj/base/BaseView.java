package cn.edu.uestc.acm.cdoj.base;

import android.view.View;

/**
 * Created by lagranmoon on 2017/8/21.
 */

public interface BaseView<T> {
    void setPresenter(T presenter);
    void initView(View view);
}
