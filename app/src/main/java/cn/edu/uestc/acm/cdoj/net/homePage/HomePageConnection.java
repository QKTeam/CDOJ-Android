package cn.edu.uestc.acm.cdoj.net.homePage;

import android.util.Log;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;

import cn.edu.uestc.acm.cdoj.utils.Request;

/**
 * Created by 14779 on 2017-8-10.
 */

public class HomePageConnection {

    private static final String TAG = "HomePageConnection";
    public static HomePageConnection instance = new HomePageConnection();
    private String url = "http://acm.uestc.edu.cn";
    private String recentContestPath = "/recentContest/";

    public static HomePageConnection getInstance(){
        return instance;
    }

    private String getRecentContestJson(){
        byte[] dataReceived = Request.get(url, recentContestPath);
        if (dataReceived != null){
            return new String(dataReceived);
        }
        return "";
    }

    private List<RecentContestListItem> handleRecentContestJson(String jsonString){
        return JSON.parseObject(jsonString, new TypeReference<RecentContestList>(){}).getRecentContestList();
    }

    public List<RecentContestListItem> getRecentContest(){
        return handleRecentContestJson(getRecentContestJson());
    }
}
