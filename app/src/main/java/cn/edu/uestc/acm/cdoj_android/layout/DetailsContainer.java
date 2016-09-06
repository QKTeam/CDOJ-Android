package cn.edu.uestc.acm.cdoj_android.layout;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import cn.edu.uestc.acm.cdoj_android.Global;
import cn.edu.uestc.acm.cdoj_android.R;
import cn.edu.uestc.acm.cdoj_android.layout.detail.ArticleUI;
import cn.edu.uestc.acm.cdoj_android.layout.detail.ContestUI;
import cn.edu.uestc.acm.cdoj_android.layout.detail.ProblemUI;
import cn.edu.uestc.acm.cdoj_android.net.ViewHandler;

/**
 * Created by great on 2016/8/18.
 */
public class DetailsContainer extends Fragment {
    private ArticleUI article;
    private ProblemUI problem;
    private ContestUI contest;
    private ViewPager viewPager;
    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            article = new ArticleUI();
            problem = new ProblemUI();
            contest = new ContestUI();
            Global.netContent.addDetail(article);
            Global.netContent.addDetail(problem);
            Global.netContent.addDetail(contest);
            rootView = inflater.inflate(R.layout.detail_container, container, false);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            viewPager = (ViewPager) (rootView.findViewById(R.id.detailsViewPager));
            viewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
                @Override
                public Fragment getItem(int position) {
                    switch (position) {
                        case 0:
                            return article;
                        case 1:
                            return problem;
                        case 2:
                            return contest;
                    }
                    return null;
                }
                @Override
                public int getCount() {
                    return 3;
                }
            });
        }
    }

    public void setCurrentItem(int position) {
        if (viewPager != null) {viewPager.setCurrentItem(position, false);}
    }

    public void setCurrentItem(int position, boolean scroll) {
        if (viewPager != null) {viewPager.setCurrentItem(position, scroll);}
    }

    @IntDef({ViewHandler.ARTICLE_DETAIL, ViewHandler.PROBLEM_DETAIL, ViewHandler.CONTEST_DETAIL})
    @Retention(RetentionPolicy.SOURCE)
    @interface details {}

    public Fragment getDetail(@details int which) {
        switch (which) {
            case ViewHandler.ARTICLE_DETAIL:
                return article;
            case ViewHandler.PROBLEM_DETAIL:
                return problem;
            case ViewHandler.CONTEST_DETAIL:
                return contest;
        }
        return null;
    }
}
