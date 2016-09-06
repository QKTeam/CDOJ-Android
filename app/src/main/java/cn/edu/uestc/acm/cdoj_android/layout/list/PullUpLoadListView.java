package cn.edu.uestc.acm.cdoj_android.layout.list;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by great on 2016/8/18.
 */
public class PullUpLoadListView extends ListView {

    private boolean isPullUpLoading;
    private boolean hasMoreData = true;
    private OnPullUpLoadListener onPullUpLoadListener;
    private PullUpLoadListViewFooter footer;
    public interface OnPullUpLoadListener {
        void onPullUpLoading();
    }

    public PullUpLoadListView(Context context) {
        super(context);
        init();
    }

    public PullUpLoadListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public PullUpLoadListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        isPullUpLoading = false;
        footer = new PullUpLoadListViewFooter(getContext());
        footer.updateView(PullUpLoadListViewFooter.State.NOT_LOADING, "No More");
        footer.setVisibility(View.VISIBLE);
        addFooterView(footer);
        setOnScrollListener(new OnScrollListener() {
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
            footer.updateView(PullUpLoadListViewFooter.State.LOADING, "Loading.....");
            isPullUpLoading = true;
            onPullUpLoadListener.onPullUpLoading();
        }
    }

    public void setOnPullUpLoadListener(OnPullUpLoadListener listener) {
        onPullUpLoadListener = listener;
    }


    public void finishAddData() {
        isPullUpLoading = false;
    }

    public Boolean isPullUpLoading() {
        return isPullUpLoading;
    }

    public void pullUpLoadFinish() {
        hasMoreData = true;
        footer.updateView(PullUpLoadListViewFooter.State.NOT_LOADING,"No More");
    }

    public void hasMoreData() {
        hasMoreData = true;
    }
}
