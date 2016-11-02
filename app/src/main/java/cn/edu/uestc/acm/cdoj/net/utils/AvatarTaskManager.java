package cn.edu.uestc.acm.cdoj.net.utils;

import android.util.Log;

import java.util.Vector;

import cn.edu.uestc.acm.cdoj.net.NetHandler;

/**
 * Created by Grea on 2016/10/27.
 */

public class AvatarTaskManager {

    static final Vector<Thread> avatarThreadList_run = new Vector<>(); //应寻找LinkedList的线程安全版本
    static final Vector<Thread> avatarThreadList_wait = new Vector<>(); //应寻找LinkedList的线程安全版本
    private static final String TAG = "头像线程管理";
    private static int maxThreadCount = 7;

    public static void addTask(NetHandler handler, String email) {
        Thread task = new AvatarThread(handler, email);
        if (avatarThreadList_run.size() < maxThreadCount) {
            avatarThreadList_run.add(task);
            task.start();
        } else {
            avatarThreadList_wait.add(task);
        }
    }

    public static void setMaxThreadCount(int count) {
        maxThreadCount = count;
    }
}
