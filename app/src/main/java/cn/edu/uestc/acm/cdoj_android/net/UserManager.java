package cn.edu.uestc.acm.cdoj_android.net;

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
        Object[] objects = null;
        if ((file = new File(cacheDir + File.pathSeparator + email)).exists()){
            try {
                objects = NetWorkTool.getBytes(new FileInputStream(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (objects != null){
            show(ViewHandler.AVATAR, new Object[]{new Object[]{viewHandler, extra, email}, objects}, 0);
            Log.d(TAG, "getAvatar: 本地有该头像缓存");
        }
        else{
            NetData.getAvatar(email,new Object[]{viewHandler, extra, email} , this);
            Log.d(TAG, "getAvatar: 本地没有该头像缓存");
        }

        //BitmapFactory.decodeByteArray((byte[])objects[0], 0, (int)objects[1]);
    }
    @Override
    public void show(int which, Object data, long time) {
        Object[] data1 = new Object[2];
        data1[0] = null;
        data1[1] = data;
        if (data instanceof Object[]){
            data1 = (Object[])data;
        }
        //0 add
        //1 realData
        switch (which){
            case ViewHandler.LOGIN:
                if ((boolean)data1[1]) {
                    Log.d(TAG, "show: 登录成功");
                    setUser(userName, sha1password);
                }
                else {
                    keepLoginCancelFlag= true;
                }
                break;
            case ViewHandler.REGISTER:
                if ((boolean)data1[1]){
                    setUser(userName, sha1password);
                }
                break;
            case ViewHandler.LOGOUT:
                if ((boolean)data1[1]){
                }
                break;
            case ViewHandler.AVATAR:
                Object[] extra = (Object[]) data1[0], bytes = (Object[]) data1[1];
                if (bytes != null){
                    File file;
                    try {
                        if (!(file = new File(cacheDir + File.pathSeparator + extra[2])).exists()){
                            file.createNewFile();
                        }
                        new FileOutputStream(file).write((byte[]) bytes[0], 0, (int) bytes[1]);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                data1[1] = new Object[]{extra[1], BitmapFactory.decodeByteArray((byte[]) bytes[0], 0, (Integer) bytes[1])};
                data1[0] = extra[0];
        }
        if (data1[0] != null){
            Log.d(TAG, "show: " + data1[0]);
            ((ViewHandler)(data1[0])).show(which, data1[1], time);
        }
    }

    @Override
    public void onNetStateChange(int last, int now) {
        if (last == NetWorkTool.STATE_ERROR && now == NetWorkTool.STATE_OK && keepLoginCancelFlag){
            keepLogin();
        }
    }
}
