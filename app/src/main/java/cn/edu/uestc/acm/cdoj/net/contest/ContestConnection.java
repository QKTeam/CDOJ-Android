package cn.edu.uestc.acm.cdoj.net.contest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;

import cn.edu.uestc.acm.cdoj.genaralData.ListReceived;
import cn.edu.uestc.acm.cdoj.utils.JsonUtil;
import cn.edu.uestc.acm.cdoj.utils.Request;

/**
 * Created by 14779 on 2017-7-24.
 */

public class ContestConnection {
    public static ContestConnection instance = new ContestConnection();

    private static final String TAG = "ContestConnection";
    private String url = "http://acm.uestc.edu.cn";
    private String dataPath = "/contest/data/";
    private String searchPath = "/contest/search/";
    private String[] key = {"currentPage", "orderFields", "orderAsc", "keyword", "starItd"};

    public static ContestConnection getInstance(){
        return instance;
    }

    private String getContentJson(int id){
        byte[] dataReceived = Request.get(url, dataPath+id);
        if (dataReceived != null){
            return new String(dataReceived);
        }
        return "";
    }

    private String getSearchJson(int currentPage, String orderFields, boolean orderAsc, String keyword, int startId){
        Object[] value = {currentPage, orderFields, orderAsc, keyword, startId};
        String request = JsonUtil.getJsonString(key, value);
        byte[] dataReceived = Request.post(url, searchPath, request);
        if (dataReceived != null){
            return new String(dataReceived);
        }
        return "";
    }

    private ContestReceived handleContentJson(String jsonString){
        ContestReceived contestReceived = JSON.parseObject(jsonString, new TypeReference<ContestReceived>(){});
        return contestReceived;
    }

    private ListReceived<ContestListItem> handleSearchJson(String jsonString){
        ListReceived<ContestListItem> itemListReceived = JSON.parseObject(jsonString, new TypeReference<ListReceived<ContestListItem>>(){});
        return itemListReceived;
    }

    public Contest getContent(int id){
        return handleContentJson(getContentJson(id)).getContest();
    }

    public List<ContestProblem> getContestProblemList(int id){
        return handleContentJson(getContentJson(id)).getProblemList();
    }

    public ListReceived<ContestListItem> getSearch(int currentPage, String orderFields, boolean orderAsc, String keyword, int startId){
        return handleSearchJson(getSearchJson(currentPage, orderFields, orderAsc, keyword, startId));
    }
}
