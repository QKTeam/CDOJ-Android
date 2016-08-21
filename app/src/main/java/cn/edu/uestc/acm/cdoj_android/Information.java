package cn.edu.uestc.acm.cdoj_android;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.edu.uestc.acm.cdoj_android.layout.DetailsContainerFragment;
import cn.edu.uestc.acm.cdoj_android.layout.ListContainerFragment;
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
public class Information implements ViewHandler {
    DetailsContainerFragment detailsContainer_Fragment;
    ListContainerFragment listContainerFragment;

    public Information(ListContainerFragment listContainerFragment, DetailsContainerFragment detailsContainer_Fragment) {
        this.listContainerFragment = listContainerFragment;
        this.detailsContainer_Fragment = detailsContainer_Fragment;
    }

    @Override
    public void show(int which, Object data) {
        switch (which) {
            case ViewHandler.ARTICLE_LIST:
                ArrayList<ArticleInfo> infoList_A = ((InfoList)data).getInfoList();
                for (ArticleInfo tem : infoList_A) {
                    Map<String,String> listItem = new HashMap<>();
                    listItem.put("title",tem.title);
                    listItem.put("content", tem.content);
                    listItem.put("releaseTime", tem.timeString);
                    listItem.put("author", tem.ownerName);
                    listItem.put("id",""+tem.articleId);
                    listContainerFragment.addListItem(ListContainerFragment.article, listItem);
                }
                listContainerFragment.notifyDataSetChanged(ListContainerFragment.article);
                break;

            case ViewHandler.ARTICLE_DETAIL:
                detailsContainer_Fragment.addJSData(0, ((Article) data).getContentString());
                break;

            case ViewHandler.PROBLEM_LIST:
                ArrayList<ProblemInfo> infoList_P = ((InfoList)data).getInfoList();
                for (ProblemInfo tem : infoList_P) {
                    String number = ""+tem.solved+"/"+tem.tried;
                    Map<String,String> listItem = new HashMap<>();
                    listItem.put("title",tem.title);
                    listItem.put("source", tem.source);
                    listItem.put("id", ""+tem.problemId);
                    listItem.put("number", number);
                    listContainerFragment.addListItem(ListContainerFragment.problem,listItem);
                }
                listContainerFragment.notifyDataSetChanged(ListContainerFragment.problem);
                break;

            case ViewHandler.PROBLEM_DETAIL:
                detailsContainer_Fragment.addJSData(1, ((Problem) data).getContentString());
                break;

            case ViewHandler.CONTEST_LIST:
                ArrayList<ContestInfo> infoList_C = ((InfoList)data).getInfoList();
                for (ContestInfo tem : infoList_C) {
                    Map<String,String> listItem = new HashMap<>();
                    listItem.put("title",tem.title);
                    listItem.put("releaseTime", tem.timeString);
                    listItem.put("timeLimit", tem.lengthString);
                    listItem.put("id", ""+tem.contestId);
                    listItem.put("status", tem.status);
                    listItem.put("permissions", tem.typeName);
                    listContainerFragment.addListItem(ListContainerFragment.contest, listItem);
                }
                listContainerFragment.notifyDataSetChanged(ListContainerFragment.contest);
                break;

            case ViewHandler.CONTEST_DETAIL:
                detailsContainer_Fragment.addJSData(2, ((Contest) data).getContentString());
                break;
        }
    }
}
