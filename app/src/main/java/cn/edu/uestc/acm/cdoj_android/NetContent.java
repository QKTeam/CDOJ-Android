package cn.edu.uestc.acm.cdoj_android;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.edu.uestc.acm.cdoj_android.layout.ListFragmentWithGestureLoad;
import cn.edu.uestc.acm.cdoj_android.layout.details.DetailsWebViewFragment;
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
    DetailsWebViewFragment[] detailsFragments  = new DetailsWebViewFragment[3];
    ListFragmentWithGestureLoad[] listFragments = new ListFragmentWithGestureLoad[3];
    DetailsWebViewFragment[] detailsFragments_itemActivity = new DetailsWebViewFragment[3];

    @Override
    public void show(int which, Object data, long time) {
        switch (which) {
            case ViewHandler.ARTICLE_LIST:
                ArrayList<ArticleInfo> infoList_A = ((InfoList) data).getInfoList();
                for (ArticleInfo tem : infoList_A) {
                    Map<String, String> listItem = new HashMap<>();
                    listItem.put("title", tem.title);
                    listItem.put("content", tem.content);
                    listItem.put("releaseTime", tem.timeString);
                    listItem.put("author", tem.ownerName);
                    listItem.put("id", "" + tem.articleId);
                    listFragments[0].addListItem(listItem);
                }
                listFragments[0].notifyDataSetChanged();
                break;

            case ViewHandler.ARTICLE_DETAIL:
                if (detailsFragments[0] != null) {
                    detailsFragments[0].addJSData(((Article) data).getContentString());
                }
                if (detailsFragments_itemActivity[0] != null) {
                    detailsFragments_itemActivity[0].addJSData(((Article) data).getContentString());
                }
                break;

            case ViewHandler.PROBLEM_LIST:
                ArrayList<ProblemInfo> infoList_P = ((InfoList) data).getInfoList();
                for (ProblemInfo tem : infoList_P) {
                    String number = "" + tem.solved + "/" + tem.tried;
                    Map<String, String> listItem = new HashMap<>();
                    listItem.put("title", tem.title);
                    listItem.put("source", tem.source);
                    listItem.put("id", "" + tem.problemId);
                    listItem.put("number", number);
                    listFragments[1].addListItem(listItem);
                }
                listFragments[1].notifyDataSetChanged();
                break;

            case ViewHandler.PROBLEM_DETAIL:
                if (detailsFragments[1] != null) {
                    detailsFragments[1].addJSData(((Problem) data).getContentString());
                }
                if (detailsFragments_itemActivity[1] != null) {
                    detailsFragments_itemActivity[1].addJSData(((Problem) data).getContentString());
                }
                break;

            case ViewHandler.CONTEST_LIST:
                ArrayList<ContestInfo> infoList_C = ((InfoList) data).getInfoList();
                for (ContestInfo tem : infoList_C) {
                    Map<String, String> listItem = new HashMap<>();
                    listItem.put("title", tem.title);
                    listItem.put("releaseTime", tem.timeString);
                    listItem.put("timeLimit", tem.lengthString);
                    listItem.put("id", "" + tem.contestId);
                    listItem.put("status", tem.status);
                    listItem.put("permissions", tem.typeName);
                    listFragments[2].addListItem(listItem);
                }
                listFragments[2].notifyDataSetChanged();
                break;

            case ViewHandler.CONTEST_DETAIL:
                if (detailsFragments[2] != null) {
                    detailsFragments[2].addJSData(((Contest) data).getContentString());
                }
                if (detailsFragments_itemActivity[2] != null) {
                    detailsFragments_itemActivity[2].addJSData(((Contest) data).getContentString());
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

    public void getContent(DetailsWebViewFragment webViewFragment, int idOrPage) {
        switch (webViewFragment.getHTMLType()) {
            case ViewHandler.ARTICLE_DETAIL:
                detailsFragments_itemActivity[0] = webViewFragment;
                NetData.getArticleDetail(idOrPage, this);
                break;
            case ViewHandler.PROBLEM_DETAIL:
                detailsFragments_itemActivity[1] = webViewFragment;
                NetData.getProblemDetail(idOrPage, this);
                break;
            case ViewHandler.CONTEST_DETAIL:
                detailsFragments_itemActivity[2] = webViewFragment;
                NetData.getContestDetail(idOrPage, this);
                break;
        }
    }

    public NetContent addListFragments(ListFragmentWithGestureLoad[] listFragments) {
        this.listFragments = listFragments;
        return this;
    }

    public NetContent addDetailsFragments(DetailsWebViewFragment[] detailsFragments) {
        this.detailsFragments = detailsFragments;
        return this;
    }
}
