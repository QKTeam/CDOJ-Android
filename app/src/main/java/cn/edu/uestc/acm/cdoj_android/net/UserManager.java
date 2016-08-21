package cn.edu.uestc.acm.cdoj_android.net;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by qwe on 16-8-21.
 */
public class UserManager {
    Context context;
    SharedPreferences sp;

    public UserManager(Context c) {
        this.context = c;
        sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
    }
    public boolean isLogin(){
        return sp.getBoolean("isLogin", false);
    }
    public void keepLogin(final String userName, final String password){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                NetData.login(userName, password, null);
            }
        }, 0 , 20*60*1000);
    }
    public void keepLogin(){
        if (isLogin()) {
            keepLogin(sp.getString("userName", ""), sp.getString("password", ""));
        }
    }
    public void setUser(String userName, String password){
        sp.edit().putString("userName", userName).putString("password", password).putBoolean("isLogin", true).commit();
        keepLogin(userName, password);
    }
}
