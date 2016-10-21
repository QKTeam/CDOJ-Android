package cn.edu.uestc.acm.cdoj.tools;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import cn.edu.uestc.acm.cdoj.net.NetData;
import cn.edu.uestc.acm.cdoj.net.NetWorkTool;
import cn.edu.uestc.acm.cdoj.net.ViewHandler;
import cn.edu.uestc.acm.cdoj.net.data.Result;
import cn.edu.uestc.acm.cdoj.ui.modules.Global;

/**
 * Created by Grea on 2016/10/21.
 */

public class NetDataPlus {
    public static void getUserCenterData(String userName, ViewHandler viewHandler){
        async(ViewHandler.USER_CENTER_DATA,  userCenterDataUrl + userName, null, viewHandler);
    }
    public static void getUserTypeAheadItem(String userName, ViewHandler viewHandler){
        async(ViewHandler.USER_PROFILE,  userTypeAheadItemUrl + userName, null, viewHandler);
    }
    public static void getUserProfile(String userName, ViewHandler viewHandler){
        async(ViewHandler.USER_PROFILE,  userProfileUrl + userName, null, viewHandler);
    }
    public static void register(String userName, String password, String passwordRepeat, String nickName, String email, String motto, String name, int sex, int size, String phone, String school, int departmentId, int grade, String studentId, ViewHandler viewHandler){
        String key[] = new String[]{"userName", "password", "passwordRepeat", "nickName", "email", "motto", "name", "sex", "size", "phone", "school", "departmentId", "grade", "studentId"};
        Object o[] = new Object[]{userName, password, passwordRepeat, nickName, email, motto, name, sex, size, phone, school, departmentId, grade, studentId};
        async(ViewHandler.REGISTER, registerUrl ,constructJson(key, o), viewHandler);
    }

    public static void getAvatar(String email, ViewHandler viewHandler){
        getAvatar(email, null, false, viewHandler);
    }

    public static void getAvatar(String email, boolean requestInOrder, ViewHandler viewHandler){
        getAvatar(email, null, requestInOrder, viewHandler);
    }

    public static void getAvatar(String email, Object extra, ViewHandler viewHandler){
        getAvatar(email, extra, false, viewHandler);
    }

    public static void getAvatar(String email, Object extra, boolean requestInOrder, ViewHandler viewHandler){
        NetData.init(context);
        NetData.setExtra(extra);
        NetData.setIfInOrder(requestInOrder);
        NetData.getAvatar(email, viewHandler);
    }


    public static void submitCode(String codeContent, int languageId, int contestId, int problemId, ViewHandler viewHandler){
        String key[] = new String[]{"codeContent", "languageId", "contestId", "problemId"};
        Object o[] = new Object[]{codeContent, languageId, contestId == -1?"":contestId, problemId};
        async(ViewHandler.STATUS_SUBMIT, codeSubmitUrl, constructJson(key, o), viewHandler);
    }
    public static void getStatusInfo(int statusId, ViewHandler viewHandler){
        async(ViewHandler.STATUS_INFO, statusInfoUrl + statusId, null, viewHandler);
    }

    public static void getStatusList(Context context, String userName, int page, Object extra, ViewHandler viewHandler){
        getStatusList(context, -1, userName, contestId, page, extra, false, viewHandler);
    }

    public static void getStatusList(Context context, Integer problemId, int page, Object extra, boolean requestInOrder, ViewHandler viewHandler) {
        getStatusList(context, problemId, null, contestId, page, extra, requestInOrder, viewHandler);
    }

    public static void getStatusList(Context context, int contestId, int page, Object extra, boolean requestInOrder, ViewHandler viewHandler){
        getStatusList(context, -1, null, contestId, page, extra, requestInOrder,viewHandler);
    }

    public static void getStatusList(Context context, int problemId, String userName, int contestId, int page, boolean requestInOrder, ViewHandler viewHandler){
        getStatusList(context, problemId, userName, contestId, page, null, requestInOrder, viewHandler);
    }

    public static void getStatusList(Context context, int problemId, String userName, int contestId, int page, Object extra, ViewHandler viewHandler){
        getStatusList(context, problemId, userName, contestId, page, extra, false, viewHandler);
    }

    public static void getStatusList(Context context, int problemId, String userName, int contestId, int page, Object extra, boolean requestInOrder, ViewHandler viewHandler){
        NetData.init(context);
        NetData.setExtra(extra);
        NetData.setIfInOrder(requestInOrder);
        NetData.getStatusList(problemId, userName, contestId, page, viewHandler);
    }
    public static void getContestComment(int contestId, int page, ViewHandler viewHandler){
        String key[] = new String[]{"contestId", "currentPage", "orderAsc", "orderFields"};
        Object[] o = new Object[]{contestId, page, false, "id"};
        async(ViewHandler.CONTEST_COMMENT, contestCommentUrl, constructJson(key, o), viewHandler);
    }
    public static void getContestRank(int contestId, ViewHandler viewHandler){
        async(ViewHandler.CONTEST_RANK, contestRankListUrl + contestId, null, viewHandler);
    }
    public static void loginContest(int contestId, String password, ViewHandler viewHandler){
        String p = "";
        try {
            p = new JSONObject().put("contestId", contestId).put("password", NetWorkTool.sha1(password)).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        async(ViewHandler.LOGIN_CONTEST, loginContestUrl, p, viewHandler);
    }
    public static void login(String userName, String sha1password, ViewHandler viewHandler){
        String p = "";
        try {
            p = new JSONObject().put("userName", userName).put("password", sha1password).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        async(ViewHandler.LOGIN, loginUrl, p, viewHandler);
    }
    public static void logout(ViewHandler viewHandler){
        async(ViewHandler.LOGOUT, logoutUrl, null, viewHandler);
    }


    public static void getProblemList(Context context, int page, String keyword, int startId,  ViewHandler viewHandler) {
        getProblemList(context, page, keyword, startId, null, false, viewHandler);
    }

    public static void getProblemList(Context context, int page, String keyword, int startId, boolean requestInOrder, ViewHandler viewHandler) {
        getProblemList(context, page, keyword, startId, null, requestInOrder, viewHandler);
    }

    public static void getProblemList(Context context, int page, String keyword, Object extra, int startId, ViewHandler viewHandler) {
        getProblemList(context, page, keyword, startId, extra, false, viewHandler);
    }

    public static void getProblemList(Context context, int page, String keyword, int startId, Object extra, boolean requestInOrder, ViewHandler viewHandler) {
        NetData.init(context);
        NetData.setExtra(extra);
        NetData.setIfInOrder(requestInOrder);
        NetData.getProblemList(page, keyword, startId, viewHandler);
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

    public static void getArticleList(Context context, int page, ViewHandler viewHandler){
        getArticleList(context, page, null, false, viewHandler);
    }

    public static void getArticleList(Context context, int page, boolean requestInOrder, ViewHandler viewHandler){
        getArticleList(context, page, null, requestInOrder, viewHandler);
    }

    public static void getArticleList(Context context, int page, Object extra, ViewHandler viewHandler){
        getArticleList(context, page, extra, false, viewHandler);
    }

    public static void getArticleList(Context context, int page, Object extra, boolean requestInOrder, ViewHandler viewHandler){
        NetData.init(context);
        NetData.setExtra(extra);
        NetData.setIfInOrder(requestInOrder);
        NetData.getArticleList(page, viewHandler);
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

    public static void getProblemDetail(Context context, int id, ViewHandler viewHandler){
        getProblemDetail(context, id, null, false, viewHandler);
    }

    public static void getProblemDetail(Context context, int id, boolean requestInOrder, ViewHandler viewHandler){
        getProblemDetail(context, id, null, requestInOrder, viewHandler);
    }

    public static void getProblemDetail(Context context, int id, Object extra, ViewHandler viewHandler){
        getProblemDetail(context, id, extra, false, viewHandler);
    }

    public static void getProblemDetail(Context context, int id, Object extra, boolean requestInOrder, ViewHandler viewHandler){
        NetData.init(context);
        NetData.setExtra(extra);
        NetData.setIfInOrder(requestInOrder);
        NetData.getProblemDetail(id, viewHandler);
    }

    public static void getContestDetail(Context context, int id, ViewHandler viewHandler){
        getContestDetail(context,id, null, false, viewHandler);
    }

    public static void getContestDetail(Context context, int id, boolean requestInOrder, ViewHandler viewHandler){
        getContestDetail(context,id, null, requestInOrder, viewHandler);
    }

    public static void getContestDetail(Context context, int id, Object extra, ViewHandler viewHandler){
        getContestDetail(context,id, extra, false, viewHandler);
    }

    public static void getContestDetail(Context context, int id, Object extra, boolean requestInOrder, ViewHandler viewHandler){
        NetData.init(context);
        NetData.setExtra(extra);
        NetData.setIfInOrder(requestInOrder);
        NetData.getContestDetail(id, viewHandler);
    }

}
