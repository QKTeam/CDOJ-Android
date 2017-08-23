package cn.edu.uestc.acm.cdoj.user.model.data.local;

import android.content.Context;
import android.support.annotation.NonNull;

import cn.edu.uestc.acm.cdoj.user.model.data.UserDataSource;

/**
 * Concrete implementation of a data source as a db.
 * Created by lagranmoon on 2017/8/22.
 */

public class UserLocalDataSource implements UserDataSource {

    private static UserLocalDataSource INSTANCE ;

    private UserDBHelper mDbHelper;

    private UserLocalDataSource(@NonNull Context context) {
        mDbHelper = new UserDBHelper(context);
    }

    public static UserLocalDataSource getInstance(@NonNull Context context){
       if (INSTANCE==null){
           INSTANCE = new UserLocalDataSource(context);
       }
       return INSTANCE;
    }


    @Override
    public void login(Context context, @NonNull String RequestContent) {

    }

    @Override
    public void getUserInfo(@NonNull GetUserInfoCallback getUserInfoCallback) {

    }
}
