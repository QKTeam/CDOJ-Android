package cn.edu.uestc.acm.cdoj.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by lagranmoon on 2017/7/18.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> tabList;
    private List<String> tab_item;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> tabList, List<String> tab_item) {
        super(fm);
        this.tabList = tabList;
        this.tab_item = tab_item;
    }


    @Override
    public Fragment getItem(int position) {
        return tabList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tab_item.get(position%tab_item.size());
    }

    @Override
    public int getCount() {
        return tabList.size();
    }
}
