package cn.edu.uestc.acm.cdoj.genaralData;

import java.util.List;

/**
 * Created by 14779 on 2017-7-18.
 */

public class ListReceived<T> extends ContentReceived {
    /**
     * list
     * pageInfo
     * result
     */

    private PageInfo pageInfo;
    private List<T> list;

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }


}
