package cn.edu.uestc.acm.cdoj.net;
import android.os.Handler;
import android.os.Message;

import java.util.List;

import cn.edu.uestc.acm.cdoj.genaralData.ListReceived;
import cn.edu.uestc.acm.cdoj.net.article.Article;
import cn.edu.uestc.acm.cdoj.net.article.ArticleConnection;
import cn.edu.uestc.acm.cdoj.net.article.ArticleListItem;
import cn.edu.uestc.acm.cdoj.net.article.ObtainArticle;
import cn.edu.uestc.acm.cdoj.net.contest.Contest;
import cn.edu.uestc.acm.cdoj.net.contest.ContestConnection;
import cn.edu.uestc.acm.cdoj.net.contest.ContestListItem;
import cn.edu.uestc.acm.cdoj.net.contest.ContestProblem;
import cn.edu.uestc.acm.cdoj.net.contest.ObtainContest;
import cn.edu.uestc.acm.cdoj.net.problem.ObtainProblem;
import cn.edu.uestc.acm.cdoj.net.problem.Problem;
import cn.edu.uestc.acm.cdoj.net.problem.ProblemConnection;
import cn.edu.uestc.acm.cdoj.net.problem.ProblemListItem;
import cn.edu.uestc.acm.cdoj.utils.ThreadUtil;

/**
 * Created by 14779 on 2017-7-21.
 */

public class Connection implements ObtainArticle, ObtainProblem, ObtainContest {

    private static final String TAG = "Connection";
    public static Connection instance = new Connection();

    //通过handler将callback传递出去
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x01012013:
                    Object[] data = (Object[]) msg.obj;
                    ReceivedCallback callback = (ReceivedCallback) data[0];
                    Object result = data[1];
                    callback.onDataReceived(result);
                    break;
            }
        }
    };

    @Override
    public void getArticleContent(final int id, final ReceivedCallback<Article> callback) {
        ThreadUtil.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                Article result = ArticleConnection.getInstance().getContent(id);
                Message message = new Message();
                Object[] obj = new Object[2];
                obj[0] = callback;
                obj[1] = result;
                message.obj = obj;
                message.what = 0x01012013;
                handler.sendMessage(message);
            }
        });
    }

    @Override
    public void getArticleList(int page, ReceivedCallback<ListReceived<ArticleListItem>> callback) {
        searchArticle(page, "time", callback);
    }

    @Override
    public void searchArticle(int page, String orderFields, ReceivedCallback<ListReceived<ArticleListItem>> callback) {
        searchArticle(page, orderFields, 0, callback);
    }

    @Override
    public void searchArticle(int page, String orderFields, int type, ReceivedCallback<ListReceived<ArticleListItem>> callback) {
        searchArticle(page, orderFields, type, false, callback);
    }

    @Override
    public void searchArticle(final int page, final String orderFields, final int type, final boolean orderAsc, final ReceivedCallback<ListReceived<ArticleListItem>> callback) {
        ThreadUtil.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                ListReceived<ArticleListItem> result = ArticleConnection.getInstance().getSearch(page, type, orderFields, orderAsc);
//                List<ArticleListItem> result = new ArrayList<>();
                Message message = new Message();
                Object[] obj = new Object[2];
                obj[0] = callback;
                obj[1] = result;
                message.obj = obj;
                message.what = 0x01012013;
                handler.sendMessage(message);
            }
        });
    }


    @Override
    public void getProblemContent(final int id, final ReceivedCallback<Problem> callback) {
        ThreadUtil.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                Problem result = ProblemConnection.getInstance().getContent(id);
                Message msg = new Message();
                Object[] obj = new Object[2];
                obj[0] = callback;
                obj[1] = result;
                msg.what = 0x01012013;
                msg.obj = obj;
                handler.sendMessage(msg);
            }
        });
    }

    @Override
    public void getProblemList(int page, ReceivedCallback<ListReceived<ProblemListItem>> callback) {
        searchProblem(page, "id", callback);
    }

    @Override
    public void searchProblem(int page, ReceivedCallback<ListReceived<ProblemListItem>> callback) {
        searchProblem(page, "id", callback);
    }

    @Override
    public void searchProblem(int page, String orderFields, ReceivedCallback<ListReceived<ProblemListItem>> callback) {
        searchProblem(page, orderFields, true, callback);
    }

    @Override
    public void searchProblem(int page, String orderFields, boolean orderAsc, ReceivedCallback<ListReceived<ProblemListItem>> callback) {
        searchProblem(page, orderFields, orderAsc, "", callback);
    }

    @Override
    public void searchProblem(int page, String orderFields, boolean orderAsc, String keyword, ReceivedCallback<ListReceived<ProblemListItem>> callback) {
        searchProblem(page, orderFields, orderAsc, keyword, 0, callback);
    }

    @Override
    public void searchProblem(final int page, final String orderFields, final boolean orderAsc, final String keyword, final int startId, final ReceivedCallback<ListReceived<ProblemListItem>> callback) {
        ThreadUtil.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                ListReceived<ProblemListItem> result = ProblemConnection.getInstance().getSearch(page, orderFields, orderAsc, keyword, startId);
                Message msg = new Message();
                Object[] obj = new Object[2];
                obj[0] = callback;
                obj[1] = result;
                msg.obj = obj;
                msg.what = 0x01012013;
                handler.sendMessage(msg);
            }
        });
    }

    @Override
    public void getContestContent(final int id, final ReceivedCallback<Contest> callback) {
        ThreadUtil.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                Contest result = ContestConnection.getInstance().getContent(id);
                Message msg = new Message();
                Object[] obj = new Object[2];
                obj[0] = callback;
                obj[1] = result;
                msg.obj = obj;
                msg.what = 0x01012013;
                handler.sendMessage(msg);
            }
        });
    }

    @Override
    public void getContestProblemList(final int id, final ReceivedCallback<List<ContestProblem>> callback) {
        ThreadUtil.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                List<ContestProblem> result = ContestConnection.getInstance().getContestProblemList(id);
                Message msg = new Message();
                Object[] obj = new Object[2];
                obj[0] = callback;
                obj[1] = result;
                msg.obj = obj;
                msg.what = 0x01012013;
                handler.sendMessage(msg);
            }
        });
     }

    @Override
    public void getContestList(int page, ReceivedCallback<ListReceived<ContestListItem>> callback) {
        searchContest(page, callback);
    }

    @Override
    public void searchContest(int page, ReceivedCallback<ListReceived<ContestListItem>> callback) {
        searchContest(page, "time", callback);
    }

    @Override
    public void searchContest(int page, String orderFields, ReceivedCallback<ListReceived<ContestListItem>> callback) {
        searchContest(page, orderFields, true, callback);
    }

    @Override
    public void searchContest(int page, String orderFields, boolean orderAsc, ReceivedCallback<ListReceived<ContestListItem>> callback) {
        searchContest(page, orderFields, orderAsc, "", callback);
    }

    @Override
    public void searchContest(int page, String orderFields, boolean orderAsc, String keyword, ReceivedCallback<ListReceived<ContestListItem>> callback) {
        searchContest(page, orderFields, orderAsc, keyword, 1, callback);
    }

    @Override
    public void searchContest(final int page, final String orderFields, final boolean orderAsc, final String keyword, final int startId, final ReceivedCallback<ListReceived<ContestListItem>> callback) {
        ThreadUtil.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                ListReceived<ContestListItem> result = ContestConnection.getInstance().getSearch(page, orderFields, orderAsc, keyword, startId);
                Message msg = new Message();
                Object[] obj = new Object[2];
                obj[0] = callback;
                obj[1] = result;
                msg.obj = obj;
                msg.what = 0x01012013;
                handler.sendMessage(msg);
            }
        });
    }
}
