package cn.edu.uestc.acm.cdoj;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import cn.edu.uestc.acm.cdoj.net.user.LoginFragment;
import cn.edu.uestc.acm.cdoj.net.user.Register_Step1_Fragment;
import cn.edu.uestc.acm.cdoj.net.user.Register_Step2_Fragment;
import cn.edu.uestc.acm.cdoj.net.user.UserInfoFragment;

public class UserInfoActivity extends AppCompatActivity implements Register_Step1_Fragment.SendDataCallback {

    private static final String TAG = "LoginActivity";
    public static boolean isLogin = false;
    private  FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_userinfo);
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.userinfo_container,new LoginFragment());
//        if (isLogin){
//            transaction.add(R.id.userinfo_container,new UserInfoFragment());
//        }else {
//            transaction.add(R.id.userinfo_container,new LoginFragment());
//        }
        transaction.commit();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        isLogin = intent.getBooleanExtra("isLogin",false);
    }

    @Override
    public void onDataReceived(String[] register_info_simple_info) {
        Register_Step2_Fragment register_step2_fragment = new Register_Step2_Fragment();
        register_step2_fragment.setRegister_info_simple_info(register_info_simple_info);
        transaction.replace(R.id.userinfo_container,register_step2_fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
