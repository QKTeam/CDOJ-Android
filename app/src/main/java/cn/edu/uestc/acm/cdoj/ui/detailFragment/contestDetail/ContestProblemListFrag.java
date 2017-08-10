package cn.edu.uestc.acm.cdoj.ui.detailFragment.contestDetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.contest.problem.ContestProblem;
import cn.edu.uestc.acm.cdoj.ui.ViewPagerAdapter;
import cn.edu.uestc.acm.cdoj.ui.data.ProblemStatusListData;
import cn.edu.uestc.acm.cdoj.ui.detailFragment.CodeFragment;

/**
 * Created by 14779 on 2017-7-25.
 */

public class ContestProblemListFrag extends Fragment {
    private static final String TAG = "ContestProblemListFrag";
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<ContestProblem> problemList = new ArrayList<>();
    private List<String> tab_title  = new ArrayList<>();
    private int positionItem;
    private FloatingActionMenu floatingActionMenu;
    private FloatingActionButton submitBt;
    private FloatingActionButton historyBt;
    private String type = "contestProblem";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contest_problemlist_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String problems = getArguments().getString("contest_detail");
        problemList = JSON.parseObject(problems, new TypeReference<List<ContestProblem>>(){});
        for (int i = 0; i<problemList.size(); i++){
            tab_title.add(problemList.get(i).getOrderCharacter());
        }
        initData();
        viewPager = view.findViewById(R.id.view_pager_contest_problem_list);
        tabLayout = view.findViewById(R.id.tab_layout_contest_problem_list);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(), fragmentList, tab_title);
        tabLayout.getSelectedTabPosition();
        viewPager.setOffscreenPageLimit(problemList.size()-1);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        positionItem = tabLayout.getSelectedTabPosition();
        initFloatingButton(view);
    }


    private void initFloatingButton(View view) {

        floatingActionMenu = view.findViewById(R.id.contest_problem_detail_fragment_floating_button_menu);
        submitBt = view.findViewById(R.id.contest_problem_submit_button);
        historyBt = view.findViewById(R.id.contest_problem_submit_history_button);
        submitBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                CodeFragment fragment = new CodeFragment(problemList.get(positionItem).getProblemId(),type);
                transaction.add(R.id.code_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        historyBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                ContestStatusFrg fragment = new ContestStatusFrg(getContext(), "problem_status_fragment");
                new ProblemStatusListData(getContext(),problemList.get(positionItem).getProblemId()).setUpList(fragment);
                transaction.replace(R.id.code_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    public void initData(){
        for (int i = 0; i<problemList.size(); i++){
            ContestProblemFrg fragment = new ContestProblemFrg();
            Bundle bundle = new Bundle();
            bundle.putString("contest_problem_list_fragment", JSON.toJSONString(problemList.get(i)));
            fragment.setArguments(bundle);
            fragmentList.add(fragment);
        }
    }
}
