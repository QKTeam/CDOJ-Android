package cn.edu.uestc.acm.cdoj.net;

import android.content.Context;

/**
 * CObject extra, created by Grea on 2016/10/21.
 */

public class NetDataPlus {

    public static void login(Context context, String userName, String password, ConvertNetData convertNetData) {
        login(context, userName, password, false, null, convertNetData);
    }

    public static void login(Context context, String userName, String password, boolean isSha1, ConvertNetData convertNetData) {
        login(context, userName, password, isSha1, null, convertNetData);
    }

    public static void login(Context context, String userName, String password, Object extra, ConvertNetData convertNetData) {
        login(context, userName, password, false, extra, convertNetData);
    }

    public static void login(Context context, String userName, String password, boolean isSha1, Object extra, ConvertNetData convertNetData) {
        if (!isSha1) {
            password = NetData.sha1(password);
        }
        NetData.login(context, userName, password, extra, convertNetData);
    }

    public static void logout(Context context, ConvertNetData convertNetData) {
        logout(context, null, convertNetData);
    }

    public static void logout(Context context, Object extra, ConvertNetData convertNetData) {
        NetData.logout(context, extra, convertNetData);
    }

    public static void getUserCenterData(Context context, String userName, ConvertNetData convertNetData) {
        getUserCenterData(context, userName, null, convertNetData);
    }

    public static void getUserCenterData(Context context, String userName, Object extra, ConvertNetData convertNetData) {
        NetData.getUserCenterData(context, userName, extra, convertNetData);
    }

    public static void getUserTypeAheadItem(Context context, String userName, ConvertNetData convertNetData) {
        getUserTypeAheadItem(context, userName, null, convertNetData);
    }

    public static void getUserTypeAheadItem(Context context, String userName, Object extra, ConvertNetData convertNetData) {
        NetData.getUserTypeAheadItem(context, userName, extra, convertNetData);
    }

    public static void getUserProfile(Context context, String userName, ConvertNetData convertNetData) {
        getUserProfile(context, userName, null, convertNetData);
    }

    public static void getUserProfile(Context context, String userName, Object extra, ConvertNetData convertNetData) {
        NetData.getUserProfile(context, userName, extra, convertNetData);
    }

    public static void register(Context context, String userName, String password,
                                String passwordRepeat, String nickName, String email,
                                String motto, String name, int sex, int size, String phone,
                                String school, int departmentId, int grade, String studentId,
                                ConvertNetData convertNetData) {
        register(context, userName, password, passwordRepeat,
                nickName, email, motto, name, sex, size, phone,
                school, departmentId, grade, studentId, null, convertNetData);
    }

    public static void register(Context context, String userName, String password,
                                String passwordRepeat, String nickName, String email,
                                String motto, String name, int sex, int size, String phone,
                                String school, int departmentId, int grade, String studentId,
                                Object extra, ConvertNetData convertNetData) {
        NetData.register(context, userName, password, passwordRepeat,
                nickName, email, motto, name, sex, size, phone,
                school, departmentId, grade, studentId, extra, convertNetData);
    }

    public static void getAvatar(Context context, String email, ConvertNetData convertNetData) {
        NetData.getAvatar(context, email, null, convertNetData);
    }

    public static void getAvatar(Context context, String email, Object extra, ConvertNetData convertNetData) {
        NetData.getAvatar(context, email, extra, convertNetData);
    }

    public static void submitCode(Context context, String codeContent, int languageId, int contestId, int problemId, ConvertNetData convertNetData) {
        submitCode(context, codeContent, languageId, contestId, problemId, null, convertNetData);
    }

    public static void submitCode(Context context, String codeContent, int languageId, int problemId, ConvertNetData convertNetData) {
        submitCode(context, codeContent, languageId, -1, problemId, null, convertNetData);
    }
    public static void submitCode(Context context, String codeContent, int languageId, int problemId, Object extra, ConvertNetData convertNetData) {
        submitCode(context, codeContent, languageId, -1, problemId, extra, convertNetData);
    }

    public static void submitCode(Context context, String codeContent, int languageId, int contestId, int problemId, Object extra, ConvertNetData convertNetData) {
        NetData.submitCode(context, codeContent, languageId, contestId, problemId, extra, convertNetData);
    }

    public static void getStatusInfo(Context context, int statusId, ConvertNetData convertNetData) {
        getStatusInfo(context, statusId, null, convertNetData);
    }

    public static void getStatusInfo(Context context, int statusId, Object extra, ConvertNetData convertNetData) {
        NetData.getStatusInfo(context, statusId, extra, convertNetData);
    }

    public static void getStatusList(Context context, int contestId, int page, Object extra, ConvertNetData convertNetData) {
        getStatusList(context, -1, "", contestId, page, extra, convertNetData);
    }

    public static void getStatusList(Context context, int contestId, int page, ConvertNetData convertNetData) {
        getStatusList(context, -1, "", contestId, page, null, convertNetData);
    }

    public static void getStatusList(Context context, String userName, int page, Object extra, ConvertNetData convertNetData) {
        getStatusList(context, -1, userName, -1, page, extra, convertNetData);
    }

    public static void getStatusList(Context context, String userName, int page, ConvertNetData convertNetData) {
        getStatusList(context, -1, userName, -1, page, null, convertNetData);
    }

    public static void getStatusList(Context context, Integer problemId, int page, Object extra, ConvertNetData convertNetData) {
        getStatusList(context, problemId, "", -1, page, extra, convertNetData);
    }

    public static void getStatusList(Context context, Integer problemId, int page, ConvertNetData convertNetData) {
        getStatusList(context, problemId, "", -1, page, null, convertNetData);
    }

    public static void getStatusList(Context context, int problemId, String userName, int contestId, int page, ConvertNetData convertNetData) {
        getStatusList(context, problemId, userName, contestId, page, null, convertNetData);
    }

    public static void getStatusList(Context context, int problemId, String userName, int contestId, int page, Object extra, ConvertNetData convertNetData) {
        NetData.getStatusList(context, problemId, userName, contestId, page, extra, convertNetData);
    }

    public static void getContestComment(Context context, int contestId, int page, ConvertNetData convertNetData) {
        getContestComment(context, contestId, page, null, convertNetData);
    }

    public static void getContestComment(Context context, int contestId, int page, Object extra, ConvertNetData convertNetData) {
        NetData.getContestComment(context, contestId, page, extra, convertNetData);
    }


    public static void getContestRank(Context context, int contestId, ConvertNetData convertNetData) {
        getContestRank(context, contestId, null, convertNetData);
    }

    public static void getContestRank(Context context, int contestId, Object extra, ConvertNetData convertNetData) {
        NetData.getContestRank(context, contestId, extra, convertNetData);
    }


    public static void loginContest(Context context, int contestId, String password, ConvertNetData convertNetData) {
        loginContest(context, contestId, password, null, convertNetData);
    }

    public static void loginContest(Context context, int contestId, String password, Object extra, ConvertNetData convertNetData) {
        NetData.loginContest(context, contestId, password, extra, convertNetData);
    }


    public static void getArticleList(Context context, int page, ConvertNetData convertNetData) {
        getArticleList(context, page, null, convertNetData);
    }

    public static void getArticleList(Context context, int page, Object extra, ConvertNetData convertNetData) {
        NetData.getArticleList(context, page, extra, convertNetData);
    }


    public static void getProblemList(Context context, int page, Object extra, ConvertNetData convertNetData) {
        getProblemList(context, page, "", 0, extra, convertNetData);
    }

    public static void getProblemList(Context context, int page, ConvertNetData convertNetData) {
        getProblemList(context, page, "", 0, null, convertNetData);
    }

    public static void getProblemList(Context context, int page, int startId, Object extra, ConvertNetData convertNetData) {
        getProblemList(context, page, "", startId, extra, convertNetData);
    }

    public static void getProblemList(Context context, int page, int startId, ConvertNetData convertNetData) {
        getProblemList(context, page, "", startId, null, convertNetData);
    }

    public static void getProblemList(Context context, int page, String keyword, Object extra, ConvertNetData convertNetData) {
        getProblemList(context, page, keyword, 0, extra, convertNetData);
    }

    public static void getProblemList(Context context, int page, String keyword, ConvertNetData convertNetData) {
        getProblemList(context, page, keyword, 0, null, convertNetData);
    }

    public static void getProblemList(Context context, int page, String keyword, int startId, ConvertNetData convertNetData) {
        getProblemList(context, page, keyword, startId, null, convertNetData);
    }

    public static void getProblemList(Context context, int page, String keyword, int startId, Object extra, ConvertNetData convertNetData) {
        NetData.getProblemList(context, page, keyword, startId, extra, convertNetData);
    }

    public static void getContestList(Context context, int page, ConvertNetData convertNetData) {
        getContestList(context, page, "", 1, null, convertNetData);
    }

    public static void getContestList(Context context, int page, Object extra, ConvertNetData convertNetData) {
        getContestList(context, page, "", 1, extra, convertNetData);
    }

    public static void getContestList(Context context, int page, String keyword, ConvertNetData convertNetData) {
        getContestList(context, page, keyword, 1, null, convertNetData);
    }

    public static void getContestList(Context context, int page, String keyword, Object extra, ConvertNetData convertNetData) {
        getContestList(context, page, keyword, 1, extra, convertNetData);
    }

    public static void getContestList(Context context, int page, int startId, ConvertNetData convertNetData) {
        getContestList(context, page, "", startId, null, convertNetData);
    }

    public static void getContestList(Context context, int page, int startId, Object extra, ConvertNetData convertNetData) {
        getContestList(context, page, "", startId, extra, convertNetData);
    }

    public static void getContestList(Context context, int page, String keyword, int startId, ConvertNetData convertNetData) {
        getContestList(context, page, keyword, startId, null, convertNetData);
    }

    public static void getContestList(Context context, int page, String keyword, int startId, Object extra, ConvertNetData convertNetData) {
        NetData.getContestList(context, page, keyword, startId, extra, convertNetData);
    }

    public static void getArticleDetail(Context context, int id, ConvertNetData convertNetData) {
        getArticleDetail(context, id, null, convertNetData);
    }

    public static void getArticleDetail(Context context, int id, Object extra, ConvertNetData convertNetData) {
        NetData.getArticleDetail(context, id, extra, convertNetData);
    }

    public static void getProblemDetail(Context context, int id, ConvertNetData convertNetData) {
        getProblemDetail(context, id, null, convertNetData);
    }

    public static void getProblemDetail(Context context, int id, Object extra, ConvertNetData convertNetData) {
        NetData.getProblemDetail(context, id, extra, convertNetData);
    }

    public static void getContestDetail(Context context, int id, ConvertNetData convertNetData) {
        getContestDetail(context, id, null, convertNetData);
    }

    public static void getContestDetail(Context context, int id, Object extra, ConvertNetData convertNetData) {
        NetData.getContestDetail(context, id, extra, convertNetData);
    }
}
