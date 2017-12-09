package cn.edu.uestc.acm.cdoj.net.api;


import cn.edu.uestc.acm.cdoj.user.model.bean.LoginInfo;
import cn.edu.uestc.acm.cdoj.user.model.bean.LoginResponse;
import cn.edu.uestc.acm.cdoj.user.model.bean.LogoutResponse;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by lagranyan on 2017/12/6.
 * 用户注册登录相关API
 */

public interface APIUser {

        @POST("user/login")
        Observable<LoginResponse> login(@Body LoginInfo loginInfo);

        @GET("user/logout")
        Observable<LogoutResponse>logout();

}
