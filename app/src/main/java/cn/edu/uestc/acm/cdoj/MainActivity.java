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
import cn.edu.uestc.acm.cdoj.ui.modules.Global;
import cn.edu.uestc.acm.cdoj.ui.modules.list.SearchHistoryManager;
import cn.edu.uestc.acm.cdoj.ui.user.User;

public class MainActivity extends Activity implements ConvertNetData{

    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_launch);
        startMainUIActivity();
//        setupLaunchCartoon();
        setupDefaultColorMatrix();
        readHTMLFile();
        setupRankProblemIcons();
        setupListFooterIcons();
        readSearchHistoriesFile();
        Global.setDefaultLogo(new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.logo)));
        Global.setFilesDirPath(getFilesDir() + File.separator);
        Global.setCacheDirPath(getCacheDir() + File.separator);
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
            Global.setHtmldataArticle(new String(in));

            input = getResources().getAssets().open("problemRender.html");
            in = new byte[input.available()];
            input.read(in);
            Global.setHtmldataProblem(new String(in));

            input = getResources().getAssets().open("contestOverviewRender.html");
            in = new byte[input.available()];
            input.read(in);
            Global.setHtmldataContest(new String(in));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupDefaultColorMatrix() {
        Global.setMainColorMatrix(new float[]{
                0, 0, 0, 0, 255,
                0, 0, 0, 0, 166,
                0, 0, 0, 0, 0,
                0, 0, 0, 1, 0});
    }

    private void setupRankProblemIcons() {
        Global.setRankIcon_didNothing(new BitmapDrawable(getResources(),
                DrawImage.draw(this, R.drawable.contest_rank_mark_bg_white,
                        RGBAColor.getColorMatrix(this, R.color.rank_didNothing, false))));

        Global.setRankIcon_tried(new BitmapDrawable(getResources(),
                DrawImage.draw(this, R.drawable.contest_rank_mark_bg_white,
                        RGBAColor.getColorMatrix(this, R.color.rank_tried, false))));

        Global.setRankIcon_solved(new BitmapDrawable(getResources(),
                DrawImage.draw(this, R.drawable.contest_rank_mark_bg_white,
                        RGBAColor.getColorMatrix(this, R.color.rank_solved, false))));

        Global.setRankIcon_theFirstSolved(new BitmapDrawable(getResources(),
                DrawImage.draw(this, R.drawable.contest_rank_mark_bg_white,
                        RGBAColor.getColorMatrix(this, R.color.rank_theFirstSolved, false))));
    }

    private void setupListFooterIcons() {
        Global.setListIcon_up(new BitmapDrawable(getResources(),
                DrawImage.draw(this, R.drawable.ic_arrow_upward_white_48dp,
                RGBAColor.getColorMatrixWithPercentAlpha(this, R.color.main_yellow, 0.5f, false))));

        Global.setListFooterIcon_noData(new BitmapDrawable(getResources(),
                DrawImage.draw(this, R.drawable.ic_notifications_none_white_36dp, true)));

        Global.setListFooterIcon_problem(new BitmapDrawable(getResources(),
                DrawImage.draw(this, R.drawable.ic_sync_problem_white, true)));

        Global.setListFooterIcon_done(new BitmapDrawable(getResources(),
                DrawImage.draw(this, R.drawable.ic_done_white, true)));

        Global.setListFooterIcon_netProblem(new BitmapDrawable(getResources(),
                DrawImage.draw(this, R.drawable.ic_sync_disabled_white, true)));
    }

    private void readSearchHistoriesFile() {
        Global.setProblemSearchHistory(SearchHistoryManager.getAllHistories("problem"));

        Global.setContestSearchHistory(SearchHistoryManager.getAllHistories("contest_button_container"));
    }

    private void loadLocalUser() {
        File file = new File(Global.getFilesDirPath() + "user");
        if (!file.exists()) {
            finish();
            return;
        }
        try {
            Scanner input = new Scanner(file);
            Global.setUser(new User(input.nextLine(), input.nextLine()));
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "loadLocalUser: "+Global.getUser().getUserName()+"  "+Global.getUser().getPasswordSHA1());
        NetDataPlus.login(this, Global.getUser().getUserName(), Global.getUser().getPasswordSHA1(), true, this);
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
