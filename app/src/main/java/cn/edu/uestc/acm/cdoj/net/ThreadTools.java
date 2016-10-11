package cn.edu.uestc.acm.cdoj.net;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by qwe on 16-10-11.
 */

public class ThreadTools {
    static Handler handler;
    static {
        handler = new Handler(Looper.getMainLooper());
    }
    public static void runOnMainThread(Runnable runnable){
        handler.post(runnable);
    }
}
