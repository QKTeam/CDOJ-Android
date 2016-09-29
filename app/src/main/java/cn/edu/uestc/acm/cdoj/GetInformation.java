package cn.edu.uestc.acm.cdoj;

import cn.edu.uestc.acm.cdoj.layout.DetailsContainer;
import cn.edu.uestc.acm.cdoj.layout.ListContainer;

/**
 * Created by great on 2016/8/15.
 */
public interface GetInformation {

    boolean isTwoPane();

    DetailsContainer getDetailsContainer();

    ListContainer getListContainer();
}
