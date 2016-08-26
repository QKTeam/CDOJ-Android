package cn.edu.uestc.acm.cdoj_android;

import android.app.Fragment;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.edu.uestc.acm.cdoj_android.layout.detail.ArticleUI;
import cn.edu.uestc.acm.cdoj_android.layout.detail.ContestUI;
import cn.edu.uestc.acm.cdoj_android.layout.detail.ProblemUI;
import cn.edu.uestc.acm.cdoj_android.layout.list.ArticleListFragment;
import cn.edu.uestc.acm.cdoj_android.layout.list.ContestListFragment;
import cn.edu.uestc.acm.cdoj_android.layout.list.ProblemListFragment;
import cn.edu.uestc.acm.cdoj_android.net.NetData;
import cn.edu.uestc.acm.cdoj_android.net.ViewHandler;
import cn.edu.uestc.acm.cdoj_android.net.data.Article;
import cn.edu.uestc.acm.cdoj_android.net.data.ArticleInfo;
import cn.edu.uestc.acm.cdoj_android.net.data.Contest;
import cn.edu.uestc.acm.cdoj_android.net.data.ContestInfo;
import cn.edu.uestc.acm.cdoj_android.net.data.InfoList;
import cn.edu.uestc.acm.cdoj_android.net.data.Problem;
import cn.edu.uestc.acm.cdoj_android.net.data.ProblemInfo;

/**
 * Created by great on 2016/8/15.
 */
public class NetContent implements ViewHandler {
    private ArticleUI article;
    private ProblemUI problem;
    private ContestUI contest;
    private ArticleUI article_itemActivity;
    private ProblemUI problem_itemActivity;
    private ContestUI contest_itemActivity;
    private ArticleListFragment articleList;
    private ProblemListFragment problemList;
    private ContestListFragment contestList;


    @Override
    public void show(int which, Object data, long time) {
        switch (which) {
            case ViewHandler.ARTICLE_LIST:
                ArrayList<ArticleInfo> infoList_A = ((InfoList) data).getInfoList();
                for (ArticleInfo tem : infoList_A) {
                    Map<String, Object> listItem = new HashMap<>();
                    listItem.put("title", tem.title);
                    listItem.put("content", tem.content);
                    listItem.put("date", tem.timeString);
                    listItem.put("author", tem.ownerName);
                    listItem.put("id", "" + tem.articleId);
                    articleList.addListItem(listItem);
                }
                articleList.notifyDataSetChanged();
                break;

            case ViewHandler.ARTICLE_DETAIL:
                if (article != null) {
                    article.addJSData(((Article) data).getContentString());
                }
                if (article_itemActivity != null) {
                    article_itemActivity.addJSData(((Article) data).getContentString());
                    article_itemActivity = null;
                }
                break;

            case ViewHandler.PROBLEM_LIST:
                ArrayList<ProblemInfo> infoList_P = ((InfoList) data).getInfoList();
                for (ProblemInfo tem : infoList_P) {
                    String number = "" + tem.solved + "/" + tem.tried;
                    Map<String, Object> listItem = new HashMap<>();
                    listItem.put("title", tem.title);
                    listItem.put("source", tem.source);
                    listItem.put("id", "" + tem.problemId);
                    listItem.put("number", number);
                    problemList.addListItem(listItem);
                }
                problemList.notifyDataSetChanged();
                break;

            case ViewHandler.PROBLEM_DETAIL:
                if (problem != null) {
                    problem.addJSData(((Problem) data).getContentString());
                }
                if (problem_itemActivity != null) {
                    problem_itemActivity.addJSData(((Problem) data).getContentString());
                    problem_itemActivity = null;
                }
                break;

            case ViewHandler.CONTEST_LIST:
                ArrayList<ContestInfo> infoList_C = ((InfoList) data).getInfoList();
                for (ContestInfo tem : infoList_C) {
                    Map<String, Object> listItem = new HashMap<>();
                    listItem.put("title", tem.title);
                    listItem.put("date", tem.timeString);
                    listItem.put("timeLimit", tem.lengthString);
                    listItem.put("id", "" + tem.contestId);
                    listItem.put("status", tem.status);
                    listItem.put("permission", tem.typeName);
                    contestList.addListItem(listItem);
                }
                contestList.notifyDataSetChanged();
                break;

            case ViewHandler.CONTEST_DETAIL:
                if (contest != null) {
                    contest.addOverView(((Contest) data).getContentString());
                    contest.addProblems(((Contest) data).getProblemList());
                }
                if (contest_itemActivity != null) {
                    contest_itemActivity.addOverView(((Contest) data).getContentString());
                    contest_itemActivity.addProblems(((Contest) data).getProblemList());
                    contest_itemActivity = null;
                }
                break;
        }
    }

    @IntDef({ViewHandler.ARTICLE_DETAIL, ViewHandler.PROBLEM_DETAIL, ViewHandler.CONTEST_DETAIL,
            ViewHandler.ARTICLE_LIST, ViewHandler.PROBLEM_LIST, ViewHandler.CONTEST_LIST})
    @Retention(RetentionPolicy.SOURCE)
    @interface content {}

    public void getContent(@content int which, int idOrPage) {
        switch (which) {
            case ViewHandler.ARTICLE_DETAIL:
                NetData.getArticleDetail(idOrPage, this);
                break;
            case ViewHandler.PROBLEM_DETAIL:
                NetData.getProblemDetail(idOrPage, this);
                break;
            case ViewHandler.CONTEST_DETAIL:
                NetData.getContestDetail(idOrPage, this);
                break;
            case ViewHandler.ARTICLE_LIST:
                NetData.getArticleList(idOrPage, this);
                break;
            case ViewHandler.PROBLEM_LIST:
                NetData.getProblemList(idOrPage, "", this);
                break;
            case ViewHandler.CONTEST_LIST:
                NetData.getContestList(idOrPage, "", this);
                break;
        }
    }

    public void getItemContent(Fragment detail, int idOrPage) {
        if (detail == null) {return;}
        if (detail instanceof ArticleUI) {
            this.article_itemActivity = (ArticleUI) detail;
            NetData.getArticleDetail(idOrPage,this);
            return;
        }
        if (detail instanceof ProblemUI) {
            this.problem_itemActivity = (ProblemUI) detail;
            NetData.getProblemDetail(idOrPage, this);
            return;
        }
        if (detail instanceof ContestUI) {
            this.contest_itemActivity = (ContestUI) detail;
            NetData.getContestDetail(idOrPage, this);
        }
    }

    public void addList(ArticleListFragment articleList) {
        this.articleList = articleList;
    }

    public void addList(ProblemListFragment problemList) {
        this.problemList = problemList;
    }

    public void addList(ContestListFragment contestList) {
        this.contestList = contestList;
    }

    public void addDetail(ArticleUI article) {
        this.article = article;
    }

    public void addDetail(ProblemUI problem) {
        this.problem = problem;
    }

    public void addDetail(ContestUI contest) {
        this.contest = contest;
    }
}
