package cn.edu.uestc.acm.cdoj.net.problem;

import cn.edu.uestc.acm.cdoj.genaralData.ListReceived;
import cn.edu.uestc.acm.cdoj.net.ReceivedCallback;

/**
 * Created by 14779 on 2017-7-24.
 */

public interface ObtainProblem {
    void getProblemContent(int id, ReceivedCallback<Problem> callback);

    void getProblemList(int page, ReceivedCallback<ListReceived<ProblemListItem>> callback);

    void searchProblem(int page, ReceivedCallback<ListReceived<ProblemListItem>> callback);

    void searchProblem(int page, String orderFields, ReceivedCallback<ListReceived<ProblemListItem>> callback);

    void searchProblem(int page, String orderFields, boolean orderAsc, ReceivedCallback<ListReceived<ProblemListItem>> callback);

    void searchProblem(int page, String orderFields, boolean orderAsc, String keyword, ReceivedCallback<ListReceived<ProblemListItem>> callback);

    void searchProblem(int page, String orderFields, boolean orderAsc, String keyword, int startId, ReceivedCallback<ListReceived<ProblemListItem>> callback);

}
