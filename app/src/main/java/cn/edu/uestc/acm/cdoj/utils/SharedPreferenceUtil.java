package cn.edu.uestc.acm.cdoj.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lagranmoon on 2017/8/3.
 */

public class SharedPreferenceUtil {
    public static void saveSharedPreference(Activity activity,String name,String[] keys,String[] values){
        SharedPreferences sharedPreferences = activity.getSharedPreferences(DigestUtil.sha1(name), Context.MODE_APPEND);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = 0;i<keys.length;i++){
            editor.putString(keys[i],values[i]);
        }
        editor.apply();
    }
//    public static void save_user_password_sp(Activity activity,String userName,String password){
//        String [] key = {"userName","password"};
//        String [] value = {userName,password};
//        saveSharedPreference(activity,);
//     }
    public static void save_single_sp(Activity activity,String name,String key,String value){
        String[] keys = {key};
        String[] values = {value};
        saveSharedPreference(activity,name,keys,values);
    }
}
