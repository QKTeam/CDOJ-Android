package cn.edu.uestc.acm.cdoj.net.contest;

import java.util.List;

import cn.edu.uestc.acm.cdoj.genaralData.ListReceived;
import cn.edu.uestc.acm.cdoj.net.ReceivedCallback;

/**
 * Created by 14779 on 2017-7-24.
 */

public interface ObtainContest {
    void getContestContent(int id, ReceivedCallback<Contest> callback);

    void getContestReceived(int id, ReceivedCallback<ContestReceived> callback);

    void getContestProblemList(int id, ReceivedCallback<List<ContestProblem>> callback);

    void getContestList(int page, ReceivedCallback<ListReceived<ContestListItem>> callback);

    void searchContest(int page, ReceivedCallback<ListReceived<ContestListItem>> callback);

    void searchContest(int page, String orderFields, ReceivedCallback<ListReceived<ContestListItem>> callback);

    void searchContest(int page, String orderFields, boolean orderAsc, ReceivedCallback<ListReceived<ContestListItem>> callback);

    void searchContest(int page, String orderFields, boolean orderAsc, String keyword, ReceivedCallback<ListReceived<ContestListItem>> callback);

    void searchContest(int page, String orderFields, boolean orderAsc, String keyword, int startId, ReceivedCallback<ListReceived<ContestListItem>> callback);

    void getContestComment(int page, int ContestId, ReceivedCallback<ListReceived<ContestCommentListItem>> callback);
}
