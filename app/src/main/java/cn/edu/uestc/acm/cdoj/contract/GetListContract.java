package cn.edu.uestc.acm.cdoj.contract;

import java.util.List;

import cn.edu.uestc.acm.cdoj.base.BasePresenter;
import cn.edu.uestc.acm.cdoj.base.BaseView;

/**
 * Created by lagranmoon on 2018/2/7.
 * 根据指定条件获取列表的契约类
 */

public interface GetListContract {

    interface Presentter<T> extends BasePresenter{
        List<T> getList(String s);
    }

    interface Vier<T> extends BaseView<T>{
        void showList();
        void refreshList();
    }
}
