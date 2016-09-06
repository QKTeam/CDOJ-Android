package cn.edu.uestc.acm.cdoj_android;

import android.app.Fragment;
import android.support.annotation.IntDef;
import android.util.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.edu.uestc.acm.cdoj_android.layout.detail.ArticleFragment;
import cn.edu.uestc.acm.cdoj_android.layout.detail.ContestFragment;
import cn.edu.uestc.acm.cdoj_android.layout.detail.ProblemFragment;
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
import cn.edu.uestc.acm.cdoj_android.net.data.PageInfo;
import cn.edu.uestc.acm.cdoj_android.net.data.Problem;
import cn.edu.uestc.acm.cdoj_android.net.data.ProblemInfo;
import cn.edu.uestc.acm.cdoj_android.net.data.Rank;
import cn.edu.uestc.acm.cdoj_android.net.data.Status;

/**
 * Created by great on 2016/8/15.
 */
public class NetContent implements ViewHandler {
    private ArticleFragment article;
    private ProblemFragment problem;
    private ContestFragment contest;
    private ArticleFragment article_itemActivity;
    private ProblemFragment problem_itemActivity;
    private ContestFragment contest_itemActivity;
    private ArticleListFragment articleList;
    private ProblemListFragment problemList;
    private ContestListFragment contestList;


    @Override
    public void show(int which, Object data, long time) {
        switch (which) {
            case ViewHandler.ARTICLE_LIST:
                if (((InfoList) data).result) {
                    articleList.setPageInfo(((InfoList) data).pageInfo);
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
                }else {
                    articleList.stopPullUpLoad();
                }
                articleList.notifyDataSetChanged();
                break;

            case ViewHandler.PROBLEM_LIST:
                if (((InfoList) data).result) {
                    problemList.setPageInfo(((InfoList) data).pageInfo);
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
                } else {
                    problemList.stopPullUpLoad();
                }
                problemList.notifyDataSetChanged();
                break;

            case ViewHandler.CONTEST_LIST:
                if (((InfoList) data).result) {
                    contestList.setPageInfo(((InfoList) data).pageInfo);
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
                } else {
                    contestList.stopPullUpLoad();
                }
                contestList.notifyDataSetChanged();
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


            case ViewHandler.PROBLEM_DETAIL:
                if (problem != null) {
                    problem.addJSData(((Problem) data).getContentString());
                }
                if (problem_itemActivity != null) {
                    problem_itemActivity.addJSData(((Problem) data).getContentString());
                    problem_itemActivity = null;
                }
                break;

            case ViewHandler.CONTEST_DETAIL:
                Contest contest_data = (Contest) data;
                if (contest != null) {
                    contest.addOverView(contest_data.getContentString());
                    contest.addProblems(contest_data.getProblemList());
                }
                if (contest_itemActivity != null) {
                    contest_itemActivity.addOverView(((Contest) data).getContentString());
                    contest_itemActivity.addProblems(((Contest) data).getProblemList());
//                    contest_itemActivity = null;
                }
                break;

            case ViewHandler.CONTEST_COMMENT:
                Log.d("获取Content列表", ": "+((InfoList) data).pageInfo.currentPage+"  "+((InfoList) data).result);
                if (contest != null) {
                    if (((InfoList) data).result) {
                        contest.getClarification().setPageInfo(((InfoList) data).pageInfo);
                        ArrayList<ArticleInfo> infoList_clarification = ((InfoList) data).getInfoList();
                        for (ArticleInfo tem : infoList_clarification) {
                            Map<String, Object> listItem = new HashMap<>();
//                    listItem.put("header", tem.);
                            listItem.put("content", tem.content);
                            listItem.put("date", tem.timeString);
                            listItem.put("author", tem.ownerName);
                            contest.getClarification().addListItem(listItem);
                        }
                    } else {
                        contest.getClarification().stopPullUpLoad();
                    }
                    contest.getClarification().notifyDataSetChanged();
                }
                if (contest_itemActivity != null) {
                    if (((InfoList) data).result){
                        contest_itemActivity.getClarification().setPageInfo(((InfoList) data).pageInfo);
                        ArrayList<ArticleInfo> infoList_clarification = ((InfoList) data).getInfoList();
                        for (ArticleInfo tem : infoList_clarification) {
                            Map<String, Object> listItem = new HashMap<>();
//                    listItem.put("header", tem.);
                            listItem.put("content", tem.content);
                            listItem.put("date", tem.timeString);
                            listItem.put("author", tem.ownerName);
                            contest_itemActivity.getClarification().addListItem(listItem);
                        }
                    }else {
                        contest_itemActivity.getClarification().stopPullUpLoad();
                    }
                    contest_itemActivity.getClarification().notifyDataSetChanged();
                }
                break;

            case ViewHandler.STATUS_LIST:
                Log.d("获取Status列表", ": "+((InfoList) data).pageInfo.currentPage+"  "+((InfoList) data).result);
                if (contest != null) {
                    if (((InfoList) data).result) {
                        contest.getStatus().setPageInfo(((InfoList) data).pageInfo);
                        ArrayList<Status> infoList_status = ((InfoList) data).getInfoList();
                        for (Status tem : infoList_status) {
                            Map<String, Object> listItem = new HashMap<>();
                            listItem.put("prob", tem.problemId);
                            listItem.put("result", tem.returnType);
                            listItem.put("length", tem.length);
                            listItem.put("submitTime", tem.timeString);
                            listItem.put("language", tem.language);
                            listItem.put("time", tem.timeCost);
                            listItem.put("memory", tem.memoryCost);
                            contest.getStatus().addListItem(listItem);
                        }
                    } else {
                        contest.getStatus().stopPullUpLoad();
                    }
                    contest.getStatus().notifyDataSetChanged();
                }
                if (contest_itemActivity != null) {
                    if (((InfoList) data).result) {
                        contest_itemActivity.getStatus().setPageInfo(((InfoList) data).pageInfo);
                        ArrayList<Status> infoList_status = ((InfoList) data).getInfoList();
                        for (Status tem : infoList_status) {
                            Map<String, Object> listItem = new HashMap<>();
                            listItem.put("prob", tem.problemId);
                            listItem.put("result", tem.returnType);
                            listItem.put("length", tem.length);
                            listItem.put("submitTime", tem.timeString);
                            listItem.put("language", tem.language);
                            listItem.put("time", tem.timeCost);
                            listItem.put("memory", tem.memoryCost);
                            contest_itemActivity.getStatus().addListItem(listItem);
                        }
                    } else {
                        contest_itemActivity.getStatus().stopPullUpLoad();
                    }
                    contest_itemActivity.getStatus().notifyDataSetChanged();
                }
                break;

            case ViewHandler.CONTEST_RANK:
                Rank rankInfo = (Rank) data;
                if (contest != null) {
                    if (rankInfo.result) {
                        ArrayList<Rank.Performance> infoList_rank = rankInfo.getPerformanceList();
                        for (Rank.Performance tem : infoList_rank) {
                            Map<String, Object> listItem = new HashMap<>();
                            listItem.put("rank", tem.rank);
                            listItem.put("name", tem.nickName+"("+tem.name+")");
                            listItem.put("solved", tem.solved);
                            contest.getRank().addListItem(listItem);
                        }
                    }
                    contest.getRank().notifyDataSetChanged();
                }
                if (contest_itemActivity != null) {
                    if (rankInfo.result) {
                        ArrayList<Rank.Performance> infoList_rank = rankInfo.getPerformanceList();
                        for (Rank.Performance tem : infoList_rank) {
                            Map<String, Object> listItem = new HashMap<>();
                            listItem.put("rank", tem.rank);
                            listItem.put("name", tem.nickName+"("+tem.name+")");
                            listItem.put("solved", tem.solved);
                            contest_itemActivity.getRank().addListItem(listItem);
                        }
                    }
                    contest_itemActivity.getRank().notifyDataSetChanged();
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
                NetData.getContestComment(idOrPage, 1, this);
                NetData.getStatusList(idOrPage, 1, this);
                NetData.getContestRank(idOrPage, this);
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

    public void getContent(Fragment detail, int id) {
        if (detail == null) {return;}
        if (detail instanceof ArticleFragment) {
            this.article_itemActivity = (ArticleFragment) detail;
            NetData.getArticleDetail(id,this);
            return;
        }
        if (detail instanceof ProblemFragment) {
            this.problem_itemActivity = (ProblemFragment) detail;
            NetData.getProblemDetail(id, this);
            return;
        }
        if (detail instanceof ContestFragment) {
            this.contest_itemActivity = (ContestFragment) detail;
            NetData.getContestDetail(id, this);
        }
    }


    @IntDef({ViewHandler.CONTEST_COMMENT, ViewHandler.STATUS_LIST, ViewHandler.CONTEST_RANK})
    @Retention(RetentionPolicy.SOURCE)
    @interface contestPart {}

    public void getContestPart( @contestPart int part,int id, int page) {
        if (contest != null || contest_itemActivity != null) {
            switch (part) {
                case ViewHandler.CONTEST_COMMENT:
                    NetData.getContestComment(id, page, this);
                    Log.d("向NETdata获取content列表", "getContestPart: "+id+"  page"+page);
                    break;
                case ViewHandler.STATUS_LIST:
                    NetData.getStatusList(id, page, this);
                    break;
                case ViewHandler.CONTEST_RANK:
                    NetData.getContestRank(id, this);
                    break;
            }
        }
    }


    public void addListFragment(ArticleListFragment articleList) {
        this.articleList = articleList;
    }

    public void addListFragment(ProblemListFragment problemList) {
        this.problemList = problemList;
    }

    public void addListFragment(ContestListFragment contestList) {
        this.contestList = contestList;
    }

    public void addDetailFragment(ArticleFragment article) {
        this.article = article;
    }

    public void addDetailFragment(ProblemFragment problem) {
        this.problem = problem;
    }

    public void addDetailFragment(ContestFragment contest) {
        this.contest = contest;
    }

    public void removeContestDetailFragmentInActivity() {
        contest_itemActivity = null;
    }
}
