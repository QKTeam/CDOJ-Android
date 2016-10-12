package cn.edu.uestc.acm.cdoj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.io.IOException;
import java.io.InputStream;

import cn.edu.uestc.acm.cdoj.tools.DrawImage;
import cn.edu.uestc.acm.cdoj.tools.RGBAColor;
import cn.edu.uestc.acm.cdoj.ui.modules.Global;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent launchActivityIntent = new Intent(this, LaunchCartoonActivity.class);
        startActivity(launchActivityIntent);
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
        Global.mainColorMatrix = new float[]{
                0, 0, 0, 0, 255,
                0, 0, 0, 0, 166,
                0, 0, 0, 0, 0,
                0, 0, 0, 1, 0};
        setupStatusIcons();
        setupListFooterIcons();
        finish();
    }

    private void setupStatusIcons() {
        Global.didNothingIcon = DrawImage.draw(this, R.drawable.contest_rank_mark_bg_white,
                RGBAColor.getColorMatrix(this, R.color.rank_didNothing, false));
        Global.triedIcon = DrawImage.draw(this, R.drawable.contest_rank_mark_bg_white,
                RGBAColor.getColorMatrix(this, R.color.rank_tried, false));
        Global.solvedIcon = DrawImage.draw(this, R.drawable.contest_rank_mark_bg_white,
                RGBAColor.getColorMatrix(this, R.color.rank_solved, false));
        Global.theFirstSolvedIcon = DrawImage.draw(this, R.drawable.contest_rank_mark_bg_white,
                RGBAColor.getColorMatrix(this, R.color.rank_theFirstSolved, false));
    }

    private void setupListFooterIcons() {
        Global.listFootericon_noData = DrawImage.draw(this, R.drawable.ic_sync_disabled_white, true);
        Global.listFootericon_problem = DrawImage.draw(this, R.drawable.ic_sync_problem_white, true);
        Global.listFootericon_done = DrawImage.draw(this, R.drawable.ic_done_white, true);
    }
}
