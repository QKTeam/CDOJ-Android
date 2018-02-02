package cn.edu.uestc.acm.cdoj.data.bean;

/**
 * Created by lagranmoon on 2018/2/1.
 * 请求成功时的通用返回
 */

public class ResposeSuccess {

    /**
     * pageInfo : 页面的一些信息
     * result : success
     */

    public PageInfoBean pageInfo;
    public String result;

    public static class PageInfoBean {
        /**
         * countPerPage :20
         * currentPage : 1
         * displayDistance : 2
         * totalItems : 17
         * totalPages : 1
         */

        public int countPerPage;
        public int currentPage;
        public int displayDistance;
        public int totalItems;
        public int totalPages;

    }
}
