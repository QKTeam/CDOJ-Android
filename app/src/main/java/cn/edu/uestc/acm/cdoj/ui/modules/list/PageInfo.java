package cn.edu.uestc.acm.cdoj.ui.modules.list;

import java.util.Map;

/**
 * Created by Grea on 2016/10/20.
 */

public class PageInfo {

    public Integer currentPage;
    public Integer displayDistance;
    public Integer countPerPage;
    public Integer totalItems;
    public Integer totalPages;

    public PageInfo(Map<String, Object> pageInfoMap) {
        currentPage = (Integer) pageInfoMap.get("currentPage");
        displayDistance = (Integer) pageInfoMap.get("displayDistance");
        countPerPage = (Integer) pageInfoMap.get("countPerPage");
        totalItems = (Integer) pageInfoMap.get("totalItems");
        totalPages = (Integer) pageInfoMap.get("totalPages");
    }
}
