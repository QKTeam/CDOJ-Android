package cn.edu.uestc.acm.cdoj_android;

import cn.edu.uestc.acm.cdoj_android.layout.DetailsContainer;
import cn.edu.uestc.acm.cdoj_android.layout.ListContainerFragment;

/**
 * Created by great on 2016/8/15.
 */
public interface GetInformation {

    boolean isTwoPane();

    DetailsContainer getDetailsContainer();

    ListContainerFragment getListContainer();
}
