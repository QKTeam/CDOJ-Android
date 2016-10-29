package cn.edu.uestc.acm.cdoj.net.utils;

import android.os.Looper;
import android.util.Log;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Handler;

import cn.edu.uestc.acm.cdoj.net.NetHandler;

/**
 * Created by Grea on 2016/10/23.
 */

public class NetThread extends Thread {
    private ArrayList<NetHandler> taskList = new ArrayList<>();
    public final static Object lock = new Object();

    String TAG = "网络线程";
    @Override
    public void run() {
        while (true) {
            if (taskList.size() == 0) {
                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }else {
                NetHandler task = taskList.get(0);
                task.onGetNetData();
                taskList.remove(task);
            }
        }
    }

    public void addTask(NetHandler handler) {
        taskList.add(handler);
        synchronized (lock) {
            lock.notify();
        }
    }
}
