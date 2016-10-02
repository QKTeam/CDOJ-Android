package cn.edu.uestc.acm.cdoj.layout;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.layout.detail.article.ArticleFragment;
import cn.edu.uestc.acm.cdoj.layout.detail.contest.ContestFragment;
import cn.edu.uestc.acm.cdoj.layout.detail.problem.ProblemFragment;
import cn.edu.uestc.acm.cdoj.net.ViewHandler;

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
    private ImageView bgImage;
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
            rootView = inflater.inflate(R.layout.detail_container, container, false);
            bgImage = (ImageView) rootView.findViewById(R.id.details_container_bg);
            configureBGImage();
            article = new ArticleFragment();
            problem = new ProblemFragment();
            contest = new ContestFragment();
            user = new User();
            addParts();
        }
        return rootView;
    }

    private void configureBGImage() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo_orange);
        Bitmap afterBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(afterBitmap);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(new float[]{
                1, 0, 0, 0, 0,
                0, 1, 0, 0, 0,
                0, 0, 1, 0, 0,
                0, 0, 0, 0.7f, 0}));
        canvas.drawBitmap(bitmap, new Matrix(), paint);
        bgImage.setImageBitmap(afterBitmap);
    }


    private void addParts() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.details_container, article);
        transaction.add(R.id.details_container, problem);
        transaction.add(R.id.details_container, contest);
        transaction.add(R.id.details_container, user);
        transaction.commit();
        show(ViewHandler.ARTICLE_DETAIL);
    }

    private void hideAll() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(article);
        transaction.hide(problem);
        transaction.hide(contest);
        transaction.hide(user);
        transaction.commit();
    }

    @IntDef({ViewHandler.ARTICLE_DETAIL, ViewHandler.PROBLEM_DETAIL,
            ViewHandler.CONTEST_DETAIL, ViewHandler.USER})
    @Retention(RetentionPolicy.SOURCE)
    @interface details {}

    public void show(@details int which) {
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
