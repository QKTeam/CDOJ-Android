package cn.edu.uestc.acm.cdoj.net.contest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;

import cn.edu.uestc.acm.cdoj.genaralData.ContentReceived;
import cn.edu.uestc.acm.cdoj.genaralData.ListReceived;
import cn.edu.uestc.acm.cdoj.net.contest.comment.ContestCommentListItem;
import cn.edu.uestc.acm.cdoj.net.contest.problem.ContestProblem;
import cn.edu.uestc.acm.cdoj.net.contest.rank.RankListOverview;
import cn.edu.uestc.acm.cdoj.net.contest.rank.RankListReceived;
import cn.edu.uestc.acm.cdoj.net.contest.status.ContestStatusListItem;
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
    private String loginPath = "/contest/loginContest/";
    private String commentPath = "/article/commentSearch/";
    private String statusPath = "/status/search/";
    private String rankPath = "/contest/rankList/";
    private String submitPath = "/status/submit/";

    private String[] key = {"currentPage", "orderFields", "orderAsc", "keyword", "starItd"};
    private String[] loginKey = {"contestId","password"};
    private String[] commentKey = {"currentPage", "ContestId"};
    private String[] statusKey = {"currentPage", "contestId", "orderFields", "orderAsc"};
    private String[] submitKey = {"contestId", "codeContent", "languageId"};

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

    private String getLoginJson(int contestId, String password){
        Object[] value = {contestId, password};
        String request = JsonUtil.getJsonString(loginKey, value);
        byte[] dataReceived = Request.post(url, loginPath, request);
        if (dataReceived != null){
            return new String(dataReceived);
        }
        return "";
    }

    private String getCommentJson(int currentPage, int contestId){
        Object[] value = {currentPage, contestId};
        String request = JsonUtil.getJsonString(commentKey, value);
        byte[] dataReceived = Request.post(url, commentPath, request);
        if (dataReceived != null){
            return new String(dataReceived);
        }
        return "";
    }

    private String getStatusJson(int currentPage, int contestId, String orderFields, boolean orderAsc){
        Object[] value = {currentPage, contestId, orderFields, orderAsc};
        String request = JsonUtil.getJsonString(statusKey, value);
        byte[] dataReceived = Request.post(url, statusPath, request);
        if (dataReceived != null){
            return new String(dataReceived);
        }
        return "";
    }

    private String getRankJson(int id){
        byte[] dataReceived = Request.get(url, rankPath+id);
        if (dataReceived != null){
            return new String(dataReceived);
        }
        return "";
    }

    private String getSubmitJosn(int contestId, String codeContent, int languageId){
        Object[] value = {contestId, codeContent, languageId};
        String request = JsonUtil.getJsonString(submitKey, value);
        byte[] dataReceived = Request.post(url,submitPath, request);
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

    private ContentReceived handleLoginJson(String jsonString){
        return JSON.parseObject(jsonString, new TypeReference<ContentReceived>(){});
    }

    private ListReceived<ContestCommentListItem> handleCommentJson(String jsonString){
        return JSON.parseObject(jsonString, new TypeReference<ListReceived<ContestCommentListItem>>(){});
    }

    private ListReceived<ContestStatusListItem> handleStatusJson(String jsonString){
        return JSON.parseObject(jsonString, new TypeReference<ListReceived<ContestStatusListItem>>(){});
    }

    private RankListReceived handleRankJson(String jsonString){
        return JSON.parseObject(jsonString, new TypeReference<RankListReceived>(){});
    }

    private ContentReceived handleSubmitJosn(String jsonString){
        return JSON.parseObject(jsonString, new TypeReference<ContentReceived>(){});
    }

    public Contest getContent(int id){
        return handleContentJson(getContentJson(id)).getContest();
    }

    public List<ContestProblem> getContestProblemList(int id){
        return handleContentJson(getContentJson(id)).getProblemList();
    }

    public ContestReceived getContestReceived(int id){
        return handleContentJson(getContentJson(id));
    }

    public ListReceived<ContestListItem> getSearch(int currentPage, String orderFields, boolean orderAsc, String keyword, int startId){
        return handleSearchJson(getSearchJson(currentPage, orderFields, orderAsc, keyword, startId));
    }

    public ContentReceived getLogin(int contestId, String password){
        return handleLoginJson(getLoginJson(contestId, password));
    }

    public ListReceived<ContestCommentListItem> getComment(int page, int ContestId){
        return handleCommentJson(getCommentJson(page, ContestId));
    }

    public ListReceived<ContestStatusListItem> getStatus(int page, int contestId, String orderFields, boolean orderAsc){
        return handleStatusJson(getStatusJson(page, contestId, orderFields, orderAsc));
    }

    public RankListReceived getRankReceived(int id){
        return handleRankJson(getRankJson(id));
    }

    //lastFetched,problemList,rankLIst
    public RankListOverview getRank(int id){
        return getRankReceived(id).getRankList();
    }

    public ContentReceived submitContestCode(int contestId, String codeContent, int languageId){
        return handleSubmitJosn(getSubmitJosn(contestId, codeContent, languageId));
    }
}
