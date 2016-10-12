package cn.edu.uestc.acm.cdoj.net;

import cn.edu.uestc.acm.cdoj.net.data.Result;

/**
 * Created by lenovo on 2016/8/7.
 */
public interface ViewHandler {
    int PROBLEM_LIST = 0,
        CONTEST_LIST = 1,
        ARTICLE_LIST = 2,
        PROBLEM_DETAIL = 3,
        CONTEST_DETAIL = 4,
        ARTICLE_DETAIL = 5,
        LOGIN = 6,
        LOGOUT = 7,
        LOGIN_CONTEST = 8,
        CONTEST_COMMENT = 9,
        CONTEST_RANK = 10,
        STATUS_LIST = 11,
        STATUS_INFO = 12,
        STATUS_SUBMIT = 13,
        AVATAR = 14,
        REGISTER = 15,
        USER_PROFILE = 16,
        USER_TYPE_AHEAD_ITEM = 17,
        USER_CENTER_DATA = 18;

    //    void showProblemList(ProblemInfoList problemInfoList);
//    void showContestList(ContestInfoList contestInfoList);
//    void showArticleList(ArticleInfoList articleInfoList);
    void show(int which, Result result, long time);
}
