package cn.edu.uestc.acm.cdoj_android.layout;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by great on 2016/8/18.
 */
public class ViewPagerWithoutGesture extends ViewPager {

    public ViewPagerWithoutGesture(Context context) {
        super(context);
    }

    public ViewPagerWithoutGesture(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
