package cn.edu.uestc.acm.cdoj;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import cn.edu.uestc.acm.cdoj.net.ConvertNetData;
import cn.edu.uestc.acm.cdoj.net.NetDataPlus;
import cn.edu.uestc.acm.cdoj.net.Result;
import cn.edu.uestc.acm.cdoj.tools.DrawImage;
import cn.edu.uestc.acm.cdoj.tools.RGBAColor;
import cn.edu.uestc.acm.cdoj.ui.MainUIActivity;
import cn.edu.uestc.acm.cdoj.ui.modules.list.SearchHistoryManager;
import cn.edu.uestc.acm.cdoj.ui.user.User;

public class MainActivity extends Activity implements ConvertNetData {

    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_launch);
        startMainUIActivity();
//        setupLaunchCartoon();
        setupDefaultColorMatrix();
        readHTMLFile();
        setupIcon();
        readSearchHistoriesFile();
        Resource.setDefaultLogo(new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.logo)));
        Resource.setFilesDirPath(getFilesDir() + File.separator);
        Resource.setCacheDirPath(getCacheDir() + File.separator);
        loadLocalUser();
    }

    private void setupLaunchCartoon() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        ImageView mImageView = (ImageView) findViewById(R.id.launch_image);
        mImageView.setImageResource(R.drawable.launch);
    }

    private void startMainUIActivity() {
        Intent mainUIActivityIntent = new Intent(MainActivity.this, MainUIActivity.class);
        startActivity(mainUIActivityIntent);
    }

    private void readHTMLFile() {
        try {
            InputStream input;
            byte[] in;

            input = getResources().getAssets().open("articleRender.html");
            in = new byte[input.available()];
            input.read(in);
            Resource.setHtmlData_article(new String(in));

            input = getResources().getAssets().open("problemRender.html");
            in = new byte[input.available()];
            input.read(in);
            Resource.setHtmlData_problem(new String(in));

            input = getResources().getAssets().open("contestOverviewRender.html");
            in = new byte[input.available()];
            input.read(in);
            Resource.setHtmlData_contest(new String(in));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupDefaultColorMatrix() {
        Resource.setMainColorMatrix(new float[]{
                0, 0, 0, 0, 255,
                0, 0, 0, 0, 166,
                0, 0, 0, 0, 0,
                0, 0, 0, 1, 0});
    }

    private void setupIcon() {
        setupMainListTabIcons();
        setupGestureLoadListIcons();
        setupRankProblemIcons();
        setupProblemButtonIcons();
    }

    private void setupMainListTabIcons() {
        Resource.setNoticeListIcon_selected(DrawImage.draw(this, R.drawable.ic_list_notice_selected));
        Resource.setNoticeListIcon_unselect(DrawImage.draw(this, R.drawable.ic_list_notice_unselect));
        Resource.setProblemListIcon_selected(DrawImage.draw(this, R.drawable.ic_list_problem_selected));
        Resource.setProblemListIcon_unselect(DrawImage.draw(this, R.drawable.ic_list_problem_unselect));
        Resource.setContestListIcon_selected(DrawImage.draw(this, R.drawable.ic_list_contestt_selected));
        Resource.setContestListIcon_unselect(DrawImage.draw(this, R.drawable.ic_list_contest_unselect));
    }

    private void setupRankProblemIcons() {
        Resource.setRankIcon_didNothing(DrawImage.draw(this, R.drawable.contest_rank_mark_bg_white,
                RGBAColor.getColorMatrix(this, R.color.rank_didNothing, false)));

        Resource.setRankIcon_tried(DrawImage.draw(this, R.drawable.contest_rank_mark_bg_white,
                RGBAColor.getColorMatrix(this, R.color.rank_tried, false)));

        Resource.setRankIcon_solved(DrawImage.draw(this, R.drawable.contest_rank_mark_bg_white,
                RGBAColor.getColorMatrix(this, R.color.rank_solved, false)));

        Resource.setRankIcon_theFirstSolved(DrawImage.draw(this, R.drawable.contest_rank_mark_bg_white,
                RGBAColor.getColorMatrix(this, R.color.rank_theFirstSolved, false)));
    }

    private void setupGestureLoadListIcons() {
        Resource.setListIcon_up(DrawImage.draw(this, R.drawable.ic_arrow_upward_white_48dp,
                RGBAColor.getColorMatrixWithPercentAlpha(this, R.color.main_yellow, 0.5f, false)));

        Resource.setListFooterIcon_noData(DrawImage.draw(this, R.drawable.ic_notifications_none_white_36dp));
        Resource.setListFooterIcon_problem(DrawImage.draw(this, R.drawable.ic_sync_problem_white));
        Resource.setListFooterIcon_done(DrawImage.draw(this, R.drawable.ic_done_white));
        Resource.setListFooterIcon_netProblem(DrawImage.draw(this, R.drawable.ic_sync_disabled_white));
    }

    private void setupProblemButtonIcons() {
        Resource.setProblemIcon_addCode(DrawImage.draw(this, R.drawable.ic_create_white_48dp));
        Resource.setProblemIcon_checkResult(DrawImage.draw(this, R.drawable.ic_flag_white_48dp));
    }

    private void readSearchHistoriesFile() {
        Resource.setProblemSearchHistory(SearchHistoryManager.getAllHistories("problem"));

        Resource.setContestSearchHistory(SearchHistoryManager.getAllHistories("contest"));
    }

    private void loadLocalUser() {
        File file = new File(Resource.getFilesDirPath() + "user");
        if (!file.exists()) {
            finish();
            return;
        }
        try {
            Scanner input = new Scanner(file);
            Resource.setUser(new User(input.nextLine(), input.nextLine()));
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        NetDataPlus.login(this, Resource.getUser().getUserName(), Resource.getUser().getPasswordSHA1(), true, this);
    }

    @NonNull
    @Override
    public Result onConvertNetData(String jsonString, Result result) {
        User user = JSON.parseObject(jsonString, User.class);
        if (user.getResult().equals("success")) {
            result.setStatus(Result.SUCCESS);
            result.setContent(user);
        } else {
            result.setStatus(Result.FALSE);
        }
        return result;
    }

    @Override
    public void onNetDataConverted(Result result) {
        finish();
    }
}
