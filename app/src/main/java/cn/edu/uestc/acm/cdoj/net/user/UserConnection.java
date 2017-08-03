package cn.edu.uestc.acm.cdoj.net.user;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

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
                    UserInfoReceived.UserBean userBean = (UserInfoReceived.UserBean) obj[1];
                    getUserInfo.getUserInfo(userBean);
                    break;
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

    public void getUserInfo(final Context context, final String username, final UserInfoCallback userInfoCallback, final int size) {
        ThreadUtil.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap;
                byte[] receivedData = Request.get(baseUrl, userInfoUrl + username);
                UserInfoReceived userInfoReceived = JSON.parseObject(new String(receivedData), UserInfoReceived.class);

                String email = userInfoReceived.getUser().getEmail();
                String url = String.format("http://cdn.v2ex.com/gravatar/%s.jpg?s=%d&&d=retro", DigestUtil.md5(email), size);
                String uri = context.getFilesDir() + "/Images/" + DigestUtil.md5(url) + ".jpg";
                if (!new File(uri).exists()) {
                    bitmap = ImageUtil.downloadImage(url);
                    ImageUtil.saveImage(context, bitmap, url);
                }
                Message message = Message.obtain();
                Object[] obj = new Object[3];
                obj[0] = userInfoCallback;
                obj[1] = userInfoReceived.getUser();
                message.obj = obj;
                message.what = 0x01012017;
                handler.sendMessage(message);
            }
        });
    }


}
