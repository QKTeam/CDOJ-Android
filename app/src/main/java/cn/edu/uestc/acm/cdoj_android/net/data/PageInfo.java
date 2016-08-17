package cn.edu.uestc.acm.cdoj_android.net.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lenovo on 2016/8/8.
 */
public class PageInfo{
    public int countPerPage, currentPage, displayDistance, totalItems, totalPages;
    public PageInfo(JSONObject pageInfo){
        try {
            countPerPage = pageInfo.getInt("countPerPage");
            currentPage = pageInfo.getInt("currentPage");
            displayDistance = pageInfo.getInt("displayDistance");
            totalItems = pageInfo.getInt("totalItems");
            totalPages = pageInfo.getInt("totalPages");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
