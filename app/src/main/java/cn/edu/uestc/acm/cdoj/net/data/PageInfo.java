package cn.edu.uestc.acm.cdoj.net.data;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Grea on 2016/10/20.
 */

public class PageInfo {

    public int currentPage;
    public int displayDistance;
    public int countPerPage;
    public int totalItems;
    public int totalPages;

    public PageInfo() {

    }

    public PageInfo(Map pageInfoMap) {
        currentPage = (int) pageInfoMap.get("currentPage");
        displayDistance = (int) pageInfoMap.get("displayDistance");
        countPerPage = (int) pageInfoMap.get("countPerPage");
        totalItems = (int) pageInfoMap.get("totalItems");
        totalPages = (int) pageInfoMap.get("totalPages");
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

    public int getCountPerPage() {
        return countPerPage;
    }

    public void setCountPerPage(int countPerPage) {
        this.countPerPage = countPerPage;
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
