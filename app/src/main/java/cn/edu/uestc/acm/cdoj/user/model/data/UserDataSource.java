package cn.edu.uestc.acm.cdoj.user.model.data;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by lagranmoon on 2017/8/22.
 */

public interface UserDataSource {

    interface LoginCallback{
        void handleLoginResponse();
    }

    interface RegisterCallback{
        void handleRegisterResponse();
    }

    interface GetUserInfoCallback{
        void setUserInfo();
    }

    interface EditCallback{
        void handleEditResponse();
    }

    void login(Context context,@NonNull String RequestContent);

    void getUserInfo(@NonNull GetUserInfoCallback getUserInfoCallback);
}
