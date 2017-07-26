package cn.edu.uestc.acm.cdoj.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by leigu on 2017/4/19.
 */

public class ThreadUtil {
    private static ThreadUtil instance;
    private ThreadPoolExecutor avatarThreadPool;
    private ThreadPoolExecutor generalThreadPool;


    private ThreadUtil() {
        avatarThreadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(6);
        generalThreadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
    }

    public static ThreadUtil getInstance() {
        if (instance == null) {
            instance = new ThreadUtil();
        }
        return instance;
    }

    public void avatarExecute(Runnable command) {
        avatarThreadPool.execute(command);
    }

    public void execute(Runnable command) {
        generalThreadPool.execute(command);
    }

}
