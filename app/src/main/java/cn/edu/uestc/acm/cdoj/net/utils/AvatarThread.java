package cn.edu.uestc.acm.cdoj.net.utils;

import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.edu.uestc.acm.cdoj.net.NetData;
import cn.edu.uestc.acm.cdoj.net.NetHandler;
import cn.edu.uestc.acm.cdoj.ui.modules.Global;

/**
 * Created by Grea on 2016/10/27.
 */

public class AvatarThread extends Thread {

    private static final String TAG = "头像线程";
    private NetHandler handler;
    private String email;

    public AvatarThread(NetHandler handler, String email) {
        super();
        this.handler = handler;
        this.email = email;
    }

    @Override
    public void run() {
        byte[] avatarBytes = NetWorkUtils.getAvatar(handler.getUrlString());
        BitmapDrawable avatar = new BitmapDrawable(handler.getContext().getResources(),
                new ByteArrayInputStream(avatarBytes));
        handler.setContent(avatar);
        handler.sendEmptyMessage(NetHandler.AFTERCONVERT); //没起作用

        Log.d(TAG, "run: 获取头像完成  " + handler.getUrlString());
        saveAvatar(avatarBytes, email);

        if (AvatarTaskManager.avatarThreadList_wait.size() != 0) {
            synchronized (AvatarTaskManager.avatarThreadList_wait) {
                Thread thread = AvatarTaskManager.avatarThreadList_wait.get(0);
                AvatarTaskManager.avatarThreadList_wait.remove(thread);
                AvatarTaskManager.avatarThreadList_run.add(thread);
                thread.start();
            }
        }
        AvatarTaskManager.avatarThreadList_run.remove(this);
        Log.d(TAG, "run: 线程结束");
    }

    private void saveAvatar(byte[] avatarBytes, String email) {
        File file = new File(Global.getCacheDirPath() + email);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream output = new FileOutputStream(file);
            output.write(avatarBytes);
            output.close();
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }
}
