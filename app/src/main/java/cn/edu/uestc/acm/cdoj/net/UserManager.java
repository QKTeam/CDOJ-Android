package cn.edu.uestc.acm.cdoj.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import cn.edu.uestc.acm.cdoj.net.data.Result;

/**
 * Created by qwe on 16-8-21.
 */
public class UserManager implements ViewHandler, NetStateListener {
    private String TAG = "tagUserManager";
    Context context;
    SharedPreferences sp;

    String userName = "ssss", sha1password = "hahahhaha", cacheDir;
    private boolean keepLoginCancelFlag = false, hasAddListener = false;
    public UserManager(Context c) {
        this.context = c;
        sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        cacheDir = context.getCacheDir().getAbsolutePath();
    }
    public boolean isLogin(){
        return sp.getBoolean("isLogin", false);
    }
    void keepLogin(final String userName, final String sha1password, boolean im){
        keepLoginCancelFlag = false;
        int s = im?0:20*60*1000;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Log.d(TAG, "run: 执行一次登录");
                _login(userName, sha1password, null);
            }
            @Override
            public boolean cancel() {
                return keepLoginCancelFlag;
            }
        }, s , 20*60*1000);
    }
    public void keepLogin(){
        if (isLogin()) {
            if (!hasAddListener){
                hasAddListener = true;
                NetWorkTool.addNetStateListener(this);
            }
            keepLogin(sp.getString("userName", ""), sp.getString("password", ""), true);
        }
    }
    void setUser(String userName, String sha1password){
        sp.edit().putString("userName", userName).putString("password", sha1password).putBoolean("isLogin", true).commit();
        keepLogin(userName, sha1password, false);
    }
    public void login(String userName, String password, ViewHandler viewHandler){
        _login(userName, NetWorkTool.sha1(password), viewHandler);
    }
    void _login(String userName, String sha1password, ViewHandler viewHandler){
        NetData.login(this.userName = userName, this.sha1password = sha1password, this, viewHandler);
    }
    public void register(String userName, String password, String passwordRepeat, String nickName, String email, String motto, String name, int sex, int size, String phone, String school, int departmentId, int grade, String studentId, ViewHandler viewHandler){
        NetData.register(this.userName = userName, this.sha1password = NetWorkTool.sha1(password), NetWorkTool.sha1(passwordRepeat), nickName, email, motto, name, sex, size, phone, school, departmentId, grade, studentId, this, viewHandler);
    }
    public void logout(ViewHandler viewHandler){
        sp.edit().putBoolean("isLogin", false).commit();
        NetData.logout(this, viewHandler);
    }
    public void getAvatar(String email, Object extra, ViewHandler viewHandler){
        File file;
        Object[] bytes = null;
        if ((file = new File(cacheDir + File.separator + email)).exists()){
            try {
                bytes = NetWorkTool.getBytes(new FileInputStream(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (bytes != null){
            show(ViewHandler.AVATAR, new Result(bytes, new Object[]{viewHandler, extra, email}), 0);
            Log.d(TAG, "getAvatar: 本地有该头像缓存");
        }
        else{
            NetData.getAvatar(email,new Object[]{viewHandler, extra, email} , this);
            Log.d(TAG, "getAvatar: 本地没有该头像缓存");
        }

        //BitmapFactory.decodeByteArray((byte[])objects[0], 0, (int)objects[1]);
    }
    @Override
    public void show(int which, Result result, long time) {
        Object extra = result.getExtra();
        switch (which){
            case ViewHandler.LOGIN:
                if (result.result) {
                    Log.d(TAG, "show: 登录成功");
                    setUser(userName, sha1password);
                }
                else {
                    keepLoginCancelFlag= true;
                }
                break;
            case ViewHandler.REGISTER:
                if (result.result){
                    setUser(userName, sha1password);
                }
                break;
            case ViewHandler.LOGOUT:
                if (result.result){
                }
                break;
            case ViewHandler.AVATAR:
                Object[] extra1 = (Object[]) extra;
                Object[] bytes = (Object[]) result.getContent();
                if (bytes != null){
                    File file;
                    try {
                        if (!(file = new File(cacheDir+ File.separator  + extra1[2])).exists()){
                            file.createNewFile();
                        }
                        new FileOutputStream(file).write((byte[]) bytes[0], 0, (int) bytes[1]);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                result.setContent(BitmapFactory.decodeByteArray((byte[]) bytes[0], 0, (Integer) bytes[1]));
                result.setExtra(extra1[1]);
                extra = extra1[0];
        }
        if (extra != null){
            Log.d(TAG, "show: " + extra);
            ((ViewHandler)(extra)).show(which, result, time);
        }
    }

    @Override
    public void onNetStateChange(int last, int now) {
        if (last == NetWorkTool.STATE_ERROR && now == NetWorkTool.STATE_OK && keepLoginCancelFlag){
            keepLogin();
        }
    }
}
