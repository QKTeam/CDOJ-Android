package cn.edu.uestc.acm.cdoj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import cn.edu.uestc.acm.cdoj.tools.DrawImage;
import cn.edu.uestc.acm.cdoj.tools.RGBAColor;
import cn.edu.uestc.acm.cdoj.ui.modules.Global;
import cn.edu.uestc.acm.cdoj.ui.modules.list.SearchHistoryManager;
import cn.edu.uestc.acm.cdoj.ui.user.User;

public class MainActivity extends Activity {

    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startLaunchActivity();
        readHTMLFile();
        setupDefaultColorMatrix();
        setupStatusIcons();
        setupListFooterIcons();
        readSearchHistoriesFile();
        Global.filesDirPath = getFilesDir() + File.separator;
        loadLocalUser();
        EditText editText = new EditText(this);

        finish();
    }

    private void startLaunchActivity() {
        Intent launchActivityIntent = new Intent(this, LaunchCartoonActivity.class);
        startActivity(launchActivityIntent);
    }

    private void readHTMLFile() {
        try {
            InputStream input;
            byte[] in;
            input = getResources().getAssets().open("articleRender.html");
            in = new byte[input.available()];
            input.read(in);
            Global.HTMLDATA_ARTICLE = new String(in);
            input = getResources().getAssets().open("problemRender.html");
            in = new byte[input.available()];
            input.read(in);
            Global.HTMLDATA_PROBLEM = new String(in);
            input = getResources().getAssets().open("contestOverviewRender.html");
            in = new byte[input.available()];
            input.read(in);
            Global.HTMLDATA_CONTEST = new String(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupDefaultColorMatrix() {
        Global.mainColorMatrix = new float[]{
                0, 0, 0, 0, 255,
                0, 0, 0, 0, 166,
                0, 0, 0, 0, 0,
                0, 0, 0, 1, 0};
    }

    private void setupStatusIcons() {
        Global.rankIcon_didNothing = DrawImage.draw(this, R.drawable.contest_rank_mark_bg_white,
                RGBAColor.getColorMatrix(this, R.color.rank_didNothing, false));
        Global.rankIcon_tried = DrawImage.draw(this, R.drawable.contest_rank_mark_bg_white,
                RGBAColor.getColorMatrix(this, R.color.rank_tried, false));
        Global.rankIcon_solved = DrawImage.draw(this, R.drawable.contest_rank_mark_bg_white,
                RGBAColor.getColorMatrix(this, R.color.rank_solved, false));
        Global.rankIcon_theFirstSolved = DrawImage.draw(this, R.drawable.contest_rank_mark_bg_white,
                RGBAColor.getColorMatrix(this, R.color.rank_theFirstSolved, false));
    }

    private void setupListFooterIcons() {
        Global.listFooterIcon_noData = DrawImage.draw(this, R.drawable.ic_notifications_none_white_36dp, true);
        Global.listFooterIcon_problem = DrawImage.draw(this, R.drawable.ic_sync_problem_white, true);
        Global.listFooterIcon_done = DrawImage.draw(this, R.drawable.ic_done_white, true);
        Global.listFooterIcon_netProblem = DrawImage.draw(this, R.drawable.ic_sync_disabled_white, true);
    }

    private void readSearchHistoriesFile() {
        Global.problemSearchHistory = SearchHistoryManager.getAllHistories("problem");
        Global.contestSearchHistory = SearchHistoryManager.getAllHistories("contest");
    }

    private void loadLocalUser() {
        File file = new File(Global.filesDirPath + "user");
        if (!file.exists()) {
            return;
        }
        try {
            Scanner input = new Scanner(file);
            Global.user = new User(input.nextLine(), input.nextLine());
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
