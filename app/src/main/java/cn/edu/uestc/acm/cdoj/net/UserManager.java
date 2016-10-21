package cn.edu.uestc.acm.cdoj.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cn.edu.uestc.acm.cdoj.net.data.Result;

/**
 * Created by qwe on 16-8-21.
 */
public class UserManager implements ViewHandler, NetStateListener {
    private String TAG = "tagUserManager";
    private String KEY_CURRENT_USER = "currentUser";
    Context mContext;
    SharedPreferences sp;

    String mUserName = "ssss", mSha1password = "hahahhaha";
    MyDBHelper mUserDBHelper;
    private boolean keepLoginCancelFlag = false, hasAddListener = false;
    public UserManager(Context c) {
        this.mContext = c;
        sp = mContext.getSharedPreferences("user", Context.MODE_PRIVATE);
        mUserDBHelper = new MyDBHelper(mContext, "user", null, 1);
    }
    public String getCurrentUser(){
        return sp.getString(KEY_CURRENT_USER, null);
    }
    public boolean hasCurrentUser(){
        return getCurrentUser() != null;
    }
    public void setCurrentUser(String userName, ViewHandler viewHandler){
        if (userName == null){
            sp.edit().remove(KEY_CURRENT_USER).commit();
            logout(null);
        }
        else {
            String sha1password = (String) mUserDBHelper.getList(userName).get(0).get("sha1password");
            _login(userName, sha1password, viewHandler);
        }
    }
    public boolean hasUser(){
        return getUserList().size() >= 1;
    }
    public ArrayList<Map<String, Object>> getUserList(){
        return mUserDBHelper.getList(null);
    }
    public void deleteUser(String userName){
        if (getCurrentUser().equals(userName)){
            setCurrentUser(null, null);
        }
        mUserDBHelper.delete(userName);
    }
    private void addUser(String userName, String sha1password){
        mUserDBHelper.insert(userName, sha1password);
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
    void keepLogin(){
        if (!hasAddListener){
            hasAddListener = true;
            NetWorkTool.addNetStateListener(this);
        }
        keepLogin(mUserName, mSha1password, false);
    }
    public void login(String userName, String password, ViewHandler viewHandler){
        _login(userName, NetWorkTool.sha1(password), viewHandler);
    }
    private void _login(String userName, String sha1password, ViewHandler viewHandler){
        NetData.setExtra(viewHandler);
        NetData.login(this.mUserName = userName, this.mSha1password = sha1password, this);
    }
    public void register(String userName, String password, String passwordRepeat, String nickName, String email, String motto, String name, int sex, int size, String phone, String school, int departmentId, int grade, String studentId, ViewHandler viewHandler){
        NetData.setExtra(viewHandler);
        NetData.register(this.mUserName = userName, this.mSha1password = NetWorkTool.sha1(password), NetWorkTool.sha1(passwordRepeat), nickName, email, motto, name, sex, size, phone, school, departmentId, grade, studentId, this);
    }
    public void logout(ViewHandler viewHandler){
        NetData.setExtra(viewHandler);
        NetData.logout(this);
    }
    @Override
    public void show(int which, Result result, long time) {
        Object extra = result.getExtra();
        switch (which){
            case ViewHandler.LOGIN:
                if (result.result) {
                    if (extra != null){
                        sp.edit().putString(KEY_CURRENT_USER, mUserName).commit();
                        mUserDBHelper.insert(mUserName, mSha1password);
                        keepLogin();
                    }
                }
                else {
                    if (result.resultType == Result.STATE_CONTENT_ERROR && extra == null){
                        setCurrentUser(null, null);
                        mUserDBHelper.delete(mUserName);
                    }
                    keepLoginCancelFlag= true;
                }
                break;
            case ViewHandler.REGISTER:
                if (result.result){
                }
                break;
            case ViewHandler.LOGOUT:
                if (result.result){
                }
                break;
        }
        if (extra != null){
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
