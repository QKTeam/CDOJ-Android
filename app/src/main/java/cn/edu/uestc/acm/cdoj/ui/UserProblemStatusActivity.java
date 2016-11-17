package cn.edu.uestc.acm.cdoj.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.ui.user.UserProblemStatus;

/**
 * Created by 13662 on 2016/11/1.
 */

public class UserProblemStatusActivity extends AppCompatActivity{
    private LinearLayout container;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_problem_status_container);
        container = (LinearLayout) findViewById(R.id.container_user_problem_status);
        container.addView(new UserProblemStatus(this));
        toolbar = (Toolbar) findViewById(R.id.toolbar_status);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setTitle("完成情况");
        setSupportActionBar(toolbar);
    }
}
