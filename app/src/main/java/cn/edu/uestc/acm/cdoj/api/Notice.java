package cn.edu.uestc.acm.cdoj.api;

import cn.edu.uestc.acm.cdoj.data.bean.notice.NoticeDetailResponse;
import cn.edu.uestc.acm.cdoj.data.bean.notice.NoticeListResponse;
import cn.edu.uestc.acm.cdoj.data.bean.base.Search;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by lagranmoon on 2018/2/1.
 * 首页公告相关的API
 */

public interface Notice {

    @POST("article/search")
    Observable<NoticeListResponse> getNoticeList(@Body Search notice);

    @GET("article/data/{id}")
    Observable<NoticeDetailResponse> getNoticeDetail(@Path("id") int id);
}
