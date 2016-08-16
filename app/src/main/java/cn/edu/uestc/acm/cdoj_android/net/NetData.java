package cn.edu.uestc.acm.cdoj_android.net;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.uestc.acm.cdoj_android.net.data.Article;
import cn.edu.uestc.acm.cdoj_android.net.data.ArticleInfo;
import cn.edu.uestc.acm.cdoj_android.net.data.Contest;
import cn.edu.uestc.acm.cdoj_android.net.data.ContestInfo;
import cn.edu.uestc.acm.cdoj_android.net.data.InfoList;
import cn.edu.uestc.acm.cdoj_android.net.data.Problem;
import cn.edu.uestc.acm.cdoj_android.net.data.ProblemInfo;

/**
 * Created by qwe on 16-8-14.
 */
public class NetData {
    public final static String severAddress = "http://acm.uestc.edu.cn",
            problemListUrl = severAddress + "/problem/search",
            contestListUrl = severAddress + "/contest/search",
            articleListUrl = severAddress + "/article/search",
            articleDetailUrl= severAddress + "/article/data/",
            problemDetailUrl = severAddress + "/problem/data/",
            contestDetailUrl = severAddress + "/contest/data/";

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
    public static void getContestList(int page, String keyword, ViewHandler viewHandler){
        String p = "";
        try {
            p = new JSONObject().put("currentPage", page).put("orderAsc", "true")
                    .put("orderFields", "id").put("keyword", keyword).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        async(ViewHandler.CONTEST_LIST, new String[]{contestListUrl, p}, viewHandler);
    }
    public static void getArticleList(final int page, final ViewHandler viewHandler){
        String p = "";
        try {
            p = new JSONObject().put("currentPage", page).put("orderAsc", "false").put("orderFields", "id").toString();
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
        new AsyncTask<Void, Void, Object>(){

            @Override
            protected Object doInBackground(Void... voids) {
                return request(which, req);
            }

            @Override
            protected void onPostExecute(Object o) {
                handleInMain(which, o, viewHandler);
            }
        }.execute();

    }

    static Object request(int which, String[] req){
        switch (which){
            case ViewHandler.PROBLEM_LIST:
                return new InfoList<ProblemInfo>(NetWorkTool.post(req[0], req[1]), ProblemInfo.class);
            case ViewHandler.ARTICLE_LIST:
                return new InfoList<ArticleInfo>(NetWorkTool.post(req[0], req[1]), ArticleInfo.class);
            case ViewHandler.CONTEST_LIST:
                return new InfoList<ContestInfo>(NetWorkTool.post(req[0], req[1]), ContestInfo.class);
            case ViewHandler.PROBLEM_DETAIL:
                return new Problem(NetWorkTool.get(req[0]));
            case ViewHandler.CONTEST_DETAIL:
                return new Contest(NetWorkTool.get(req[0]));
            case ViewHandler.ARTICLE_DETAIL:
                return new Article(NetWorkTool.get(req[0]));
            default: return null;
        }
    }
    static void handleInMain(int which, Object data, ViewHandler viewHandler){
        viewHandler.show(which,data);
    }
}
