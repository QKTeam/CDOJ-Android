package cn.edu.uestc.acm.cdoj.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by lagranmoon on 2018/2/14.
 * 全局初始化Application
 */

public class BaseApplication extends Application{

    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
        super.onCreate();
    }

    public static Context getContext(){
        return context;
    }
}
