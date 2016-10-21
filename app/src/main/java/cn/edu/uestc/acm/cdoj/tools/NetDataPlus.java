package cn.edu.uestc.acm.cdoj.tools;

import android.content.Context;

import cn.edu.uestc.acm.cdoj.net.NetData;
import cn.edu.uestc.acm.cdoj.net.ViewHandler;

/**
 * Created by Grea on 2016/10/21.
 */

public class NetDataPlus {

    public static void login(Context context, String userName, String sha1password, ViewHandler viewHandler) {
        login(context, userName, sha1password, null, viewHandler);
    }
    public static void login(Context context, String userName, String sha1password, Object extra, ViewHandler viewHandler) {
        NetData.init(context);
        NetData.setExtra(extra);
        NetData.login(userName, sha1password, viewHandler);
    }

    public static void logout(Context context, ViewHandler viewHandler) {
        logout(context, null, viewHandler);
    }
    public static void logout(Context context, Object extra, ViewHandler viewHandler) {
        NetData.init(context);
        NetData.setExtra(extra);
        NetData.logout(viewHandler);
    }

    public static void getUserCenterData(Context context, String userName, ViewHandler viewHandler) {
        getUserCenterData(context, userName, null, false, viewHandler);
    }
    public static void getUserCenterData(Context context, String userName, boolean requestInOrder, ViewHandler viewHandler) {
        getUserCenterData(context, userName, null, requestInOrder, viewHandler);
    }
    public static void getUserCenterData(Context context, String userName, Object extra, ViewHandler viewHandler) {
        getUserCenterData(context, userName, extra, false, viewHandler);
    }
    public static void getUserCenterData(Context context, String userName, Object extra, boolean requestInOrder, ViewHandler viewHandler) {
        NetData.init(context);
        NetData.setExtra(extra);
        NetData.setIfInOrder(requestInOrder);
        NetData.getUserCenterData(userName, viewHandler);
    }

    public static void getUserTypeAheadItem(Context context, String userName, ViewHandler viewHandler) {
        getUserTypeAheadItem(context, userName, null, viewHandler);
    }
    public static void getUserTypeAheadItem(Context context, String userName, Object extra, ViewHandler viewHandler) {
        NetData.init(context);
        NetData.setExtra(extra);
        NetData.getUserTypeAheadItem(userName, viewHandler);
    }

    public static void getUserProfile(Context context, String userName, ViewHandler viewHandler) {
        getUserProfile(context, userName, null, viewHandler);
    }
    public static void getUserProfile(Context context, String userName, Object extra, ViewHandler viewHandler) {
        NetData.init(context);
        NetData.setExtra(extra);
        NetData.getUserProfile(userName, viewHandler);
    }

    public static void register(Context context, String userName, String password,
                                String passwordRepeat, String nickName, String email,
                                String motto, String name, int sex, int size, String phone,
                                String school, int departmentId, int grade, String studentId,
                                ViewHandler viewHandler) {
        register(context, userName, password, passwordRepeat,
                nickName, email, motto, name, sex, size, phone,
                school, departmentId, grade, studentId, null, viewHandler);
    }
    public static void register(Context context, String userName, String password,
                                String passwordRepeat, String nickName, String email,
                                String motto, String name, int sex, int size, String phone,
                                String school, int departmentId, int grade, String studentId,
                                Object extra, ViewHandler viewHandler) {
        NetData.init(context);
        NetData.setExtra(extra);
        NetData.register(userName, password, passwordRepeat,
                nickName, email, motto, name, sex, size, phone,
                school, departmentId, grade, studentId, viewHandler);
    }

    public static void getAvatar(Context context, String email, ViewHandler viewHandler) {
        getAvatar(context, email, null, false, viewHandler);
    }
    public static void getAvatar(Context context, String email, boolean requestInOrder, ViewHandler viewHandler) {
        getAvatar(context, email, null, requestInOrder, viewHandler);
    }
    public static void getAvatar(Context context, String email, Object extra, ViewHandler viewHandler) {
        getAvatar(context, email, extra, false, viewHandler);
    }
    public static void getAvatar(Context context, String email, Object extra, boolean requestInOrder, ViewHandler viewHandler) {
        NetData.init(context);
        NetData.setExtra(extra);
        NetData.setIfInOrder(requestInOrder);
        NetData.getAvatar(email, viewHandler);
    }


    public static void submitCode(Context context, String codeContent, int languageId, int contestId, int problemId, ViewHandler viewHandler) {
        submitCode(context, codeContent, languageId, contestId, problemId, null, viewHandler);
    }
    public static void submitCode(Context context, String codeContent, int languageId, int contestId, int problemId, Object extra, ViewHandler viewHandler) {
        NetData.init(context);
        NetData.setExtra(extra);
        NetData.submitCode(codeContent, languageId, contestId, problemId, viewHandler);
    }

    public static void getStatusInfo(Context context, int statusId, ViewHandler viewHandler) {
        getStatusInfo(context, statusId, null, viewHandler);
    }
    public static void getStatusInfo(Context context, int statusId, Object extra, ViewHandler viewHandler) {
        NetData.init(context);
        NetData.setExtra(extra);
        NetData.getStatusInfo(statusId, viewHandler);
    }

    public static void getStatusList(Context context, int contestId, int page, ViewHandler viewHandler) {
        getStatusList(context, -1, "", contestId, page, null, false, viewHandler);
    }
    public static void getStatusList(Context context, int contestId, int page, boolean requestInOrder, ViewHandler viewHandler) {
        getStatusList(context, -1, "", contestId, page, null, requestInOrder, viewHandler);
    }
    public static void getStatusList(Context context, int contestId, int page, Object extra, ViewHandler viewHandler) {
        getStatusList(context, -1, "", contestId, page, extra, false, viewHandler);
    }
    public static void getStatusList(Context context, int contestId, int page, Object extra, boolean requestInOrder, ViewHandler viewHandler) {
        getStatusList(context, -1, "", contestId, page, extra, requestInOrder, viewHandler);
    }
    public static void getStatusList(Context context, String userName, int page, ViewHandler viewHandler) {
        getStatusList(context, -1, userName, -1, page, null, false, viewHandler);
    }
    public static void getStatusList(Context context, String userName, int page, boolean requestInOrder, ViewHandler viewHandler) {
        getStatusList(context, -1, userName, -1, page, null, requestInOrder, viewHandler);
    }
    public static void getStatusList(Context context, String userName, int page, Object extra, ViewHandler viewHandler) {
        getStatusList(context, -1, userName, -1, page, extra, false, viewHandler);
    }
    public static void getStatusList(Context context, String userName, int page, Object extra, boolean requestInOrder, ViewHandler viewHandler) {
        getStatusList(context, -1, userName, -1, page, extra, requestInOrder, viewHandler);
    }
    public static void getStatusList(Context context, Integer problemId, int page, ViewHandler viewHandler) {
        getStatusList(context, problemId, "", -1, page, null, false, viewHandler);
    }
    public static void getStatusList(Context context, Integer problemId, int page, boolean requestInOrder, ViewHandler viewHandler) {
        getStatusList(context, problemId, "", -1, page, null, requestInOrder, viewHandler);
    }
    public static void getStatusList(Context context, Integer problemId, int page, Object extra, ViewHandler viewHandler) {
        getStatusList(context, problemId, "", -1, page, extra, false, viewHandler);
    }
    public static void getStatusList(Context context, Integer problemId, int page, Object extra, boolean requestInOrder, ViewHandler viewHandler) {
        getStatusList(context, problemId, "", -1, page, extra, requestInOrder, viewHandler);
    }
    public static void getStatusList(Context context, int problemId, String userName, int contestId, int page, boolean requestInOrder, ViewHandler viewHandler) {
        getStatusList(context, problemId, userName, contestId, page, null, requestInOrder, viewHandler);
    }
    public static void getStatusList(Context context, int problemId, String userName, int contestId, int page, Object extra, ViewHandler viewHandler) {
        getStatusList(context, problemId, userName, contestId, page, extra, false, viewHandler);
    }
    public static void getStatusList(Context context, int problemId, String userName, int contestId, int page, Object extra, boolean requestInOrder, ViewHandler viewHandler) {
        NetData.init(context);
        NetData.setExtra(extra);
        NetData.setIfInOrder(requestInOrder);
        NetData.getStatusList(problemId, userName, contestId, page, viewHandler);
    }

    public static void getContestComment(Context context, int contestId, int page, ViewHandler viewHandler) {
        getContestComment(context, contestId, page, null, false, viewHandler);
    }
    public static void getContestComment(Context context, int contestId, int page, boolean requestInOrder, ViewHandler viewHandler) {
        getContestComment(context, contestId, page, null, requestInOrder, viewHandler);
    }
    public static void getContestComment(Context context, int contestId, int page, Object extra, ViewHandler viewHandler) {
        getContestComment(context, contestId, page, extra, false, viewHandler);
    }
    public static void getContestComment(Context context, int contestId, int page, Object extra, boolean requestInOrder, ViewHandler viewHandler) {
        NetData.init(context);
        NetData.setExtra(extra);
        NetData.setIfInOrder(requestInOrder);
        NetData.getContestComment(contestId, page, viewHandler);
    }

    public static void getContestRank(Context context, int contestId, ViewHandler viewHandler) {
        getContestRank(context, contestId, null, false, viewHandler);
    }
    public static void getContestRank(Context context, int contestId, boolean requestInOrder, ViewHandler viewHandler) {
        getContestRank(context, contestId, null, requestInOrder, viewHandler);
    }
    public static void getContestRank(Context context, int contestId, Object extra, ViewHandler viewHandler) {
        getContestRank(context, contestId, extra, false, viewHandler);
    }
    public static void getContestRank(Context context, int contestId, Object extra, boolean requestInOrder, ViewHandler viewHandler) {
        NetData.init(context);
        NetData.setExtra(extra);
        NetData.setIfInOrder(requestInOrder);
        NetData.getContestRank(contestId, viewHandler);
    }

    public static void loginContest(Context context, int contestId, String password, ViewHandler viewHandler) {
        loginContest(context, contestId, password, null, viewHandler);
    }
    public static void loginContest(Context context, int contestId, String password, Object extra, ViewHandler viewHandler) {
        NetData.init(context);
        NetData.setExtra(extra);
        NetData.loginContest(contestId, password, viewHandler);
    }


    public static void getArticleList(Context context, int page, ViewHandler viewHandler) {
        getArticleList(context, page, null, false, viewHandler);
    }
    public static void getArticleList(Context context, int page, boolean requestInOrder, ViewHandler viewHandler) {
        getArticleList(context, page, null, requestInOrder, viewHandler);
    }
    public static void getArticleList(Context context, int page, Object extra, ViewHandler viewHandler) {
        getArticleList(context, page, extra, false, viewHandler);
    }
    public static void getArticleList(Context context, int page, Object extra, boolean requestInOrder, ViewHandler viewHandler) {
        NetData.init(context);
        NetData.setExtra(extra);
        NetData.setIfInOrder(requestInOrder);
        NetData.getArticleList(page, viewHandler);
    }

    public static void getProblemList(Context context, int page, ViewHandler viewHandler) {
        getProblemList(context, page, "", 0, null, false, viewHandler);
    }
    public static void getProblemList(Context context, int page, boolean requestInOrder, ViewHandler viewHandler) {
        getProblemList(context, page, "", 0, null, requestInOrder, viewHandler);
    }
    public static void getProblemList(Context context, int page, Object extra,  ViewHandler viewHandler) {
        getProblemList(context, page, "", 0, extra, false, viewHandler);
    }
    public static void getProblemList(Context context, int page, Object extra, boolean requestInOrder, ViewHandler viewHandler) {
        getProblemList(context, page, "", 0, extra, requestInOrder, viewHandler);
    }
    public static void getProblemList(Context context, int page, int startId, ViewHandler viewHandler) {
        getProblemList(context, page, "", startId, null, false, viewHandler);
    }
    public static void getProblemList(Context context, int page, int startId, boolean requestInOrder, ViewHandler viewHandler) {
        getProblemList(context, page, "", startId, null, requestInOrder, viewHandler);
    }
    public static void getProblemList(Context context, int page, int startId, Object extra,  ViewHandler viewHandler) {
        getProblemList(context, page, "", startId, extra, false, viewHandler);
    }
    public static void getProblemList(Context context, int page, int startId, Object extra, boolean requestInOrder, ViewHandler viewHandler) {
        getProblemList(context, page, "", startId, extra, requestInOrder, viewHandler);
    }
    public static void getProblemList(Context context, int page, String keyword, ViewHandler viewHandler) {
        getProblemList(context, page, keyword, 0, null, false, viewHandler);
    }
    public static void getProblemList(Context context, int page, String keyword, boolean requestInOrder, ViewHandler viewHandler) {
        getProblemList(context, page, keyword, 0, null, requestInOrder, viewHandler);
    }
    public static void getProblemList(Context context, int page, String keyword, Object extra,  ViewHandler viewHandler) {
        getProblemList(context, page, keyword, 0, extra, false, viewHandler);
    }
    public static void getProblemList(Context context, int page, String keyword, Object extra, boolean requestInOrder, ViewHandler viewHandler) {
        getProblemList(context, page, keyword, 0, extra, requestInOrder, viewHandler);
    }

    public static void getProblemList(Context context, int page, String keyword, int startId, Object extra, boolean requestInOrder, ViewHandler viewHandler) {
        NetData.init(context);
        NetData.setExtra(extra);
        NetData.setIfInOrder(requestInOrder);
        NetData.getProblemList(page, keyword, startId, viewHandler);
    }

    public static void getContestList(Context context, int page, ViewHandler viewHandler) {
        getContestList(context, page, "", null, false, viewHandler);
    }
    public static void getContestList(Context context, int page, boolean requestInOrder, ViewHandler viewHandler) {
        getContestList(context, page, "", null, requestInOrder, viewHandler);
    }
    public static void getContestList(Context context, int page, Object extra, ViewHandler viewHandler) {
        getContestList(context, page, "", extra, false, viewHandler);
    }
    public static void getContestList(Context context, int page, String keyword, ViewHandler viewHandler) {
        getContestList(context, page, keyword, null, false, viewHandler);
    }
    public static void getContestList(Context context, int page, String keyword, boolean requestInOrder, ViewHandler viewHandler) {
        getContestList(context, page, keyword, null, requestInOrder, viewHandler);
    }
    public static void getContestList(Context context, int page, String keyword, Object extra, ViewHandler viewHandler) {
        getContestList(context, page, keyword, extra, false, viewHandler);
    }
    public static void getContestList(Context context, int page, String keyword, Object extra, boolean requestInOrder, ViewHandler viewHandler) {
        NetData.init(context);
        NetData.setExtra(extra);
        NetData.setIfInOrder(requestInOrder);
        NetData.getContestList(page, keyword, viewHandler);
    }

    public static void getArticleDetail(Context context, int id, ViewHandler viewHandler) {
        getArticleDetail(context, id, null, false, viewHandler);
    }
    public static void getArticleDetail(Context context, int id, boolean requestInOrder, ViewHandler viewHandler) {
        getArticleDetail(context, id, null, requestInOrder, viewHandler);
    }
    public static void getArticleDetail(Context context, int id, Object extra, ViewHandler viewHandler) {
        getArticleDetail(context, id, extra, false, viewHandler);
    }
    public static void getArticleDetail(Context context, int id, Object extra, boolean requestInOrder, ViewHandler viewHandler) {
        NetData.init(context);
        NetData.setExtra(extra);
        NetData.setIfInOrder(requestInOrder);
        NetData.getArticleDetail(id, viewHandler);
    }

    public static void getProblemDetail(Context context, int id, ViewHandler viewHandler) {
        getProblemDetail(context, id, null, false, viewHandler);
    }
    public static void getProblemDetail(Context context, int id, boolean requestInOrder, ViewHandler viewHandler) {
        getProblemDetail(context, id, null, requestInOrder, viewHandler);
    }
    public static void getProblemDetail(Context context, int id, Object extra, ViewHandler viewHandler) {
        getProblemDetail(context, id, extra, false, viewHandler);
    }
    public static void getProblemDetail(Context context, int id, Object extra, boolean requestInOrder, ViewHandler viewHandler) {
        NetData.init(context);
        NetData.setExtra(extra);
        NetData.setIfInOrder(requestInOrder);
        NetData.getProblemDetail(id, viewHandler);
    }

    public static void getContestDetail(Context context, int id, ViewHandler viewHandler) {
        getContestDetail(context, id, null, false, viewHandler);
    }
    public static void getContestDetail(Context context, int id, boolean requestInOrder, ViewHandler viewHandler) {
        getContestDetail(context, id, null, requestInOrder, viewHandler);
    }
    public static void getContestDetail(Context context, int id, Object extra, ViewHandler viewHandler) {
        getContestDetail(context, id, extra, false, viewHandler);
    }
    public static void getContestDetail(Context context, int id, Object extra, boolean requestInOrder, ViewHandler viewHandler) {
        NetData.init(context);
        NetData.setExtra(extra);
        NetData.setIfInOrder(requestInOrder);
        NetData.getContestDetail(id, viewHandler);
    }
}
