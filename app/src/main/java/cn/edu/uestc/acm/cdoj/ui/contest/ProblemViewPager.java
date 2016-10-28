package cn.edu.uestc.acm.cdoj.ui.contest;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import cn.edu.uestc.acm.cdoj.net.NetData;
import cn.edu.uestc.acm.cdoj.net.data.ProblemData;
import cn.edu.uestc.acm.cdoj.ui.modules.detail.DetailWebView;

/**
 * Created by great on 2016/8/25.
 */
public class ProblemViewPager extends ViewPager{
    private TabLayout mTabLayout;
    private List<DetailWebView> problemViewList;
    private PagerAdapter mAdapter;
    private ArrayList<ProblemData> problemDataList;

    public ProblemViewPager(Context context) {
        super(context);
        init();
    }

    public ProblemViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProblemViewPager(Context context, ArrayList<ProblemData> problemDataList) {
        super(context);
        this.problemDataList = problemDataList;
        init();
    }

    public void init() {
        problemViewList = new ArrayList<>();
        mTabLayout = new TabLayout(getContext());
        setupTabLayout();
        addView(mTabLayout);
        setOffscreenPageLimit(4);
        setAdapter(setupAdapter());
        setProblemDataList(problemDataList);
    }

    private void setupTabLayout() {
        LayoutParams layoutParams = new LayoutParams();
        layoutParams.gravity = Gravity.TOP;
        layoutParams.isDecor = true;
        layoutParams.height = LayoutParams.WRAP_CONTENT;
        mTabLayout.setLayoutParams(layoutParams);
        mTabLayout.setupWithViewPager(this, true);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    public void setProblemDataList(ArrayList<ProblemData> problemDataList) {
        if (problemDataList == null) return;
        for (ProblemData problemData : problemDataList) {
            DetailWebView problemView = new DetailWebView(getContext(), NetData.PROBLEM_DETAIL)
                    .setJsonString(problemData.jsonString);
            problemViewList.add(problemView);
        }
        mAdapter.notifyDataSetChanged();
        setupTab();
    }

    private void setupTab() {
        for (int i = 0; i < mTabLayout.getTabCount(); ++i) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (tab != null) {
                tab.setText(String.valueOf((char) ('A' + i)));
            }
        }
    }

    private PagerAdapter setupAdapter() {
        mAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return problemViewList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(problemViewList.get(position));
                return problemViewList.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(problemViewList.get(position));
            }
        };
        return mAdapter;
    }
}
