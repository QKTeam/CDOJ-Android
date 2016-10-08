package cn.edu.uestc.acm.cdoj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import cn.edu.uestc.acm.cdoj.ui.MainUIActivity;

public class LaunchCartoonActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        ImageView mImageView = (ImageView) findViewById(R.id.launch_image);
        mImageView.setImageResource(R.drawable.launch);
        final TextView mTextView = (TextView) findViewById(R.id.launch_text);
        final CountDownTimer mCountDownTimer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTextView.setText(millisUntilFinished / 1000 + "S");
            }
            @Override
            public void onFinish() {
                Intent mainUIActivityIntent = new Intent(LaunchCartoonActivity.this, MainUIActivity.class);
                startActivity(mainUIActivityIntent);
                finish();
            }
        };
        /*mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainUIActivityIntent = new Intent(LaunchCartoonActivity.this, MainUIActivity.class);
                startActivity(mainUIActivityIntent);
                mCountDownTimer.cancel();
                mCountDownTimer.onFinish();
            }
        });*/
        mCountDownTimer.start();
    }
}
