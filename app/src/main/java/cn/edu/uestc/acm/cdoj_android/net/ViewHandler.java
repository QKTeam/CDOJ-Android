package cn.edu.uestc.acm.cdoj_android.net;

/**
 * Created by lenovo on 2016/8/7.
 */
public interface ViewHandler {
    static final int PROBLEM_LIST = 0,
            CONTEST_LIST = 1,
            ARTICLE_LIST = 2,
            PROBLEM_DETAIL = 3,
            CONTEST_DETAIL = 4,
            ARTICLE_DETAIL = 5;
    //    void showProblemList(ProblemInfoList problemInfoList);
//    void showContestList(ContestInfoList contestInfoList);
//    void showArticleList(ArticleInfoList aritcleInfoList);
    void show(int which, Object data);
}
