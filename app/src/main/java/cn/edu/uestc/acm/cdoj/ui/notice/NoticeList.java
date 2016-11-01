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
import cn.edu.uestc.acm.cdoj.Resource;
import cn.edu.uestc.acm.cdoj.ui.modules.list.ListViewWithGestureLoad;

/**
 * Created by great on 2016/8/17.
 */
public class NoticeList extends ListViewWithGestureLoad implements ConvertNetData {
    private static final String TAG = "文章列表";
    private List<ArticleData> articleDataList;
    private BaseAdapter mListAdapter;
    private PageInfo mPageInfo;

    public NoticeList(Context context) {
        this(context, null);
    }

    public NoticeList(Context context, AttributeSet attrs) {
        super(context, attrs);
        articleDataList = new ArrayList<>();
        mListAdapter = new NoticeAdapter(context, articleDataList);
    }

    @Override
    public void onPullUpLoad() {
        if (mPageInfo != null) {
            NetDataPlus.getArticleList(getContext(), mPageInfo.currentPage + 1, NoticeList.this);
        }
    }

    @Override
    public void onListRefresh() {
       refresh();
    }

    @Override
    public void onListItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!Resource.isTwoPane()) {
            showDetailOnActivity(position);
        }
    }

    private void showDetailOnActivity(int position) {
        Intent intent = new Intent(getContext(), ItemDetailActivity.class);
        intent.putExtra("id", articleDataList.get(position).getArticleId());
        intent.putExtra("type", NetData.ARTICLE_DETAIL);
        getContext().startActivity(intent);
    }

    @NonNull
    @Override
    public Result onConvertNetData(String jsonString, Result result) {
        ListReceive<ArticleData> listReceive = JSON.parseObject(jsonString, new TypeReference<ListReceive<ArticleData>>() {
        });
        if (!listReceive.getResult().equals("success")) {
            result.setStatus(Result.FALSE);
            return result;
        }

        mPageInfo = listReceive.getPageInfo();
        result.setContent(convertNetData(listReceive.getList()));

        if (mPageInfo.totalItems == 0) {
            result.setStatus(Result.DATAISNULL);
        } else if (mPageInfo.currentPage == mPageInfo.totalPages) {
            result.setStatus(Result.FINISH);
        } else {
            result.setStatus(Result.SUCCESS);
        }
        return result;
    }

    private List<ArticleData> convertNetData(List<ArticleData> list) {
        for (ArticleData articleData : list) {
            articleData.timeString = TimeFormat.getFormatDate(articleData.getTime());
            if (!articleData.isHasMore()) {
                articleData.jsonString = JSON.toJSONString(articleData);
            }
            if (articleData.getContent().equals("")) {
                articleData.setContent(articleData.getTitle());
            }
            int summaryLength = 50 < articleData.getContent().length() ? 50 : articleData.getContent().length();
            articleData.summary = articleData.getContent().substring(0, summaryLength);
            articleData.summary = articleData.summary.replaceAll("!\\[title].*\\)", "[图片]");
            articleData.summary = articleData.summary.replace("\n", " ");
        }
        return list;
    }

    @Override
    public void onNetDataConverted(Result result) {
        if (!hasAdapter()) {
            setListAdapter(mListAdapter);
        }
        if (result.getContent() != null) {
            articleDataList.addAll((List<ArticleData>) result.getContent());
            mListAdapter.notifyDataSetChanged();
        }
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

    public void clear() {
        resetPullUpLoad();
        articleDataList.clear();
        mListAdapter.notifyDataSetChanged();
    }

    public void refresh() {
        clear();
        setRefreshing(true);
        NetDataPlus.getArticleList(getContext(), 1, NoticeList.this);
    }
}
