package cn.edu.uestc.acm.cdoj_android.layout;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import java.io.IOException;
import java.io.InputStream;

import cn.edu.uestc.acm.cdoj_android.R;
import cn.edu.uestc.acm.cdoj_android.Selection;

/**
 * Created by great on 2016/8/15.
 */
public class DetailsContainerFragment extends Fragment {
    MyWebViewFragment[] details_Fragment;
    ViewPager detailsContainer_ViewPager;
    Selection selection;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        details_Fragment = new MyWebViewFragment[3];
        for (int i = 0; i != 3; ++i) {
            details_Fragment[i] = new MyWebViewFragment();
            try {
                InputStream input;
                byte[] in;
                switch (i) {
                    case 0:
                        input = getResources().getAssets().open("articleRender.html");
                        in = new byte[input.available()];
                        input.read(in);
                        details_Fragment[i].addHTMLData(new String(in));
                        break;
                    case 1:
                       input = getResources().getAssets().open("problemRender.html");
                        in = new byte[input.available()];
                        input.read(in);
                        details_Fragment[i].addHTMLData(new String(in));
                        break;
                    case 2:
                        input = getResources().getAssets().open("contestOverviewRender.html");
                        in = new byte[input.available()];
                        input.read(in);
                        details_Fragment[i].addHTMLData(new String(in));
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return inflater.inflate(R.layout.details_container_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        detailsContainer_ViewPager = (ViewPager)getView();
        detailsContainer_ViewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0:
                        return details_Fragment[0];
                    case 1:
                        return details_Fragment[1];
                    case 2:
                        return details_Fragment[2];
                    default:
                        return null;
                }
            }
            @Override
            public int getCount() {
                return 3;
            }
        });
        detailsContainer_ViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0: selection.setSelectionList(0);break;
                    case 1: selection.setSelectionList(1);break;
                    case 2: selection.setSelectionList(2);break;
//                    default: setSelection(3);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
        selection.initTab(detailsContainer_ViewPager);
    }

    public void addSelection(Selection selection) {
        this.selection = selection;
    }

    public void addJSData(int which, String webData) {
        details_Fragment[which].addJSData(webData);
    }
}
