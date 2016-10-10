package cn.edu.uestc.acm.cdoj.ui.modules.list;

import java.util.Map;

import cn.edu.uestc.acm.cdoj.net.data.PageInfo;

/**
 * Created by Grea on 2016/10/7.
 */

public interface MainList {

    interface OnRefreshProgressListener {
        void start();
        void end();
    }

    void setRefreshProgressListener(OnRefreshProgressListener listener);

    void addListItem(Map<String, Object> listItem);

    ListViewWithGestureLoad getListView();

    void setProgressContainerVisibility(int visibility);

    void notifyDataSetChanged();

}
