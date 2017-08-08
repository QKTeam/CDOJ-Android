package cn.edu.uestc.acm.cdoj.net.user;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.io.File;

import cn.edu.uestc.acm.cdoj.net.UserInfoCallback;
import cn.edu.uestc.acm.cdoj.utils.DigestUtil;
import cn.edu.uestc.acm.cdoj.utils.ImageUtil;
import cn.edu.uestc.acm.cdoj.utils.Request;
import cn.edu.uestc.acm.cdoj.utils.ThreadUtil;

/**
 * Created by lagranmoon on 2017/7/25.
 */

public class UserConnection {
    private static final String TAG = "UserConnection";
    private static final UserConnection instance = new UserConnection();
    private String baseUrl = "http://acm.uestc.edu.cn";
    private String loginUrl = "/user/login";
    private String userInfoUrl = "/user/profile/";
    private String registerUrl = "/user/register";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x01012013:
                    UserInfoCallback login = (UserInfoCallback) msg.obj;
                    Bundle bundle = msg.getData();
                    login.loginStatus(bundle);
                    break;
                case 0x01012017:
                    Object[] obj = (Object[]) msg.obj;
                    UserInfoCallback getUserInfo = (UserInfoCallback) obj[0];
                    UserInfo userInfo = (UserInfo) obj[1];
                    getUserInfo.getUserInfo(userInfo);
                    break;
                case 0x01010808:
                    Object[] objects = (Object[]) msg.obj;
                    UserInfoCallback register = (UserInfoCallback) objects[0];
                    String registerStatus = (String) objects[1];
                    register.registerStatus(registerStatus);
            }
        }
    };
    private UserConnection() {
    }

    public static UserConnection getInstance() {
        return instance;
    }

    public void login(final String request_json, final UserInfoCallback userInfoCallback) {
        ThreadUtil.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                byte[] receiveData = Request.post(baseUrl, loginUrl, request_json);
                LoginDataReceived loginDataReceived;
                String[] data = new String[2];
                try {
                    loginDataReceived = JSON.parseObject(new String(receiveData), LoginDataReceived.class);
                    data[0] = loginDataReceived.getResult();
                    data[1] = loginDataReceived.getUserName();
                } catch (Exception e) {
                    data[0] = "error";
                }
                Message message = Message.obtain();
                message.obj = userInfoCallback;
                Bundle bundle = new Bundle();
                bundle.putStringArray("data", data);
                message.setData(bundle);
                message.what = 0x01012013;
                handler.sendMessage(message);
            }
        });
    }

    public void login_background(final String request_json){
        ThreadUtil.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                Request.post(baseUrl,loginUrl,request_json);
            }
        });
    }

    public void getUserInfo(final Context context, final String username, final UserInfoCallback userInfoCallback, final int size) {
        ThreadUtil.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap;
                byte[] receivedData = Request.get(baseUrl, userInfoUrl + username);
                UserInfoReceived userInfoReceived = JSON.parseObject(new String(receivedData), UserInfoReceived.class);
                UserInfo userInfo =  userInfoReceived.getUser();
                String email = userInfo.getEmail();
                String url = String.format("http://cdn.v2ex.com/gravatar/%s.jpg?s=%d&&d=retro", DigestUtil.md5(email), size);
                String uri = context.getFilesDir() + "/Images/" + DigestUtil.md5(url) + ".jpg";
                if (!new File(uri).exists()) {
                    bitmap = ImageUtil.downloadImage(url);
                    ImageUtil.saveImage(context, bitmap, url);
                }
                Message message = Message.obtain();
                Object[] obj = new Object[3];
                obj[0] = userInfoCallback;
                obj[1] =userInfo;
                message.obj = obj;
                message.what = 0x01012017;
                handler.sendMessage(message);
            }
        });
    }

    public void saveAvatar(final Context context, final String email, final int size){
        ThreadUtil.getInstance().avatarExecute(new Runnable() {
            @Override
            public void run() {
                String url = String.format("http://cdn.v2ex.com/gravatar/%s.jpg?s=%d&&d=retro", DigestUtil.md5(email), size);
                Bitmap bitmap = ImageUtil.downloadImage(url);
                ImageUtil.saveImage(context, bitmap, url);
            }
        });
    }

    public Bitmap getAvatar(Context context,String email,int size){
        String url = String.format("http://cdn.v2ex.com/gravatar/%s.jpg?s=%d&&d=retro", DigestUtil.md5(email), size);
        String uri = context.getFilesDir() + "/Images/" + DigestUtil.md5(url) + ".jpg";
        if (!new File(uri).exists()) {
            saveAvatar(context,email,size);
        }
        return ImageUtil.readImage(uri);
    }

    public void register(final String request_json, final UserInfoCallback userInfoCallback){
        ThreadUtil.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                byte[] receiveData =  Request.post(baseUrl,registerUrl,request_json);
                String registerStatus = "";

                Log.d(TAG, "receiveData"+new String (receiveData));

                try {
                    RegisterSuccess registerSuccess = JSON.parseObject(new String(receiveData),RegisterSuccess.class);
                    registerStatus = registerSuccess.getResult();
                }catch (Exception registerError){
                    UserNameError userNameError = JSON.parseObject(new String(receiveData),UserNameError.class);
                    registerStatus = userNameError.getResult();
                }
                Message message = Message.obtain();
                Object[] obj = new Object[2];
                obj[0] = userInfoCallback;
                obj[1] = registerStatus;
                message.obj = obj;
                message.what = 0x01010808;
                handler.sendMessage(message);
            }
        });
    }
}
