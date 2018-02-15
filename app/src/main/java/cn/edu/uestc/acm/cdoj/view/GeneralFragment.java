package cn.edu.uestc.acm.cdoj.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.edu.uestc.acm.cdoj.R;

/**
 * Created by lagranmoon on 2018/2/7.
 * 首页公告，问题和比赛列表通用的Fragment
 */

public class GeneralFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_general,container,false);
    }
}
