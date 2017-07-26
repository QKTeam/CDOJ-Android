package cn.edu.uestc.acm.cdoj.net.problem;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import cn.edu.uestc.acm.cdoj.genaralData.ListReceived;
import cn.edu.uestc.acm.cdoj.utils.JsonUtil;
import cn.edu.uestc.acm.cdoj.utils.Request;

/**
 * Created by 14779 on 2017-7-24.
 */

public class ProblemConnection {
    public static ProblemConnection instance = new ProblemConnection();

    private static final String TAG = "ProblemConnection";
    private String url = "http://acm.uestc.edu.cn";
    private String dataPath = "/problem/data/";
    private String searchPath = "/problem/search/";
    private String[] key = {"currentPage", "orderFields", "orderAsc", "keyword", "startId"};

    public static ProblemConnection getInstance(){
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

    private Problem handleContentJson(String jsonString){
        ProblemReceived received = JSON.parseObject(jsonString, new TypeReference<ProblemReceived>(){});
        return received.getProblem();
    }

    private ListReceived<ProblemListItem> handleSearchJSon(String jsonString){
        ListReceived listReceived = JSON.parseObject(jsonString, new TypeReference<ListReceived<ProblemListItem>>(){});
        return listReceived;
    }

    public Problem getContent(int id){
        return handleContentJson(getContentJson(id));
    }

    public ListReceived<ProblemListItem> getSearch(int currentPage, String orderFields, boolean orderAsc, String keyword, int startId){
        return handleSearchJSon(getSearchJson(currentPage, orderFields, orderAsc, keyword, startId));
    }
}
