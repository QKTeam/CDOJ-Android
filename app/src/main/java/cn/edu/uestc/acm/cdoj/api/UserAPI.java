package cn.edu.uestc.acm.cdoj.api;

import cn.edu.uestc.acm.cdoj.data.bean.DetailResponse;
import cn.edu.uestc.acm.cdoj.data.bean.ListResponse;
import cn.edu.uestc.acm.cdoj.data.bean.Search;
import cn.edu.uestc.acm.cdoj.data.bean.User;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by lagranmoon on 2018/2/1.
 * 用户相关的API
 */

public interface UserAPI {

    @POST("user/search")
    Observable<ListResponse<User>> getUserList(@Body Search user);

    @GET("user/profile/{userName)")
    Observable<DetailResponse<User>> getUserDetail(@Path("userName") String userName);

}
