package cn.edu.uestc.acm.cdoj_android;

import android.app.ListFragment;

import java.util.ArrayList;

import cn.edu.uestc.acm.cdoj_android.layout.ArticleListFragment;
import cn.edu.uestc.acm.cdoj_android.layout.ContestListFragment;
import cn.edu.uestc.acm.cdoj_android.layout.DetailsContainerFragment;
import cn.edu.uestc.acm.cdoj_android.layout.ProblemListFragment;
import cn.edu.uestc.acm.cdoj_android.net.ViewHandler;
import cn.edu.uestc.acm.cdoj_android.net.data.Article;
import cn.edu.uestc.acm.cdoj_android.net.data.ArticleInfo;
import cn.edu.uestc.acm.cdoj_android.net.data.InfoList;
import cn.edu.uestc.acm.cdoj_android.net.data.Contest;
import cn.edu.uestc.acm.cdoj_android.net.data.ContestInfo;
import cn.edu.uestc.acm.cdoj_android.net.data.InfoList;
import cn.edu.uestc.acm.cdoj_android.net.data.Problem;
import cn.edu.uestc.acm.cdoj_android.net.data.ProblemInfo;
import cn.edu.uestc.acm.cdoj_android.net.data.InfoList;

/**
 * Created by great on 2016/8/15.
 */
public class Information implements ViewHandler {
    MainActivity main;
    ListFragment[] list_Fragment;
    DetailsContainerFragment detailsContainer_Fragment;
    public Information(MainActivity main, ListFragment[] list_Fragment, DetailsContainerFragment detailsContainer_Fragment) {
        this.main = main;
        this.list_Fragment = list_Fragment;
        this.detailsContainer_Fragment = detailsContainer_Fragment;
    }
    @Override
    public void show(int which, Object data) {
        switch (which) {
            case ViewHandler.ARTICLE_LIST:
                ArrayList<ArticleInfo> infoList_A = ((InfoList)data).getInfoList();
                for (ArticleInfo tem : infoList_A) {
                    ((ArticleListFragment) list_Fragment[0]).addToList(tem.title, tem.content, tem.timeString, tem.ownerName, ""+tem.articleId);
                }
                ((ArticleListFragment) list_Fragment[0]).notifyDataSetChanged();
                break;
            case ViewHandler.ARTICLE_DETAIL:
                detailsContainer_Fragment.addJSData(0, ((Article) data).getContentString());
                break;
            case ViewHandler.PROBLEM_LIST:
                ArrayList<ProblemInfo> infoList_P = ((InfoList)data).getInfoList();
                for (ProblemInfo tem : infoList_P) {
                    String number = ""+tem.solved+"/"+tem.tried;
                    ((ProblemListFragment) list_Fragment[1]).addToList(tem.title, tem.source, "" + tem.problemId, number);
                }
                ((ProblemListFragment)list_Fragment[1]).notifyDataSetChanged();
                break;
            case ViewHandler.PROBLEM_DETAIL:
//                main.showTestText(((Problem) data).getContentString());
                detailsContainer_Fragment.addJSData(1, ((Problem) data).getContentString());
                break;
            case ViewHandler.CONTEST_LIST:
                ArrayList<ContestInfo> infoList_C = ((InfoList)data).getInfoList();
                for (ContestInfo tem : infoList_C) {
                    ((ContestListFragment) list_Fragment[2]).addToList(tem.title, tem.timeString, tem.lengthString, "" + tem.contestId, tem.status, tem.typeName);
                }
                ((ContestListFragment)list_Fragment[2]).notifyDataSetChanged();
                break;
            case ViewHandler.CONTEST_DETAIL:
                detailsContainer_Fragment.addJSData(2, ((Contest) data).getContentString());
                break;
        }
    }
}
