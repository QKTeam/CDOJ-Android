package cn.edu.uestc.acm.cdoj.net;

import android.graphics.Bitmap;

import cn.edu.uestc.acm.cdoj.net.user.UserInfoReceived;

/**
 * Created by lagranmoon on 2017/7/25.
 */

public interface UserInfoCallback {
    public void UserInfoChange(UserInfoReceived.UserBean userBean,Bitmap bitmap);
}
