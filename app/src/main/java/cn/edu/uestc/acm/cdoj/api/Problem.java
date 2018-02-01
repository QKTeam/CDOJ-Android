package cn.edu.uestc.acm.cdoj.api;

import cn.edu.uestc.acm.cdoj.data.bean.base.Search;
import cn.edu.uestc.acm.cdoj.data.bean.problem.ProblemDetailResponse;
import cn.edu.uestc.acm.cdoj.data.bean.problem.ProblemListResponse;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by lagranmoon on 2018/2/1.
 * 问题相关的API
 */

public interface Problem {

    @POST("problem/search")
    Observable<ProblemListResponse> getProblemList(@Body Search problem);

    @GET("problem/data/{id}")
    Observable<ProblemDetailResponse> getProblemDetail(@Path("id") int id);

}
