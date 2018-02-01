package cn.edu.uestc.acm.cdoj.data.bean.notice;

import java.util.List;

import cn.edu.uestc.acm.cdoj.data.bean.base.Notice;
import cn.edu.uestc.acm.cdoj.data.bean.base.ResposeSuccess;

/**
 * Created by lagranmoon on 2018/2/1.
 * 按照给定条件返回的公告信息列表
 */

public class NoticeListResponse extends ResposeSuccess {


    private List<Notice> list;


    public List<Notice> getList() {
        return list;
    }

    public void setList(List<Notice> list) {
        this.list = list;
    }

}
