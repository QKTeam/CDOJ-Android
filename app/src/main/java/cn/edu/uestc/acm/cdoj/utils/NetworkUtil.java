package cn.edu.uestc.acm.cdoj.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * NetWork State Util
 * Created by lagranmoon on 2017/8/21.
 */

public class NetworkUtil {
    /**
     * required permission android.permission.ACCESS_NETWORK_STATE .
     * @param context to get the ConnectivityManager.
     * @return true means network is available.
     */

    public static boolean isConnect(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo!=null&&networkInfo.isConnected();
    }
}
