package cn.edu.uestc.acm.cdoj.ui.data;

import android.support.v7.widget.RecyclerView;

import cn.edu.uestc.acm.cdoj.genaralData.RefreshLoadListener;


/**
 * Created by 14779 on 2017-7-23.
 */

public interface GeneralList {
    void setListAdapter(RecyclerView.Adapter adapter);

    void setRefreshLoadListener(RefreshLoadListener upLoadListener);
}
