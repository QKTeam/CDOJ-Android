package cn.edu.uestc.acm.cdoj.ui.detailFragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.Connection;
import cn.edu.uestc.acm.cdoj.net.ReceivedCallback;
import cn.edu.uestc.acm.cdoj.net.contest.ContestReceived;
import cn.edu.uestc.acm.cdoj.ui.ViewPagerAdapter;
import cn.edu.uestc.acm.cdoj.ui.data.ContestListData;
import cn.edu.uestc.acm.cdoj.ui.detailFragment.contestDetail.ContestOverViewFrg;
import cn.edu.uestc.acm.cdoj.ui.detailFragment.contestDetail.ContestProblemListFrag;

/**
 * Created by 14779 on 2017-7-24.
 */

public class ContestDetailFrg extends Fragment implements ReceivedCallback<ContestReceived> {
    private static final String TAG = "ContestDetailFrg";
    public ContestReceived contestReceived;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<Fragment> fragmentList;
    private List<String> tab_title = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contest_detail_fragment, container, false);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            fragmentList.add(initOverView());
            fragmentList.add(initProblemList());
            tab_title.add("概览");
            tab_title.add("题目");
            tab_title.add("讨论");
            tab_title.add("记录");
            tab_title.add("排名");

            ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(), fragmentList, tab_title);
            viewPager.setOffscreenPageLimit(4);
            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);
        }
    };

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int id = ContestListData.getData().get(getArguments().getInt("contest")).getContestId();
        Connection.instance.getContestReceived(id, this);
        viewPager = view.findViewById(R.id.view_pager_contest_detail);
        tabLayout = view.findViewById(R.id.tab_layout_contest_detail);
        fragmentList = new ArrayList<>();

    }

    private Fragment initOverView() {
        ContestOverViewFrg fragment = new ContestOverViewFrg();
        Bundle bundle = new Bundle();
        bundle.putString("contest_detail", JSON.toJSONString(contestReceived.getContest()));
        fragment.setArguments(bundle);
        return fragment;
    }

    private Fragment initProblemList() {
        ContestProblemListFrag fragment = new ContestProblemListFrag();
        Bundle bundle = new Bundle();
        bundle.putString("contest_detail", JSON.toJSONString(contestReceived.getProblemList()));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onDataReceived(ContestReceived contestReceived) {
        this.contestReceived = contestReceived;
        handler.obtainMessage(1, contestReceived).sendToTarget();
    }
}
