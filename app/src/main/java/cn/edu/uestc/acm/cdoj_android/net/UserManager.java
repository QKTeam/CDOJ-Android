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

    String userName, sha1password;
    public UserManager(Context c) {
        this.context = c;
        sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
    }
    public boolean isLogin(){
        return sp.getBoolean("isLogin", false);
    }
    void keepLogin(final String userName, final String sha1password, boolean im){
        int s = im?0:20*60*1000;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                NetData.login(userName, sha1password, null, null);
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
        NetData.login(this.userName = userName, this.sha1password = NetWorkTool.sha1(password), this, viewHandler);
    }
    public void register(String userName, String password, String passwordRepeat, String nickName, String email, String motto, String name, int sex, int size, String phone, String school, int departmentId, int grade, String studentId, ViewHandler viewHandler){
        NetData.register(this.userName = userName, this.sha1password = NetWorkTool.sha1(password), NetWorkTool.sha1(passwordRepeat), nickName, email, motto, name, sex, size, phone, school, departmentId, grade, studentId, this, viewHandler);
    }
    public void logout(ViewHandler viewHandler){
        NetData.logout(this, viewHandler);
    }
    void getAvatar(String email, Object addition, ViewHandler viewHandler){

    }
    @Override
    public void show(int which, Object data, long time) {
        Object[] data1 = (Object[])data;
        //0 add
        //1 realData
        switch (which){
            case ViewHandler.LOGIN:
                if ((boolean)data1[1]) {
                    setUser(userName, sha1password);
                }
                break;
            case ViewHandler.REGISTER:
                if ((boolean)data1[1]){
                    setUser(userName, sha1password);
                }
                break;
            case ViewHandler.LOGOUT:
                if ((boolean)data1[1]){
                    sp.edit().putBoolean("isLogin", false).commit();
                }
        }
        ((ViewHandler)(data1[0])).show(which, data1[1], time);
    }
}
