package cn.edu.uestc.acm.cdoj_android.layout.list;

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
    private boolean isPullUpLoad;
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
        if (isPullUpLoad){
            isPullUpLoading = false;
            footer = new PullUpLoadListViewFooter(getContext());
            footer.setVisibility(View.VISIBLE);
            addFooterView(footer);
            setOnScrollListener(new OnScrollListener() {
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
            });
        }
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

    public void setPullUpLoad(boolean isPullUpLoad) {
        this.isPullUpLoad = isPullUpLoad;
    }
}
