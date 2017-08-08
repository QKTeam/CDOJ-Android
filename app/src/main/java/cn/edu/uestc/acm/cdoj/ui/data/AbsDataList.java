package cn.edu.uestc.acm.cdoj.ui.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import cn.edu.uestc.acm.cdoj.genaralData.ContentReceived;
import cn.edu.uestc.acm.cdoj.genaralData.GeneralFragment;
import cn.edu.uestc.acm.cdoj.genaralData.ListReceived;
import cn.edu.uestc.acm.cdoj.genaralData.PageInfo;
import cn.edu.uestc.acm.cdoj.genaralData.RefreshLoadListener;
import cn.edu.uestc.acm.cdoj.net.ReceivedCallback;

/**
 * Created by 14779 on 2017-7-25.
 */

public abstract class AbsDataList<T> implements ReceivedCallback<ListReceived<T>>, RefreshLoadListener {
    private static final String TAG = "AbsDataList";
    protected Context context;
    protected PageInfo mPageInfo;
    protected List<T> data = new ArrayList<>();
    protected GeneralList list;
    protected RecyclerView.Adapter adapter;
    protected GeneralFragment.TransItemDataListener transItemDataListener;
    private boolean firstLoad = true;
    protected boolean isRefreshing = false;
    protected boolean isPasswordTrue = false;

    public AbsDataList(Context context){
        this.context = context;
        mPageInfo = new PageInfo();
        mPageInfo.currentPage = 1;

    }

    public void setUpList(GeneralList list){
        this.list = list;
        list.setRefreshLoadListener(this);
        transItemDataListener = (GeneralFragment.TransItemDataListener) context;
        createAdapter();
    }

    @Override
    public void onDataReceived(ListReceived<T> tListReceived) {
        mPageInfo = tListReceived.getPageInfo();
        if (tListReceived.getResult().equals("success")) {
            if (isRefreshing) {
                data.clear();
                isRefreshing = false;
            }
            data.addAll(tListReceived.getList());
            if (firstLoad) {
                list.setListAdapter(adapter);
                firstLoad = false;
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoginDataReceived(ContentReceived dataReceived) {
        if (dataReceived.getResult().equals("success")){
            isPasswordTrue = true;
        }
    }

    protected abstract void createAdapter();


}
