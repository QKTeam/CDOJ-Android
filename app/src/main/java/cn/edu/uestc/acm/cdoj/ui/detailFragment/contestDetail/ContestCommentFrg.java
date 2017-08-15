package cn.edu.uestc.acm.cdoj.ui.detailFragment.contestDetail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.edu.uestc.acm.cdoj.genaralData.GeneralFragment;

/**
 * Created by 14779 on 2017-8-3.
 */

public class ContestCommentFrg extends GeneralFragment{

    @SuppressLint("ValidFragment")
    public ContestCommentFrg(Context context, String type) {
        super(context, type);
    }
    public ContestCommentFrg(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
