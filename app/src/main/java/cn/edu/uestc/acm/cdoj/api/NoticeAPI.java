package cn.edu.uestc.acm.cdoj.api;

import cn.edu.uestc.acm.cdoj.data.bean.DetailResponse;
import cn.edu.uestc.acm.cdoj.data.bean.ListResponse;
import cn.edu.uestc.acm.cdoj.data.bean.Notice;
import cn.edu.uestc.acm.cdoj.data.bean.Search;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by lagranmoon on 2018/2/1.
 * 首页公告相关的API
 */

public interface NoticeAPI {

    @POST("article/search")
    Observable<ListResponse<Notice>> getNoticeList(@Body Search notice);

    @GET("article/data/{id}")
    Observable<DetailResponse<Notice>> getNoticeDetail(@Path("id") int id);
}
