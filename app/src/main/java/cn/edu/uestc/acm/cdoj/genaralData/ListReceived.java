package cn.edu.uestc.acm.cdoj.genaralData;

import java.util.List;

/**
 * Created by 14779 on 2017-7-18.
 */

public class ListReceived<T> {
    /**
     * list
     * pageInfo
     * result
     */

    private PageInfo pageInfo;
    private String result;
    private List<T> list;

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }


}
