package cn.edu.uestc.acm.cdoj.ui.detailFragment.contestDetail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.edu.uestc.acm.cdoj.genaralData.GeneralFragment;
import cn.edu.uestc.acm.cdoj.net.contest.ContestStatusListItem;

/**
 * Created by 14779 on 2017-8-4.
 */

public class ContestStatusFrg extends GeneralFragment<ContestStatusListItem> {
    public ContestStatusFrg(Context context, String type) {
        super(context, type);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
