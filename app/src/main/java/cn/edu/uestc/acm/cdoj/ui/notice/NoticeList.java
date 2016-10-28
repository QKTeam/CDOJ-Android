package cn.edu.uestc.acm.cdoj.ui.notice;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.List;

import cn.edu.uestc.acm.cdoj.net.ConvertNetData;
import cn.edu.uestc.acm.cdoj.net.NetData;
import cn.edu.uestc.acm.cdoj.net.NetDataPlus;
import cn.edu.uestc.acm.cdoj.net.Result;
import cn.edu.uestc.acm.cdoj.net.data.ArticleData;
import cn.edu.uestc.acm.cdoj.net.data.ListReceive;
import cn.edu.uestc.acm.cdoj.net.data.PageInfo;
import cn.edu.uestc.acm.cdoj.tools.TimeFormat;
import cn.edu.uestc.acm.cdoj.ui.ItemDetailActivity;
import cn.edu.uestc.acm.cdoj.ui.modules.Global;
import cn.edu.uestc.acm.cdoj.ui.modules.list.ListViewWithGestureLoad;

/**
 * Created by great on 2016/8/17.
 */
public class NoticeList extends ListViewWithGestureLoad implements ConvertNetData {
    private static final String TAG = "文章列表";
    private List<ArticleData> articleDataList = new ArrayList<>();
    private BaseAdapter mListAdapter;
    private PageInfo mPageInfo;

    public NoticeList(Context context) {
        this(context, null);
    }

    public NoticeList(Context context, AttributeSet attrs) {
        super(context, attrs);
        mListAdapter = new NoticeAdapter(context, articleDataList);
        setListAdapter(mListAdapter);
    }

    @Override
    public void onPullUpLoad() {
        if (mPageInfo != null) {
            NetDataPlus.getArticleList(getContext(), mPageInfo.currentPage + 1, NoticeList.this);
        }
    }

    @Override
    public void onListRefresh() {
        resetPullUpLoad();
        articleDataList.clear();
        mListAdapter.notifyDataSetChanged();
        NetDataPlus.getArticleList(getContext(), 1, NoticeList.this);
    }

    @Override
    public void onListItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!Global.isTwoPane()) {
            showDetailOnActivity(position);
        }
    }

    private void showDetailOnActivity(int position) {
        Intent intent = new Intent(getContext(), ItemDetailActivity.class);
        intent.putExtra("article", articleDataList.get(position));
        intent.putExtra("type", NetData.ARTICLE_DETAIL);
        getContext().startActivity(intent);
    }

    @NonNull
    @Override
    public Result onConvertNetData(String jsonString, Result result) {
        ListReceive<ArticleData> listReceive = JSON.parseObject(jsonString, new TypeReference<ListReceive<ArticleData>>() {
        });

        Log.d(TAG, "onConvertNetData: "+listReceive.getResult()+" "+jsonString);
        mPageInfo = listReceive.getPageInfo();
        articleDataList.addAll(convertNetData(listReceive.getList()));

        if (mPageInfo.totalItems == 0) {
            result.setStatus(Result.DATAISNULL);
        } else if (mPageInfo.currentPage == mPageInfo.totalPages) {
            result.setStatus(Result.FINISH);
        } else if (listReceive.getResult().equals("success")) {
            result.setStatus(Result.SUCCESS);
        } else {
            result.setStatus(Result.FALSE);
        }
        return result;
    }

    private List<ArticleData> convertNetData(List<ArticleData> list) {
        for (ArticleData articleData : list) {
            articleData.timeString = TimeFormat.getFormatDate(articleData.getTime());
            if (!articleData.isHasMore()) {
                articleData.jsonString = JSON.toJSONString(articleData);
            }
            Log.d(TAG, "convertNetData: "+articleData.getContent());
            if (articleData.getContent().equals("")) {
                articleData.setContent(articleData.getTitle());
            }
            articleData.contentWithoutLink = articleData.getContent().replaceAll("!\\[title].*\\)", "[图片]");
            articleData.contentWithoutLink = articleData.contentWithoutLink.replace("\n", " ");
        }
        return list;
    }

    @Override
    public void onNetDataConverted(Result result) {
        mListAdapter.notifyDataSetChanged();
        noticeLoadOrRefreshComplete();
        switch (result.getStatus()) {
            case Result.SUCCESS:
                break;
            case Result.DATAISNULL:
                noticeDataIsNull();
                break;
            case Result.FINISH:
                noticeLoadFinish();
                break;
            case Result.NETNOTCONECT:
                noticeNetNotConnect();
                break;
            case Result.CONECTOVERTIME:
                noticeConnectOvertime();
                break;
            case Result.FALSE:
                noticeLoadFailure();
                break;
            case Result.DEFAULT:
                break;
        }
    }
}
