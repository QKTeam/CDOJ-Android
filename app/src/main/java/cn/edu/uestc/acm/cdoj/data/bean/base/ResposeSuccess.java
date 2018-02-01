package cn.edu.uestc.acm.cdoj.data.bean.base;

/**
 * Created by lagranmoon on 2018/2/1.
 * 请求成功时的通用返回
 */

public class ResposeSuccess {

    /**
     * pageInfo : 页面的一些信息
     * result : success
     */

    private PageInfoBean pageInfo;
    private String result;

    public PageInfoBean getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfoBean pageInfo) {
        this.pageInfo = pageInfo;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public static class PageInfoBean {
        /**
         * countPerPage :20
         * currentPage : 1
         * displayDistance : 2
         * totalItems : 17
         * totalPages : 1
         */

        private int countPerPage;
        private int currentPage;
        private int displayDistance;
        private int totalItems;
        private int totalPages;

        public int getCountPerPage() {
            return countPerPage;
        }

        public void setCountPerPage(int countPerPage) {
            this.countPerPage = countPerPage;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getDisplayDistance() {
            return displayDistance;
        }

        public void setDisplayDistance(int displayDistance) {
            this.displayDistance = displayDistance;
        }

        public int getTotalItems() {
            return totalItems;
        }

        public void setTotalItems(int totalItems) {
            this.totalItems = totalItems;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }
    }
}
