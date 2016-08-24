package cn.edu.uestc.acm.cdoj_android;

import android.support.v4.view.ViewPager;

import cn.edu.uestc.acm.cdoj_android.layout.details.DetailsContainerFragment;
import cn.edu.uestc.acm.cdoj_android.layout.details.DetailsWebViewFragment;
import cn.edu.uestc.acm.cdoj_android.layout.list.ListContainerFragment;

/**
 * Created by great on 2016/8/15.
 */
public interface Selection {
    void setSelectionList(int position);

    boolean isTwoPane();

    DetailsContainerFragment getDetailsContainer();

    ListContainerFragment getListContainer();
}
