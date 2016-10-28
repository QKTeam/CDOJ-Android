package cn.edu.uestc.acm.cdoj.ui.modules.list;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.BaseAdapter;
import android.widget.ListView;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.tools.DrawImage;
import cn.edu.uestc.acm.cdoj.tools.RGBAColor;
import cn.edu.uestc.acm.cdoj.ui.modules.Global;

/**
 * Created by Grea on 2016/10/7.
 */

public class ListViewWithGestureLoad extends LinearLayout {

    private boolean isPullUpLoading;
    private boolean hasMoreData = true;
    private boolean pullUpLoadEnable = true;
    private LinearLayout mProgressContainer;
    private ListView mListView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListViewFooter mListViewFooter;
    private ImageButton upButton;
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener;
    private OnPullUpLoadListener onPullUpLoadListener;
    private AdapterView.OnItemClickListener onItemClickListener;

    public interface OnPullUpLoadListener {
        void onPullUpLoading();
    }

    public ListViewWithGestureLoad(Context context) {
        this(context, null);
    }

    public ListViewWithGestureLoad(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListViewWithGestureLoad(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        inflate(getContext(), R.layout.list_view_with_gesture_load, this);
        mProgressContainer = (LinearLayout) findViewById(R.id.list_view_with_gesture_load_progressContainer);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.list_view_with_gesture_load_swipeRefresh);

        upButton = (ImageButton) findViewById(R.id.list_view_with_gesture_up);
        upButton.setImageDrawable(Global.getListIcon_up());
        upButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListView.getFirstVisiblePosition() > 85) {
                    mListView.setSelection(0);
                }else {
                    mListView.smoothScrollToPosition(0);
                }
                v.setVisibility(GONE);
            }
        });

        mListView = (ListView) findViewById(R.id.list_view_with_gesture_load_list);
        mListViewFooter = new ListViewFooter(getContext());
        setupListView();
        setupListener();
    }

    private void setupListView() {
        mListView.addFooterView(mListViewFooter, null, false);

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_IDLE:
                        int bottomPosition = view.getLastVisiblePosition();
                        boolean toBottom = bottomPosition == view.getCount() - 1;
                        if (pullUpLoadEnable && toBottom && !isPullUpLoading && hasMoreData) {
                            mListViewFooter.updateContent(ListViewFooter.LOADING);
                            isPullUpLoading = true;
                            onPullUpLoad();
                        }
                        if (bottomPosition > 30) {
                            upButton.setVisibility(VISIBLE);
                        } else {
                            upButton.setVisibility(GONE);
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
    }

    private void setupListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onListRefresh();
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onListItemClick(parent, view, position, id);
            }
        });
    }

    public void setListAdapter(BaseAdapter adapter) {
        mProgressContainer.setVisibility(GONE);
        mListView.setAdapter(adapter);
    }

    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) {
        this.onRefreshListener = listener;
    }

    public void setOnPullUpLoadListener(OnPullUpLoadListener listener) {
        this.onPullUpLoadListener = listener;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setRefreshEnable(boolean enable) {
        mSwipeRefreshLayout.setEnabled(enable);
    }

    public void setPullUpLoadEnable(boolean enable) {
        pullUpLoadEnable = enable;
    }

    public void noticeLoadFinish() {
        hasMoreData = false;
        isPullUpLoading = false;
        mListViewFooter.updateContent(ListViewFooter.LOADCOMPLETE);
    }

    public void noticeLoadFailure() {
        hasMoreData = false;
        isPullUpLoading = false;
        mListViewFooter.updateContent(ListViewFooter.LOADPROBLEM);
    }

    public void noticeDataIsNull() {
        hasMoreData = false;
        isPullUpLoading = false;
        mListViewFooter.updateContent(ListViewFooter.DATAISNULL);
    }

    public void noticeNetNotConnect() {
        hasMoreData = false;
        isPullUpLoading = false;
        mListViewFooter.updateContent(ListViewFooter.NETNOTCONNECT);
    }

    public void noticeConnectOvertime() {
        hasMoreData = false;
        isPullUpLoading = false;
        mListViewFooter.updateContent(ListViewFooter.CONNECTOVERTIME);
    }

    public void noticePullUpLoadComplete() {

        isPullUpLoading = false;
    }

    public void noticeRefreshComplete() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public void noticeLoadOrRefreshComplete() {
        noticePullUpLoadComplete();
        noticeRefreshComplete();
    }

    public void resetPullUpLoad() {
        isPullUpLoading = false;
        hasMoreData = true;
    }

    public boolean isPullUpLoading() {
        return isPullUpLoading;
    }

    public boolean isRefreshing() {
        return mSwipeRefreshLayout.isRefreshing();
    }

    public ListView getListView() {
        return mListView;
    }

    public void onListItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(parent, view, position, id);
        }
    }

    public void onListRefresh() {
        if (onRefreshListener != null) {
            onRefreshListener.onRefresh();
        }
    }

    public void onPullUpLoad() {
        if (onPullUpLoadListener != null) {
            onPullUpLoadListener.onPullUpLoading();
        }
    }
}
