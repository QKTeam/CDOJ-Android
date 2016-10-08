package cn.edu.uestc.acm.cdoj.ui.modules.list;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import cn.edu.uestc.acm.cdoj.R;

/**
 * Created by Grea on 2016/10/7.
 */

public class ListViewWithGestureLoad extends LinearLayout {

    private boolean isPullUpLoading;
    private boolean hasMoreData = true;
    private LinearLayout mProgressContainer;
    private ListView mListView;
    private ListAdapter mListAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListViewFooter mListViewFooter;
    private OnPullUpLoadListener onPullUpLoadListener;
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener;
    private AdapterView.OnItemClickListener onItemClickListener;
    private boolean mProgressContainerShown;

    public interface OnPullUpLoadListener {
        void onPullUpLoading();
    }

    public ListViewWithGestureLoad(Context context) {
        super(context);
        initViews();
    }

    public ListViewWithGestureLoad(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public ListViewWithGestureLoad(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        inflate(getContext(), R.layout.list_view_with_gesture_load, this);
        isPullUpLoading = false;
        mProgressContainer = (LinearLayout) findViewById(R.id.list_view_with_gesture_load_progressContainer);
        mListView = (ListView) findViewById(R.id.list_view_with_gesture_load_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.list_view_with_gesture_load_swipeRefresh);
        mListViewFooter = new ListViewFooter(getContext());
        configListView();
    }

    private void configListView() {
        mListView.addFooterView(mListViewFooter);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_IDLE:
                        boolean toBottom = view.getLastVisiblePosition() == view.getCount() - 1;
                        if (toBottom && !isPullUpLoading && hasMoreData) {
                            startPullUpLoad();
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {}
        });
    }

    private void startPullUpLoad() {
        if (onPullUpLoadListener != null) {
            mListViewFooter.updateContent(ListViewFooter.LOADING);
            isPullUpLoading = true;
            onPullUpLoadListener.onPullUpLoading();
        }
    }

    public void setPullUpLoadFinish() {
        hasMoreData = false;
        mListViewFooter.updateContent(ListViewFooter.LOADCOMPLETE);
    }

    public void setPullUpLoadFailure() {
        isPullUpLoading = false;
        mListViewFooter.updateContent(ListViewFooter.LOADPROBLEM);
    }

    public void resetPullUpLoad() {
        hasMoreData = true;
    }

    public boolean isPullUpLoading() {
        return isPullUpLoading;
    }

    public boolean isRefreshing() {
        return mSwipeRefreshLayout.isRefreshing();
    }

    public void setOnPullUpLoadListener(OnPullUpLoadListener listener) {
        onPullUpLoadListener = listener;
    }

    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) {
        onRefreshListener = listener;
        mSwipeRefreshLayout.setOnRefreshListener(listener);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        onItemClickListener = listener;
        mListView.setOnItemClickListener(listener);
    }

    public void setAdapter(ListAdapter adapter) {
        mListAdapter = adapter;
        setProgressContainerVisibility(View.GONE);
        mListView.setAdapter(adapter);
    }

    public void setRefreshing(boolean refreshing) {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(refreshing);
        }
    }

    public void setPullUpLoading(boolean loading) {
        isPullUpLoading = loading;
        if (loading) startPullUpLoad();
    }

    public void setProgressContainerVisibility(int visibility) {
        if (visibility != View.INVISIBLE && visibility != View.VISIBLE) {
            return;
        }
        mProgressContainerShown = visibility == View.VISIBLE;
        if (mProgressContainer != null) {
            mProgressContainer.setVisibility(visibility);
        }
    }

    public ListView getListView() {
        return mListView;
    }

    public int getFirstVisiblePosition() {
        return mListView.getFirstVisiblePosition();
    }

    public int getLastVisiblePosition() {
        return mListView.getLastVisiblePosition();
    }

    public View findItemViewWithTag(Object tag) {
       return mListView.findViewWithTag(tag);
    }
}
