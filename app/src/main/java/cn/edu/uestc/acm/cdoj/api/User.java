package cn.edu.uestc.acm.cdoj.api;

import cn.edu.uestc.acm.cdoj.data.bean.base.Search;
import cn.edu.uestc.acm.cdoj.data.bean.user.UserDetailResponse;
import cn.edu.uestc.acm.cdoj.data.bean.user.UserListResponse;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by lagranmoon on 2018/2/1.
 * 用户相关的API
 */

public interface User {

    @POST("user/search")
    Observable<UserListResponse> getUserList(@Body Search user);

    @GET("user/profile/{userName)")
    Observable<UserDetailResponse> getUserDetail(@Path("userName") String userName);

}
