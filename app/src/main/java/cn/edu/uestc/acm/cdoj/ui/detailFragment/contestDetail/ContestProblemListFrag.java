package cn.edu.uestc.acm.cdoj.ui.detailFragment.contestDetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.List;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.contest.problem.ContestProblem;
import cn.edu.uestc.acm.cdoj.ui.ViewPagerAdapter;

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
        viewPager.setOffscreenPageLimit(problemList.size()-1);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
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
