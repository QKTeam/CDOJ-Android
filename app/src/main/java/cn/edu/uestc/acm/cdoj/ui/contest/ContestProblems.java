package cn.edu.uestc.acm.cdoj.ui.contest;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.NetData;
import cn.edu.uestc.acm.cdoj.net.data.Problem;
import cn.edu.uestc.acm.cdoj.ui.modules.detail.DetailWebView;

/**
 * Created by great on 2016/8/25.
 */
public class ContestProblems extends Fragment{
    private View rootView;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private int problemsCount;
    private DetailWebView[] problems;
    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
            mViewPager = (ViewPager) rootView.findViewById(R.id.contestProblems_viewPager);
            mViewPager.setOffscreenPageLimit(4);
            tabLayout = (TabLayout) rootView.findViewById(R.id.contestProblems_tabLayout);
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            if (problems != null) setViewPagerAdapter();
        }
    }

    public void addProblems(ArrayList<Problem> problemList) {
        problemsCount = problemList.size();
        problems = new DetailWebView[problemsCount];
        for (int i = 0; i != problemsCount; ++i) {
            problems[i] = new DetailWebView(context, NetData.PROBLEM_DETAIL)
                    .addJSData(problemList.get(i).obtainJsonString());
        }
        if (mViewPager != null) setViewPagerAdapter();
    }

    private void setViewPagerAdapter() {
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return problemsCount;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(problems[position]);
                return problems[position];
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(problems[position]);
            }
        });
        tabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i != problemsCount; ++i) {
            tabLayout.getTabAt(i).setText(String.valueOf((char)('A' + i)));
        }
    }
}
