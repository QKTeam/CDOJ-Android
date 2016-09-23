package cn.edu.uestc.acm.cdoj_android.layout;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import cn.edu.uestc.acm.cdoj_android.R;
import cn.edu.uestc.acm.cdoj_android.layout.detail.ArticleFragment;
import cn.edu.uestc.acm.cdoj_android.layout.detail.ContestFragment;
import cn.edu.uestc.acm.cdoj_android.layout.detail.ProblemFragment;
import cn.edu.uestc.acm.cdoj_android.net.ViewHandler;

/**
 * Created by great on 2016/8/18.
 */
public class DetailsContainer extends Fragment {
    private ArticleFragment article;
    private ProblemFragment problem;
    private ContestFragment contest;
    private View rootView;
    private User user;
    private FragmentManager fragmentManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);
        fragmentManager = getChildFragmentManager();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            article = new ArticleFragment();
            problem = new ProblemFragment();
            contest = new ContestFragment();
            user = new User();
            addParts();
            rootView = inflater.inflate(R.layout.detail_container, container, false);
        }
        return rootView;
    }

    private void addParts() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.details_container, article);
        transaction.add(R.id.details_container, problem);
        transaction.add(R.id.details_container, contest);
        transaction.add(R.id.details_container, user);
        transaction.commit();
        hideAll();
    }

    private void hideAll() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(article);
        transaction.hide(problem);
        transaction.hide(contest);
        transaction.hide(user);
        transaction.commit();
    }

    public void show(int which) {
        hideAll();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (which) {
            case ViewHandler.ARTICLE_DETAIL:
                transaction.show(article);
                break;
            case ViewHandler.PROBLEM_DETAIL:
                transaction.show(problem);
                break;
            case ViewHandler.CONTEST_DETAIL:
                transaction.show(contest);
                break;
            case ViewHandler.USER:
                transaction.show(user);
        }
        transaction.commit();
    }

    @IntDef({ViewHandler.ARTICLE_DETAIL, ViewHandler.PROBLEM_DETAIL,
            ViewHandler.CONTEST_DETAIL, ViewHandler.USER})
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
            case ViewHandler.USER:
                return user;
        }
        return null;
    }
}
