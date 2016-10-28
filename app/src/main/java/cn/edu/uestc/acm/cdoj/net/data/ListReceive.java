package cn.edu.uestc.acm.cdoj.net.data;

import java.util.List;

/**
 * Created by Grea on 2016/10/27.
 */

public class ListReceive<T> {
    private List<T> list;
    private PageInfo pageInfo;
    private String result;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

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
}
