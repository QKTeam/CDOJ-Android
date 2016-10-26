package cn.edu.uestc.acm.cdoj.ui.modules.list;

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

    public PageInfo(Map pageInfoMap) {
        currentPage = (int) pageInfoMap.get("currentPage");
        displayDistance = (int) pageInfoMap.get("displayDistance");
        countPerPage = (int) pageInfoMap.get("countPerPage");
        totalItems = (int) pageInfoMap.get("totalItems");
        totalPages = (int) pageInfoMap.get("totalPages");
    }
}
