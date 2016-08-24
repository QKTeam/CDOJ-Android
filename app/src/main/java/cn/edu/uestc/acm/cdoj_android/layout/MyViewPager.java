package cn.edu.uestc.acm.cdoj_android.layout;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by great on 2016/8/18.
 */
public class MyViewPager extends ViewPager {
    private boolean isScrollable;

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isScrollable && super.onTouchEvent(ev);
    }

    public void setScrollable(boolean isScrollable) {
        this.isScrollable = isScrollable;
    }
}
