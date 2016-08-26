package cn.edu.uestc.acm.cdoj_android.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.uestc.acm.cdoj_android.net.data.Article;
import cn.edu.uestc.acm.cdoj_android.net.data.ArticleInfo;
import cn.edu.uestc.acm.cdoj_android.net.data.Contest;
import cn.edu.uestc.acm.cdoj_android.net.data.ContestInfo;
import cn.edu.uestc.acm.cdoj_android.net.data.InfoList;
import cn.edu.uestc.acm.cdoj_android.net.data.Problem;
import cn.edu.uestc.acm.cdoj_android.net.data.ProblemInfo;
import cn.edu.uestc.acm.cdoj_android.net.data.Rank;
import cn.edu.uestc.acm.cdoj_android.net.data.Status;

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
            stutasInfoUrl = severAddress + "/stutas/info/",
            codeSubmitUrl = severAddress + "/status/submit",
            avatarUrl = "http://cdn.v2ex.com/gravatar/%@.jpg?s=%ld&&d=retro";
    public static void getAvatar(String email, Object addition, ViewHandler viewHandler){
        asyncWithAdd(ViewHandler.AVATAR, new String[]{avatarUrl.replace("%@", NetWorkTool.md(email, "md5")).replace("%ld", "200")}, viewHandler, addition);
    }

    public static void submitCode(String codeContent, int languageId, int contestId, int problemId, ViewHandler viewHandler){
        String key[] = new String[]{"codeContent", "languageId", "contestId", "problemId"};
        Object o[] = new Object[]{codeContent, languageId, contestId == -1?"":contestId, problemId};
        async(ViewHandler.STUTAS_SUBMIT, new String[]{codeSubmitUrl, constructJson(key, o)}, viewHandler);
    }
    public static void getStutasInfo(int statusId, ViewHandler viewHandler){
        async(ViewHandler.STUTAS_INFO, new String[]{stutasInfoUrl + statusId}, viewHandler);
    }
    public static void getStutasList(int contestId, int page, ViewHandler viewHandler){
        String key[] = new String[]{"contestId", "currentPage", "orderAsc", "orderFields", "result"};
        Object[] o = new Object[]{contestId, page, false, "statusId", 0};
        async(ViewHandler.STATUS_LIST, new String[]{statusListUrl, constructJson(key, o)}, viewHandler);
    }
    public static void getContestComment(int contestId, int page, ViewHandler viewHandler){
        String key[] = new String[]{"contestId", "currentPage", "orderAsc", "orderFields"};
        Object[] o = new Object[]{contestId, page, false, "id"};
        async(ViewHandler.CONTEST_COMMENT, new String[]{contestCommentUrl, constructJson(key, o)}, viewHandler);
    }
    public static void getContestRank(int contestId, ViewHandler viewHandler){
        async(ViewHandler.CONTEST_RANK, new String[]{contestRankListUrl + contestId}, viewHandler);
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
        async(ViewHandler.LOGCONTEST, new String[]{loginContestUrl, p}, viewHandler);
    }
    public static void login(String userName, String sha1password, ViewHandler viewHandler){
        String p = "";
        try {
            p = new JSONObject().put("userName", userName).put("password", sha1password).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        async(ViewHandler.LOGIN, new String[]{loginUrl, p}, viewHandler);
    }
    public static void logout(ViewHandler viewHandler){
        async(ViewHandler.LOGOUT, new String[]{logoutUrl}, viewHandler);
    }
    public static void getProblemList(final int page, String keyword, final ViewHandler viewHandler){
        String p = "";
        try {
            p = new JSONObject().put("currentPage", page).put("orderAsc", "true")
                    .put("orderFields", "id").put("keyword", keyword).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        async(ViewHandler.PROBLEM_LIST, new String[]{problemListUrl, p}, viewHandler);
    }
    public static void getContestList(int page, String keyword, ViewHandler viewHandler) {
        String p = "";
        try {
            p = new JSONObject().put("currentPage", page).put("orderAsc", "false")
                    .put("orderFields", "time").put("keyword", keyword).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        async(ViewHandler.CONTEST_LIST, new String[]{contestListUrl, p}, viewHandler);

    }
    public static void getArticleList(final int page, final ViewHandler viewHandler){
        String p = "";
        try {
            p = new JSONObject().put("currentPage", page).put("orderAsc", "true")
                    .put("orderFields", "order").put("type", 0).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        async(ViewHandler.ARTICLE_LIST, new String[]{articleListUrl, p}, viewHandler);
    }

    public static void getArticleDetail(final int id, final ViewHandler viewHandler){
        async(ViewHandler.ARTICLE_DETAIL, new String[]{articleDetailUrl + id}, viewHandler);
    }
    public static void getContestDetail(final int id, final ViewHandler viewHandler){
        async(ViewHandler.CONTEST_DETAIL, new String[]{contestDetailUrl + id}, viewHandler);
    }
    public static void getProblemDetail(final int id, final ViewHandler viewHandler){
        async(ViewHandler.PROBLEM_DETAIL, new String[]{problemDetailUrl + id}, viewHandler);
    }


    static void async(final int which, final String[] req, final ViewHandler viewHandler){
        asyncWithAdd(which, req,  viewHandler, null);
    }
    static void asyncWithAdd(final int which, final String[] req, final ViewHandler viewHandler, final Object add) {
        final long time = System.currentTimeMillis();
        new AsyncTask<Void, Void, Object>(){

            @Override
            protected Object doInBackground(Void... voids) {
                return request(which, req);
            }

            @Override
            protected void onPostExecute(Object o) {
                handleInMain(which, o, viewHandler, time, add);
            }
        }.execute();

    }

    static Object request(int which, String[] req){
        switch (which){
            case ViewHandler.AVATAR:
                return BitmapFactory.decodeStream(NetWorkTool._get(req[1]));
        }
        String result = NetWorkTool.getOrPost(req);
        switch (which){
            case ViewHandler.PROBLEM_LIST:
                return new InfoList<ProblemInfo>(result, ProblemInfo.class);
            case ViewHandler.ARTICLE_LIST:
                return new InfoList<ArticleInfo>(result, ArticleInfo.class);
            case ViewHandler.CONTEST_LIST:
                return new InfoList<ContestInfo>(result, ContestInfo.class);
            case ViewHandler.PROBLEM_DETAIL:
                return new Problem(result);
            case ViewHandler.CONTEST_DETAIL:
                return new Contest(result);
            case ViewHandler.ARTICLE_DETAIL:
                return new Article(result);
            case ViewHandler.LOGIN:
//                String s;
//                Log.d(TAG, "request: name:" + req[0] + "||pwd:" + req[1] + "|||||" + (s = result));
                return checkResult(result);
            case ViewHandler.LOGOUT:
                return checkResult(result);
            case ViewHandler.LOGCONTEST:
//                Log.d(TAG, "request: name:" + req[0] + "||pwd:" + req[1] + "|||||" + (s = result));;
                return checkResult(result);
            case ViewHandler.CONTEST_COMMENT:
                return new InfoList<ArticleInfo>(result, ArticleInfo.class);
            case ViewHandler.CONTEST_RANK:
                return new Rank(result);
            case ViewHandler.STATUS_LIST:
                return new InfoList<Status>(result, Status.class);
            case ViewHandler.STUTAS_INFO:
                return Status.getCode(result);
            case ViewHandler.STUTAS_SUBMIT:
                return checkResult(result);
            default: return null;
        }
    }
    static void handleInMain(int which, Object data, ViewHandler viewHandler, long time, Object add){
        if (viewHandler != null){
            if (add != null){
                viewHandler.show(which, new Object[]{add, data}, time);
            }else {
                viewHandler.show(which, data, time);
            }
        }
    }
    static boolean checkResult(String json){
        if (json == null) {
            return false;
        }
        try {
            return new JSONObject(json).getString("result").equals("success");
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }
}
