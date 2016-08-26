package cn.edu.uestc.acm.cdoj_android.net;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by qwe on 16-8-21.
 */
public class UserManager implements ViewHandler{
    Context context;
    SharedPreferences sp;
    ViewHandler viewHandler;

    public UserManager(Context c) {
        this.context = c;
        sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
    }
    public boolean isLogin(){
        return sp.getBoolean("isLogin", false);
    }
    public void keepLogin(final String userName, final String sha1password, boolean im){
        int s = im?0:20*60*1000;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                NetData.login(userName, sha1password, null);
            }
        }, s , 20*60*1000);
    }
    public void keepLogin(){
        if (isLogin()) {
            keepLogin(sp.getString("userName", ""), sp.getString("password", ""), true);
        }
    }
    void setUser(String userName, String sha1password){
        sp.edit().putString("userName", userName).putString("password", sha1password).putBoolean("isLogin", true).commit();
        keepLogin(userName, sha1password, false);
    }
    public void login(String userName, String password, ViewHandler viewHandler){
        NetData.login(this.userName = userName, this.sha1password = NetWorkTool.sha1(password), this);
        this.viewHandler = viewHandler;
    }
    String userName, sha1password;
    public void getAvatar(String email, Object addition, ViewHandler viewHandler){

    }
    @Override
    public void show(int which, Object data, long time) {
        if ((boolean)data) {
            setUser(userName, sha1password);
        }
        viewHandler.show(which, data, time);
    }
}
