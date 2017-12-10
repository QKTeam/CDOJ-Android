package cn.edu.uestc.acm.cdoj.net;

import android.os.Bundle;

import cn.edu.uestc.acm.cdoj.user.model.reserved.UserInfo;

/**
 * Created by lagranmoon on 2017/7/25.
 */

public interface UserInfoCallback {
    void loginStatus(Bundle bundle);
    void registerStatus(String s);
    void getUserInfo(UserInfo userInfo);
    void editStatus(String s);
}
