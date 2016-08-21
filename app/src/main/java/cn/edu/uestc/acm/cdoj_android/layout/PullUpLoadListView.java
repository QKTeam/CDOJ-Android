package cn.edu.uestc.acm.cdoj_android.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by great on 2016/8/18.
 */
public class PullUpLoadListView extends ListView {

    private boolean isPullUpLoading;
    private OnPullUpLoadListener onPullUpLoadListener;
    private OnScrollListener onScrollListener;
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
        footer.setVisibility(View.VISIBLE);
        addFooterView(footer);
        onScrollListener = new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (needLoad(firstVisibleItem, visibleItemCount, totalItemCount)) {
                    startPullUpLoad();
                }
            }
            private boolean needLoad(int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int lastVisibleItem = firstVisibleItem + visibleItemCount;
                boolean isAtListEnd = lastVisibleItem == totalItemCount;
                return (!isPullUpLoading && isAtListEnd);
            }
        };
        setOnScrollListener(onScrollListener);
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

    public void pullUpLoadingComplete() {
        isPullUpLoading = false;
        footer.updateView(PullUpLoadListViewFooter.State.NOT_LOADING,"Loading Complete");
    }

    public Boolean isPullUpLoading() {
        return isPullUpLoading;
    }
}
