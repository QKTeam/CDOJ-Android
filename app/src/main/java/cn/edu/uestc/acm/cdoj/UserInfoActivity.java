package cn.edu.uestc.acm.cdoj;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import cn.edu.uestc.acm.cdoj.net.user.LoginFragment;

public class UserInfoActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_userinfo);
        setSupportActionBar(toolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.userinfo_container,new LoginFragment());
        transaction.commit();
    }

}
