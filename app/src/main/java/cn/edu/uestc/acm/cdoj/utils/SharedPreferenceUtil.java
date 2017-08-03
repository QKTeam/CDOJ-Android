package cn.edu.uestc.acm.cdoj.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lagranmoon on 2017/8/3.
 */

public class SharedPreferenceUtil {
    public static void saveSharedPreference(Activity activity,String name,String[] key,String[] value){
        SharedPreferences sharedPreferences = activity.getSharedPreferences(DigestUtil.sha1(name), Context.MODE_APPEND);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = 0;i<key.length;i++){
            editor.putString(key[i],value[i]);
        }
        editor.apply();
    }
}
