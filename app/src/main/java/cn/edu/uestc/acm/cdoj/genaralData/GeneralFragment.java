package cn.edu.uestc.acm.cdoj.genaralData;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.ui.data.GeneralList;

/**
 * Created by 14779 on 2017-7-19.
 */

public class GeneralFragment<T> extends Fragment implements GeneralList {
    private static final String TAG = "GeneralFragment";
    private String type;
    private Context context;
    private RecyclerView mRecyclerView;
    protected TwinklingRefreshLayout swipeRefreshLayout;
    private RefreshLoadListener refreshLoadListener;

    @SuppressLint("ValidFragment")
    public GeneralFragment(Context context, String type) {
        super();
        this.context = context;
        this.type = type;
    }
    public GeneralFragment(){
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_genaral, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.genaral_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        swipeRefreshLayout = view.findViewById(R.id.general_refresh_layout);

        ProgressLayout headView = new ProgressLayout(context);
        swipeRefreshLayout.setHeaderView(headView);
        swipeRefreshLayout.setOverScrollRefreshShow(false);
        swipeRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLoadListener.onRefresh();
                        refreshLayout.finishRefreshing();
                    }
                }, 1500);

            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLoadListener.onLoadMore();
                        refreshLayout.finishLoadmore();

                    }
                }, 1500);
            }
        });
    }

    @Override
    public void setListAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void setRefreshLoadListener(RefreshLoadListener refreshLoadListener) {
        this.refreshLoadListener = refreshLoadListener;
    }


    public interface TransItemDataListener {
        void onTranItemData(int position, String type);
    }
}


