package cn.edu.uestc.acm.cdoj.net;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.IntDef;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Vector;

import cn.edu.uestc.acm.cdoj.net.utils.NetThread;
import cn.edu.uestc.acm.cdoj.net.utils.NetWorkUtils;

/**
 * Created by Grea on 16-10-22.
 */
public class NetData {

    public final static int PROBLEM_LIST = 0;
    public final static int CONTEST_LIST = 1;
    public final static int ARTICLE_LIST = 2;
    public final static int PROBLEM_DETAIL = 3;
    public final static int CONTEST_DETAIL = 4;
    public final static int ARTICLE_DETAIL = 5;
    public final static int LOGIN = 6;
    public final static int LOGOUT = 7;
    public final static int LOGIN_CONTEST = 8;
    public final static int CONTEST_COMMENT = 9;
    public final static int CONTEST_RANK = 10;
    public final static int STATUS_LIST = 11;
    public final static int STATUS_INFO = 12;
    public final static int STATUS_SUBMIT = 13;
    public final static int AVATAR = 14;
    public final static int REGISTER = 15;
    public final static int USER_PROFILE = 16;
    public final static int USER_TYPE_AHEAD_ITEM = 17;
    public final static int USER_CENTER_DATA = 18;

    @IntDef({PROBLEM_LIST, CONTEST_LIST, ARTICLE_LIST, PROBLEM_DETAIL, CONTEST_DETAIL, ARTICLE_DETAIL,
            LOGIN, LOGOUT, LOGIN_CONTEST, CONTEST_COMMENT, CONTEST_RANK, STATUS_LIST, STATUS_INFO,
            STATUS_SUBMIT, AVATAR, REGISTER, USER_PROFILE, USER_TYPE_AHEAD_ITEM, USER_CENTER_DATA})
    @Retention(RetentionPolicy.SOURCE)
    public @interface NetRequestType {
    }

    static String TAG = "-------NetDataTag-----";

    private final static String avatarUrl = "http://cdn.v2ex.com/gravatar/%@.jpg?s=200&&d=retro";

    private final static String ojBaseUrl = "http://acm.uestc.edu.cn";
    private final static String problemListUrl = ojBaseUrl + "/problem/search";
    private final static String contestListUrl = ojBaseUrl + "/contest/search";
    private final static String articleListUrl = ojBaseUrl + "/article/search";
    private final static String articleDetailUrl = ojBaseUrl + "/article/data/";
    private final static String problemDetailUrl = ojBaseUrl + "/problem/data/";
    private final static String contestDetailUrl = ojBaseUrl + "/contest/data/";
    private final static String loginUrl = ojBaseUrl + "/user/login";
    private final static String logoutUrl = ojBaseUrl + "/user/logout";
    private final static String loginContestUrl = ojBaseUrl + "/contest/loginContest";
    private final static String contestCommentUrl = ojBaseUrl + "/article/commentSearch";
    private final static String contestRankListUrl = ojBaseUrl + "/contest/rankList/";
    private final static String statusListUrl = ojBaseUrl + "/status/search";
    private final static String statusInfoUrl = ojBaseUrl + "/status/info/";
    private final static String codeSubmitUrl = ojBaseUrl + "/status/submit";
    private final static String registerUrl = ojBaseUrl + "/user/register";
    private final static String userProfileUrl = ojBaseUrl + "/user/profile/";
    private final static String userTypeAheadItemUrl = ojBaseUrl + "/user/typeAheadItem/";
    private final static String userCenterDataUrl = ojBaseUrl + "/user/userCenterData/";

    private static NetThread mNetThread;
    private static Vector<Thread> avatarThreadList = new Vector<>(); //应寻找LinkedList的线程安全版本

    static void getUserCenterData(Context context, String userName, Object extra, ConvertNetData convertNetData) {
        get(context, USER_CENTER_DATA, userCenterDataUrl + userName, extra, convertNetData);
    }

    static void getUserTypeAheadItem(Context context, String userName, Object extra, ConvertNetData convertNetData) {
        get(context, USER_TYPE_AHEAD_ITEM, userTypeAheadItemUrl + userName, extra, convertNetData);
    }

    static void getUserProfile(Context context, String userName, Object extra, ConvertNetData convertNetData) {
        get(context, USER_PROFILE, userProfileUrl + userName, extra, convertNetData);
    }

    static void register(Context context, String userName, String password,
                         String passwordRepeat, String nickName, String email,
                         String motto, String name, int sex, int size, String phone,
                         String school, int departmentId, int grade,
                         String studentId, Object extra, ConvertNetData convertNetData) {

        String key[] = new String[]{
                "userName", "password", "passwordRepeat",
                "nickName", "email", "motto", "name", "sex",
                "size", "phone", "school", "departmentId",
                "grade", "studentId"};
        Object o[] = new Object[]{
                userName, password, passwordRepeat,
                nickName, email, motto, name, sex,
                size, phone, school, departmentId,
                grade, studentId};
        post(context, REGISTER, constructJson(key, o), registerUrl, extra, convertNetData);
    }

    static void getAvatar(Context context, final String email, final Object extra, final ConvertNetData convertNetData) {
        BitmapDrawable avatarDrawable = readAvatarFromLocal(context, email);
        if (avatarDrawable != null) {
            convertNetData.onNetDataConverted(new Result(AVATAR, NetHandler.Status.SUCCESS, extra, avatarDrawable));
        } else {
            Thread avatarThread = new Thread() {
                @Override
                public void run() {
                    convertNetData.onNetDataConverted(
                            new Result(AVATAR, NetHandler.Status.SUCCESS, extra, NetWorkUtils.getAvatar(avatarUrl.replace("%@", email))));

                    avatarThreadList.remove(this);
                    if (avatarThreadList.get(0) != null) {
                        avatarThreadList.get(0).start();
                    }
                }
            };
            avatarThreadList.add(avatarThread);
            if (avatarThreadList.size() < 5) {
                avatarThread.start();
            }
        }
    }

    static void submitCode(Context context, String codeContent, int languageId,
                           int contestId, int problemId, Object extra, ConvertNetData convertNetData) {
        String key[] = new String[]{"codeContent", "languageId", "contestId", "problemId"};
        Object o[] = new Object[]{codeContent, languageId, contestId == -1 ? "" : contestId, problemId};
        post(context, STATUS_SUBMIT, constructJson(key, o), codeSubmitUrl, extra, convertNetData);
    }

    static void getStatusInfo(Context context, int statusId, Object extra, ConvertNetData convertNetData) {
        get(context, STATUS_INFO, statusInfoUrl + statusId, extra, convertNetData);
    }

    static void getStatusList(Context context, int problemId, String userName,
                              int contestId, int page, Object extra, ConvertNetData convertNetData) {
        String key[] = new String[]{"problemId", "userName", "contestId", "currentPage", "orderAsc", "orderFields", "result"};
        Object[] o = new Object[]{problemId == -1 ? "" : problemId, userName, contestId, page, false, "statusId", 0};
        post(context, STATUS_LIST, constructJson(key, o), statusListUrl, extra, convertNetData);
    }

    static void getContestComment(Context context, int contestId, int page, Object extra, ConvertNetData convertNetData) {
        String key[] = new String[]{"contestId", "currentPage", "orderAsc", "orderFields"};
        Object[] o = new Object[]{contestId, page, false, "id"};
        post(context, CONTEST_COMMENT, constructJson(key, o), contestCommentUrl, extra, convertNetData);
    }

    static void getContestRank(Context context, int contestId, Object extra, ConvertNetData convertNetData) {
        get(context, CONTEST_RANK, contestRankListUrl + contestId, extra, convertNetData);
    }

    static void loginContest(Context context, int contestId, String password, Object extra, ConvertNetData convertNetData) {
        String postJsonString = "";
        try {
            postJsonString = new JSONObject()
                    .put("contestId", contestId)
                    .put("password", password)
                    .toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        post(context, LOGIN_CONTEST, postJsonString, loginContestUrl, extra, convertNetData);
    }

    static void login(Context context, String userName, String password, Object extra, ConvertNetData convertNetData) {
        String postJsonString = "";
        try {
            postJsonString = new JSONObject()
                    .put("userName", userName)
                    .put("password", password)
                    .toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        post(context, LOGIN, postJsonString, loginUrl, extra, convertNetData);
    }

    static void logout(Context context, Object extra, ConvertNetData convertNetData) {
        get(context, LOGOUT, logoutUrl, extra, convertNetData);
    }

    static void getArticleList(Context context, final int page, Object extra, final ConvertNetData convertNetData) {
        String postJsonString = "";
        try {
            postJsonString = new JSONObject()
                    .put("currentPage", page)
                    .put("orderAsc", "true")
                    .put("orderFields", "order")
                    .put("type", 0)
                    .toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        post(context, ARTICLE_LIST, postJsonString, articleListUrl, extra, convertNetData);
    }

    static void getProblemList(Context context, final int page, String keyword, int startId, Object extra, final ConvertNetData convertNetData) {
        String postJsonString = "";
        try {
            postJsonString = new JSONObject()
                    .put("currentPage", page)
                    .put("orderAsc", "true")
                    .put("orderFields", "id")
                    .put("keyword", keyword)
                    .put("startId", startId)
                    .toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        post(context, PROBLEM_LIST, postJsonString, problemListUrl, extra, convertNetData);
    }

    static void getContestList(Context context, int page, String keyword, Object extra, ConvertNetData convertNetData) {
        String postJsonString = "";
        try {
            postJsonString = new JSONObject()
                    .put("currentPage", page)
                    .put("orderAsc", "false")
                    .put("orderFields", "time")
                    .put("keyword", keyword)
                    .toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        post(context, CONTEST_LIST, postJsonString, contestListUrl, extra, convertNetData);
    }

    static void getArticleDetail(Context context, final int id, Object extra, final ConvertNetData convertNetData) {
        get(context, ARTICLE_DETAIL, articleDetailUrl + id, extra, convertNetData);
    }

    static void getProblemDetail(Context context, final int id, Object extra, final ConvertNetData convertNetData) {
        get(context, PROBLEM_DETAIL, problemDetailUrl + id, extra, convertNetData);
    }

    static void getContestDetail(Context context, final int id, Object extra, final ConvertNetData convertNetData) {
        get(context, CONTEST_DETAIL, contestDetailUrl + id, extra, convertNetData);
    }

    private static void post(Context context, @NetRequestType int requestType,
                             String postJsonString, String urlString, Object extra, ConvertNetData convertNetData) {

        if (mNetThread == null) {
            mNetThread = new NetThread();
            mNetThread.start();
        }
        NetHandler handler = new NetHandler(context, requestType, urlString, extra, convertNetData);
        handler.setRequestMethod(NetHandler.Request.POST);
        handler.setPostJsonString(postJsonString);
        handler.sendEmptyMessage(NetHandler.Thread.NETTHREAD);
    }

    private static void get(Context context, @NetRequestType int requestType,
                            String urlString, Object extra, ConvertNetData convertNetData) {

        if (mNetThread == null) {
            mNetThread = new NetThread();
            mNetThread.start();
        }
        NetHandler handler = new NetHandler(context, requestType, urlString, extra, convertNetData);
        handler.sendEmptyMessage(NetHandler.Thread.NETTHREAD);
    }

    private static BitmapDrawable readAvatarFromLocal(Context context, String email) {
        File avatarFile = new File(context.getCacheDir() + File.separator + email);
        if (!avatarFile.exists()) {
            return null;
        } else {
            return (BitmapDrawable) BitmapDrawable.createFromPath(avatarFile.getPath());
        }
    }

    private static String constructJson(String key[], Object o[]) {
        JSONObject temp = new JSONObject();
        try {
            for (int i = 0; i < key.length; i++) {
                temp.put(key[i], o[i]);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return temp.toString();
    }
}
