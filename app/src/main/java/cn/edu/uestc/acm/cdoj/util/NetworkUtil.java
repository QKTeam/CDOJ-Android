package cn.edu.uestc.acm.cdoj.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 获取当前网络状态的工具类
 * Created by lagranmoon on 2017/8/21.
 */

public class NetworkUtil {
    /**
     * 判断当前是否有可用的网络连接
     * @param context 传入的context对象
     * @return 布尔值，用于表示网络是否连接
     */
    public static boolean isConnect(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable();
    }
}
