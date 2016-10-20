package cn.edu.uestc.acm.cdoj.net;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.edu.uestc.acm.cdoj.net.data.Result;

/**
 * Created by qwe on 16-8-14.
 */
public class NetData {

    static String TAG = "-------NetDataTag-----";


    public final static String severAddress = "http://acm.uestc.edu.cn";
    private final static String
            problemListUrl = severAddress + "/problem/search",
            contestListUrl = severAddress + "/contest/search",
            articleListUrl = severAddress + "/article/search",
            articleDetailUrl= severAddress + "/article/data/",
            problemDetailUrl = severAddress + "/problem/data/",
            contestDetailUrl = severAddress + "/contest/data/",
            loginUrl = severAddress + "/user/login",
            logoutUrl = severAddress + "/user/logout",
            loginContestUrl = severAddress + "/contest/loginContest",
            contestCommentUrl = severAddress + "/article/commentSearch",
            contestRankListUrl = severAddress + "/contest/rankList/",
            statusListUrl = severAddress + "/status/search",
            statusInfoUrl = severAddress + "/status/info/",
            codeSubmitUrl = severAddress + "/status/submit",
            avatarUrl = "http://cdn.v2ex.com/gravatar/%@.jpg?s=%ld&&d=retro",
            registerUrl = severAddress + "/user/register",
            userProfileUrl = severAddress + "/user/profile/",
            userTypeAheadItemUrl = severAddress + "/user/typeAheadItem/",
            userCenterDataUrl = severAddress + "/user/userCenterData/";

    private static Context mContext;
    private static String avatarCacheDir;
    public static void init(Context context){
        mContext = context;
        NetWorkTool.init(mContext);
        avatarCacheDir = context.getCacheDir().getAbsolutePath() + File.separator + "avatar" + File.separator;
    }

    private static Object mExtra;
    private static boolean mInOrder = true;
    public static void setExtra(Object extra){
        NetData.mExtra = extra;
    }
    public static void setIfInOrder(boolean inOrder){
        NetData.mInOrder = inOrder;
    }
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
        Log.d(TAG, "getAvatar: 获取头像");
        File file;
        Object[] bytes = null;
        if ((file = new File(avatarCacheDir + email)).exists()){
            try {
                bytes = NetWorkTool.getBytes(new FileInputStream(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (bytes != null){
            sync(ViewHandler.AVATAR, new Result(BitmapFactory.decodeByteArray((byte[]) bytes[0], 0, (int)bytes[1]), mExtra), viewHandler);
            Log.d(TAG, "getAvatar: 本地有该头像缓存");
        }
        else{
            async(ViewHandler.AVATAR, avatarUrl.replace("%@", NetWorkTool.md(email, "md5")).replace("%ld", "200"), email, viewHandler);
            Log.d(TAG, "getAvatar: 本地没有该头像缓存");
        }
    }

    public static void submitCode(String codeContent, int languageId, int contestId, int problemId, ViewHandler viewHandler){
        String key[] = new String[]{"codeContent", "languageId", "contestId", "problemId"};
        Object o[] = new Object[]{codeContent, languageId, contestId == -1?"":contestId, problemId};
        async(ViewHandler.STATUS_SUBMIT, codeSubmitUrl, constructJson(key, o), viewHandler);
    }
    public static void getStatusInfo(int statusId, ViewHandler viewHandler){
        async(ViewHandler.STATUS_INFO, statusInfoUrl + statusId, null, viewHandler);
    }
    public static void getStatusList(int problemId, String userName, int contestId, int page, ViewHandler viewHandler){
        Log.d(TAG, "getStatusList: 获取记录");
        String key[] = new String[]{"problemId","userName","contestId", "currentPage", "orderAsc", "orderFields", "result"};
        Object[] o = new Object[]{problemId == -1?"":problemId, userName, contestId, page, false, "statusId", 0};
        async(ViewHandler.STATUS_LIST, statusListUrl, constructJson(key, o), viewHandler);
    }
    public static void getContestComment(int contestId, int page, ViewHandler viewHandler){
        String key[] = new String[]{"contestId", "currentPage", "orderAsc", "orderFields"};
        Object[] o = new Object[]{contestId, page, false, "id"};
        async(ViewHandler.CONTEST_COMMENT, contestCommentUrl, constructJson(key, o), viewHandler);
    }
    public static void getContestRank(int contestId, ViewHandler viewHandler){
        async(ViewHandler.CONTEST_RANK, contestRankListUrl + contestId, null, viewHandler);
    }
    static String constructJson(String key[], Object o[]){
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
    public static void getProblemList(final int page, String keyword, int startId, final ViewHandler viewHandler){
        String p = "";
        try {
            p = new JSONObject().put("currentPage", page).put("orderAsc", "true")
                    .put("orderFields", "id").put("keyword", keyword).put("startId", startId).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        async(ViewHandler.PROBLEM_LIST, problemListUrl, p, viewHandler);
    }
    public static void getContestList(int page, String keyword, ViewHandler viewHandler) {
        String p = "";
        try {
            p = new JSONObject().put("currentPage", page).put("orderAsc", "false")
                    .put("orderFields", "time").put("keyword", keyword).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        async(ViewHandler.CONTEST_LIST, contestListUrl, p, viewHandler);

    }
    public static void getArticleList(final int page, final ViewHandler viewHandler){
        String p = "";
        try {
            p = new JSONObject().put("currentPage", page).put("orderAsc", "true")
                    .put("orderFields", "order").put("type", 0).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        async(ViewHandler.ARTICLE_LIST, articleListUrl, p, viewHandler);
    }

    public static void getArticleDetail(final int id, final ViewHandler viewHandler){
        async(ViewHandler.ARTICLE_DETAIL, articleDetailUrl + id, null, viewHandler);
    }
    public static void getContestDetail(final int id, final ViewHandler viewHandler){
        async(ViewHandler.CONTEST_DETAIL, contestDetailUrl + id, null, viewHandler);
    }
    public static void getProblemDetail(final int id, final ViewHandler viewHandler){
        async(ViewHandler.PROBLEM_DETAIL, problemDetailUrl + id, null, viewHandler);
    }

    private static void sync(int which, Result result, ViewHandler viewHandler){
        final Object extra = NetData.mExtra;
        boolean inQueue = NetData.mInOrder;
        NetData.mExtra = null;
        NetData.mInOrder = true;
        final long time = System.currentTimeMillis();
        handleInMain(which, result, viewHandler, time, extra);
    }
    private static void async(final int which, final String url, final String params, final ViewHandler viewHandler) {
        final Object extra = NetData.mExtra;
        boolean inQueue = NetData.mInOrder;
        NetData.mExtra = null;
        NetData.mInOrder = true;
        final long time = System.currentTimeMillis();
        new ThreadTools.Task<Void, Result>() {
            @Override
            Result doInBackGround(Void aVoid) {
                return request(which, new String[]{url, params});
            }

            @Override
            void onPostExecute(Result result) {
                handleInMain(which, result, viewHandler, time, extra);
            }
        }.execute(inQueue);
/*        new AsyncTask<Void, Void, Result>(){

            @Override
            protected Result doInBackground(Void... voids) {
                return request(which, req);
            }

            @Override
            protected void onPostExecute(Result result) {
                handleInMain(which, result, viewHandler, time, extra);
            }
        }.executeOnExecutor(Executors.newFixedThreadPool(1));*/

    }

    static Result request(int which, String[] req){
        Log.d(TAG, "request: 开始http请求" + req[0]);
        switch (which){
            case ViewHandler.AVATAR:
                Object[] bytes = NetWorkTool.getBytes(NetWorkTool._get(req[0]));
                if (bytes != null){
                    File file;
                    try {
                        Log.d(TAG, "request: http请求完成" + avatarCacheDir + req[1]);;
                        if (!(file = new File(avatarCacheDir + req[1])).exists()){
                            File pf;
                            if(!(pf = file.getParentFile()).exists() || true){
                                Log.d(TAG, "request: " + pf);
                                pf.mkdirs();
                            }
                            file.createNewFile();
                        }
                        new FileOutputStream(file).write((byte[]) bytes[0], 0, (int) bytes[1]);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return new Result(BitmapFactory.decodeByteArray((byte[]) bytes[0], 0, (int)bytes[1]));

        }
        String result = NetWorkTool.getOrPost(req);
        Log.d(TAG, "request: http请求完成");
        return new Result(which, result);
    }
    static void handleInMain(int which, Result result, ViewHandler viewHandler, long time, Object extra){
        if (viewHandler != null){
            result.setExtra(extra);
            viewHandler.show(which, result, time);
        }
    }

}
