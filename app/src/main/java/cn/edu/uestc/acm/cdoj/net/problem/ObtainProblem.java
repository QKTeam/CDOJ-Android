package cn.edu.uestc.acm.cdoj.net.problem;

import cn.edu.uestc.acm.cdoj.genaralData.ContentReceived;
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

    void getProblemStatus(int problemId, int currentPage, ReceivedCallback<ListReceived<ProblemStatusListItem>> callback);

    void getProblemStatus(int problemId, int currentPage, String orderFields, ReceivedCallback<ListReceived<ProblemStatusListItem>> callback);

    void getProblemStatus(int problemId, int currentPage, String orderFields, boolean orderAsc, ReceivedCallback<ListReceived<ProblemStatusListItem>> callback);

    void getProblemStatus(int problemId, int currentPage, String orderFields, boolean orderAsc, int contestId, ReceivedCallback<ListReceived<ProblemStatusListItem>> callback);

    void getProblemStatus(int problemId, int currentPage, String orderFields, boolean orderAsc, int contestId, int result, ReceivedCallback<ListReceived<ProblemStatusListItem>> callback);

    void submitProblemCode(int problemId, String codeContent, int languageId, ReceivedCallback<ContentReceived> callback);
}
