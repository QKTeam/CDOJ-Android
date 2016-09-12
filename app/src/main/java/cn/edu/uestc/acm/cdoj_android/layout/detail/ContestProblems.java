package cn.edu.uestc.acm.cdoj_android.layout.detail;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cn.edu.uestc.acm.cdoj_android.R;
import cn.edu.uestc.acm.cdoj_android.net.ViewHandler;
import cn.edu.uestc.acm.cdoj_android.net.data.Problem;

/**
 * Created by great on 2016/8/25.
 */
public class ContestProblems extends Fragment {
    private View rootView;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private int problemsCount;
    private DetailWebViewFragment[] problems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            rootView = inflater.inflate(R.layout.contest_problems, container, false);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            viewPager = (ViewPager) rootView.findViewById(R.id.contestProblemsViewPager);
            if (problems != null) {
                setAdapter();
            }
        }

    }

    public void addProblems(ArrayList<Problem> problemList) {
        problemsCount = problemList.size();
        problems = new DetailWebViewFragment[problemsCount];
        for (int i = 0; i != problemsCount; ++i) {
            problems[i] = (new DetailWebViewFragment()).switchHTMLData(ViewHandler.PROBLEM_DETAIL);
            problems[i].addJSData(problemList.get(i).getContentString());
        }
        if (viewPager != null) {
            setAdapter();
        }
    }

    private void setAdapter() {
        viewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return problems[position];
            }

            @Override
            public int getCount() {
                return problemsCount;
            }
        });
    }
}
