package cn.edu.uestc.acm.cdoj.layout;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by great on 2016/8/18.
 */
public class ViewPagerWithoutPaging extends ViewPager {
    private boolean isPagingEnabled = false;

    public ViewPagerWithoutPaging(Context context) {
        super(context);
    }

    public ViewPagerWithoutPaging(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onInterceptTouchEvent(event);
    }

    public void setPagingEnabled(boolean pagingEnabled) {
        this.isPagingEnabled = pagingEnabled;
    }
}