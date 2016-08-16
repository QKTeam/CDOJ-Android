package cn.edu.uestc.acm.cdoj_android.net;

import android.app.Activity;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.edu.uestc.acm.cdoj_android.net.data.ArticleInfoList;
import cn.edu.uestc.acm.cdoj_android.net.data.ContestInfoList;
import cn.edu.uestc.acm.cdoj_android.net.data.ProblemInfoList;

/**
 * Created by lenovo on 2016/8/7.
 */

public class NetData_1 {
    Activity activity;
    final static String severAddress = "http://acm.uestc.edu.cn",
            problemListUrl = severAddress + "/problem/search",
            contestListUrl = severAddress + "/contest/search",
            articleListUrl = severAddress + "/article/search",
            articleDetailUrl= severAddress + "/article/data/",
            problemDetailUrl = severAddress + "/problem/data/",
            contestDetailUrl = severAddress + "/contest/data/";
    public NetData_1(Activity activity){
        this.activity = activity;
    }
    public void getContestList0(final int page, final ViewHandler viewHandler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String p = "";
                try {
                    p = new JSONObject().put("currentPage", page).put("orderAsc", "true").put("orderFields", "id").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String result = NetWorkTool.post(contestListUrl, p);// "{'currentPage':" + page + ",'orderAsc':'true'" + "'orderFields':'id'}");
                final ContestInfoList contestInfoList = new ContestInfoList(result);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        viewHandler.show(ViewHandler.CONTEST_LIST, contestInfoList);
                    }
                });

            }
        }).start();
    }
    public void getProblemList0(final int page, final ViewHandler viewHandler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String p = "";
                try {
                    p = new JSONObject().put("currentPage", page).put("orderAsc", "true").put("orderFields", "id").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String result = NetWorkTool.post(problemListUrl, p);// "{'currentPage':" + page + ",'orderAsc':'true'" + "'orderFields':'id'}");
                final ProblemInfoList problemInfoList = new ProblemInfoList(result);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        viewHandler.show(ViewHandler.PROBLEM_LIST, problemInfoList);
                    }
                });

            }
        }).start();
    }
    public void getArticleList0(final int page, final ViewHandler viewHandler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String p = "";
                try {
                    p = new JSONObject().put("currentPage", page).put("orderAsc", "false").put("orderFields", "id").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String result = NetWorkTool.post(articleListUrl, p);// "{'currentPage':" + page + ",'orderAsc':'true'" + "'orderFields':'id'}");
                final ArticleInfoList articleInfoList = new ArticleInfoList(result);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        viewHandler.show(ViewHandler.ARTICLE_LIST, articleInfoList);
                    }
                });

            }
        }).start();
    }
    public void getArticleDetail0(final int id, final ViewHandler viewHandler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = NetWorkTool.get(articleDetailUrl + id);// "{'currentPage':" + page + ",'orderAsc':'true'" + "'orderFields':'id'}");
                String r = null;
                try {
                    r = new JSONObject(result).getJSONObject("article").toString();
                } catch (JSONException e) {
                    r = null;
                    e.printStackTrace();
                }
                final String article = r;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        viewHandler.show(ViewHandler.ARTICLE_DETAIL, article);
                    }
                });

            }
        }).start();
    }
    public void getContestDetail0(final int id, final ViewHandler viewHandler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = NetWorkTool.get(contestDetailUrl + id);// "{'currentPage':" + page + ",'orderAsc':'true'" + "'orderFields':'id'}");
                String r = null;
                final ArrayList<String> list = new ArrayList<>(20);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    r = jsonObject.getJSONObject("contest").toString();
                    JSONArray plist = jsonObject.getJSONArray("problemList");
                    for (int i = 0; i < plist.length(); i++) {
                        list.add(plist.getJSONObject(i).toString());
                    }
                } catch (JSONException e) {
                    r = null;
                    e.printStackTrace();
                }
                final String contest = r;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        viewHandler.show(ViewHandler.CONTEST_DETAIL, contest);
                    }
                });

            }
        }).start();
    }
    public void getProblemDetail0(final int id, final ViewHandler viewHandler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = NetWorkTool.get(problemDetailUrl + id);// "{'currentPage':" + page + ",'orderAsc':'true'" + "'orderFields':'id'}");
                String r = null;
                try {
                    r = new JSONObject(result).getJSONObject("problem").toString();
                } catch (JSONException e) {
                    r = null;
                    e.printStackTrace();
                }
                final String problem = r;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        viewHandler.show(ViewHandler.PROBLEM_DETAIL, problem);
                    }
                });

            }
        }).start();
    }
    //
//
//
//
    public static void getProblemList(final int page, final ViewHandler viewHandler){
        new AsyncTask<Void, Void, Object>(){

            @Override
            protected Object doInBackground(Void... voids) {
                String p = "";
                try {
                    p = new JSONObject().put("currentPage", page).put("orderAsc", "true").put("orderFields", "id").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String result = NetWorkTool.post(problemListUrl, p);// "{'currentPage':" + page + ",'orderAsc':'true'" + "'orderFields':'id'}");
                return new ProblemInfoList(result);
            }
            protected void onPostExecute(Object object) {
                viewHandler.show(ViewHandler.PROBLEM_LIST, object);
            }
        }.execute();
    }
    public static void getContestList(final int page, final ViewHandler viewHandler){
        new AsyncTask<Void, Void, Object>(){

            @Override
            protected Object doInBackground(Void... voids) {
                String p = "";
                try {
                    p = new JSONObject().put("currentPage", page).put("orderAsc", "true").put("orderFields", "id").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String result = NetWorkTool.post(contestListUrl, p);// "{'currentPage':" + page + ",'orderAsc':'true'" + "'orderFields':'id'}");
                return new ContestInfoList(result);
            }
            protected void onPostExecute(Object object) {
                viewHandler.show(ViewHandler.CONTEST_LIST, object);
            }
        }.execute();
    }
    public static void getArticleList(final int page, final ViewHandler viewHandler){
        new AsyncTask<Void, Void, Object>(){

            @Override
            protected Object doInBackground(Void... voids) {
                String p = "";
                try {
                    p = new JSONObject().put("currentPage", page).put("orderAsc", "false").put("orderFields", "id").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String result = NetWorkTool.post(articleListUrl, p);// "{'currentPage':" + page + ",'orderAsc':'true'" + "'orderFields':'id'}");
                return new ArticleInfoList(result);
            }
            protected void onPostExecute(Object object) {
                viewHandler.show(ViewHandler.ARTICLE_LIST, object);
            }
        }.execute();
    }

    public static void getArticleDetail(final int id, final ViewHandler viewHandler){
        new AsyncTask<Void, Void, Object>(){

            @Override
            protected Object doInBackground(Void... voids) {
                String result = NetWorkTool.get(articleDetailUrl + id);// "{'currentPage':" + page + ",'orderAsc':'true'" + "'orderFields':'id'}");
                String r = null;
                try {
                    r = new JSONObject(result).getJSONObject("article").toString();
                } catch (JSONException e) {
                    r = null;
                    e.printStackTrace();
                }
                return new ArticleInfoList(r);
            }
            protected void onPostExecute(Object object) {
                viewHandler.show(ViewHandler.ARTICLE_DETAIL, object);
            }
        }.execute();
    }
    public void getContestDetail(final int id, final ViewHandler viewHandler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = NetWorkTool.get(contestDetailUrl + id);// "{'currentPage':" + page + ",'orderAsc':'true'" + "'orderFields':'id'}");
                String r = null;
                final ArrayList<String> list = new ArrayList<>(20);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    r = jsonObject.getJSONObject("contest").toString();
                    JSONArray plist = jsonObject.getJSONArray("problemList");
                    for (int i = 0; i < plist.length(); i++) {
                        list.add(plist.getJSONObject(i).toString());
                    }
                } catch (JSONException e) {
                    r = null;
                    e.printStackTrace();
                }
                final String contest = r;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        viewHandler.show(ViewHandler.CONTEST_DETAIL, contest);
                    }
                });

            }
        }).start();
    }
    public void getProblemDetail(final int id, final ViewHandler viewHandler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = NetWorkTool.get(problemDetailUrl + id);// "{'currentPage':" + page + ",'orderAsc':'true'" + "'orderFields':'id'}");
                String r = null;
                try {
                    r = new JSONObject(result).getJSONObject("problem").toString();
                } catch (JSONException e) {
                    r = null;
                    e.printStackTrace();
                }
                final String problem = r;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        viewHandler.show(ViewHandler.PROBLEM_DETAIL, problem);
                    }
                });

            }
        }).start();
    }
}
