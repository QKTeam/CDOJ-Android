package cn.edu.uestc.acm.cdoj;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

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
        finish();
    }
}
