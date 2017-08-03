package cn.edu.uestc.acm.cdoj.ui.detailFragment.contestDetail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.edu.uestc.acm.cdoj.genaralData.GeneralFragment;

/**
 * Created by 14779 on 2017-8-3.
 */

public class ContestCommentFrg extends GeneralFragment{

    public ContestCommentFrg(Context context, String type) {
        super(context, type);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Subscribe
    public void onEvent(int id) {

    }
}
