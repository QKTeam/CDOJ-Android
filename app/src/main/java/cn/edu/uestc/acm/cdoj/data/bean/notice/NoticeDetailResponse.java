package cn.edu.uestc.acm.cdoj.data.bean.notice;

import cn.edu.uestc.acm.cdoj.data.bean.base.Notice;
import cn.edu.uestc.acm.cdoj.data.bean.base.ResposeSuccess;

/**
 * Created by lagranmoon on 2018/2/1.
 * 公告的详细信息
 */

public class NoticeDetailResponse extends ResposeSuccess{

    /**
     * article : {"articleId":1,"clicked":12200,"content":"###What platform will my solution running on ","isVisible":true,"order":-1,"ownerEmail":"acm@uestc.edu.cn","ownerName":"administrator","time":1435299221000,"title":"Frequently Asked Questions","type":1,"userId":1}
     * result : success
     */

    private Notice notice;


    public Notice getNotice() {
        return notice;
    }

    public void setNotice(Notice notice) {
        this.notice = notice;
    }
}
