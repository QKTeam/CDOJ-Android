package cn.edu.uestc.acm.cdoj.net.utils;

import java.util.ArrayList;
import java.util.List;

import cn.edu.uestc.acm.cdoj.net.NetHandler;

/**
 * Created by Grea on 2016/10/23.
 */

public class NetThread extends Thread {
    private final List<NetHandler> taskList = new ArrayList<>();

    String TAG = "网络线程";
    @Override
    public void run() {
        while (true) {
            if (taskList.size() == 0) {
                synchronized (taskList) {
                    try {
                        taskList.wait();
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
        synchronized (taskList) {
            taskList.notify();
        }
    }
}
