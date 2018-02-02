package cn.edu.uestc.acm.cdoj.api;

import cn.edu.uestc.acm.cdoj.data.bean.DetailResponse;
import cn.edu.uestc.acm.cdoj.data.bean.ListResponse;
import cn.edu.uestc.acm.cdoj.data.bean.Problem;
import cn.edu.uestc.acm.cdoj.data.bean.Search;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by lagranmoon on 2018/2/1.
 * 问题相关的API
 */

public interface ProblemAPI {

    @POST("problem/search")
    Observable<ListResponse<Problem>> getProblemList(@Body Search problem);

    @GET("problem/data/{id}")
    Observable<DetailResponse<Problem>> getProblemDetail(@Path("id") int id);

}
