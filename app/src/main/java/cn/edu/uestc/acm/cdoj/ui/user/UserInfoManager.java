package cn.edu.uestc.acm.cdoj.ui.user;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import cn.edu.uestc.acm.cdoj.net.ViewHandler;
import cn.edu.uestc.acm.cdoj.net.data.Result;
import cn.edu.uestc.acm.cdoj.ui.modules.Global;

/**
 * Created by Grea on 2016/10/16.
 */

public class UserInfoManager implements ViewHandler {
    private static UserInfo userInfo;
    private static UserInfo.SynListener synListener;

    public static void addNewUser(Context context, UserInfo user) {
        userInfo = user;
        if (synListener != null) {
            user.setSynListener(synListener);
        }
    }

    public static boolean hasUserInfo() {
        return userInfo != null;
    }

    public static UserInfo getUserInfo() {
        return userInfo;
    }

    public static void setSynListener(UserInfo.SynListener listener) {
        synListener = listener;
        if (userInfo != null) {
            userInfo.setSynListener(listener);
        }
    }

    public static UserInfo readLocalUserInfo() {
        Map<String, Object> userInfoMap = new HashMap<>();
        try {
            File file = new File(Global.filesDirPath + "/userInfo");
            if (!file.exists()) {
                Log.d("没有登录文件", "readLocalUserInfo: ");
                return null;
            } else {
                Log.d("有登录文件", "readLocalUserInfo: ");
                Scanner input = new Scanner(file);
                userInfoMap.put("departmentId", input.nextInt());
                input.nextLine();
                userInfoMap.put("email", input.nextLine());
                userInfoMap.put("grade", input.nextInt());
                input.nextLine();
                userInfoMap.put("motto", input.nextLine());
                userInfoMap.put("name", input.nextLine());
                userInfoMap.put("nickName", input.nextLine());
                userInfoMap.put("phone", input.nextLine());
                userInfoMap.put("school", input.nextLine());
                userInfoMap.put("sex", input.nextInt());
                input.nextLine();
                userInfoMap.put("size", input.nextInt());
                input.nextLine();
                userInfoMap.put("studentId", input.nextLine());
                userInfoMap.put("type", input.nextInt());
                input.nextLine();
                userInfoMap.put("userName", input.nextLine());
                input.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        userInfo = new UserInfo(userInfoMap);
        if (Global.userManager != null) {
            Global.userManager.getAvatar(userInfo.getEmail(), null, userInfo);
        }
        return userInfo;
    }

    @Override
    public void show(int which, Result result, long time) {
        switch (which) {
            case ViewHandler.LOGOUT:
                userInfo = null;
                File file = new File(Global.filesDirPath + "/userInfo");
                file.delete();
        }
    }
}
