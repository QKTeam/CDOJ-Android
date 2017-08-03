package cn.edu.uestc.acm.cdoj.genaralData;

/**
 * Created by 14779 on 2017-7-19.
 */

public class PageInfo {

    /**
     * countPerPage : 20
     * currentPage : 1
     * displayDistance : 2
     * totalItems : 19
     * totalPages : 1
     */

    private int countPerPage;
    public int currentPage;
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
