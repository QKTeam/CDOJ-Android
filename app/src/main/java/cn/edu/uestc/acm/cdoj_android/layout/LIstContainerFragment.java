package cn.edu.uestc.acm.cdoj_android.layout;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Map;

import cn.edu.uestc.acm.cdoj_android.R;

/**
 * Created by great on 2016/8/18.
 */
public class ListContainerFragment extends Fragment {
    public final static int article = 0;
    public final static int problem = 1;
    public final static int contest = 2;
    ListFragmentWithGestureLoad[] list_Fragment;
    ViewPager listContainer_ViewPager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        list_Fragment = new ListFragmentWithGestureLoad[3];
        for (int i = 0; i != 3; ++i) {
            switch (i) {
                case 0:
                    list_Fragment[0] = new ArticleListFragment();
                    break;
                case 1:
                    list_Fragment[1] = new ProblemListFragment();
                    break;
                case 2:
                    list_Fragment[2] =new ContestListFragment();
            }
        }
        return inflater.inflate(R.layout.list_container,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listContainer_ViewPager = (ViewPager) (getView().findViewById(R.id.listViewPager));
        listContainer_ViewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0:
                        return list_Fragment[0];
                    case 1:
                        return list_Fragment[1];
                    case 2:
                        return list_Fragment[2];
                    default:
                        return null;
                }
            }
            @Override
            public int getCount() {
                return 3;
            }
        });
    }

    /*public ViewPager getViewPager() {
        return listContainer_ViewPager;
    }*/

    public void addListItem(int which, Map<String ,String> listItem) {
        list_Fragment[which].addListItem(listItem);
    }

    public void notifyDataSetChanged(int which) {
        list_Fragment[which].notifyDataSetChanged();
    }

    public void setCurrentItem(int item) {
        if (listContainer_ViewPager != null) {
            listContainer_ViewPager.setCurrentItem(item, false);
        }
    }
}
