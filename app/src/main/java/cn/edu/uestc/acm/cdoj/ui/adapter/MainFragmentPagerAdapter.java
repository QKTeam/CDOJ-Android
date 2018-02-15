package cn.edu.uestc.acm.cdoj.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by lagranmoon on 2018/2/7.
 * 首页ViewPager的Adapter
 */

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitle = new String[]{"公告","题目","比赛","我"};
    private List<Fragment> mFragmetList;

    public MainFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.mFragmetList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmetList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmetList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position];
    }
}
