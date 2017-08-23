package cn.edu.uestc.acm.cdoj.user.model.data.remote;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import cn.edu.uestc.acm.cdoj.user.model.data.UserDataSource;

/**
 * Implementation of the data source that adds a latency simulating network.
 * Created by lagranmoon on 2017/8/22.
 */

public class UserRemoteDataSource implements UserDataSource {

    private static UserRemoteDataSource INSTANCE = new UserRemoteDataSource();

    private String editUrl = "/user/edit";
    private String loginUrl = "/user/login";
    private String logoutUrl = "/user/logout";
    private String userInfoUrl = "/user/profile";
    private String registerUrl = "/user/register";
    private String baseUrl = "http://acm.uestc.edu.cn";


    private UserRemoteDataSource() {
    }

    public static UserRemoteDataSource getInstance(){
        return INSTANCE;
    }

    @Override
    public void login(@NonNull Context context, @NonNull String RequestContent) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

    }

    @Override
    public void getUserInfo(@NonNull GetUserInfoCallback getUserInfoCallback) {

    }
}
