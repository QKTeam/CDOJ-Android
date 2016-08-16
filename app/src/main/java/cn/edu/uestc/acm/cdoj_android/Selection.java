package cn.edu.uestc.acm.cdoj_android;

import android.support.v4.view.ViewPager;

/**
 * Created by great on 2016/8/15.
 */
public interface Selection {
    void setSelectionList(int position);
    void initTab(ViewPager detailsContainer_ViewPager);
}
