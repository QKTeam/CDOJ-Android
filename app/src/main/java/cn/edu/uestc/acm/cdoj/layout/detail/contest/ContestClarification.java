package cn.edu.uestc.acm.cdoj.layout.detail.contest;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.edu.uestc.acm.cdoj.Global;
import cn.edu.uestc.acm.cdoj.ItemContentActivity;
import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.layout.list.ListFragmentWithGestureLoad;
import cn.edu.uestc.acm.cdoj.layout.list.PullUpLoadListView;
import cn.edu.uestc.acm.cdoj.net.NetData;
import cn.edu.uestc.acm.cdoj.net.ViewHandler;
import cn.edu.uestc.acm.cdoj.net.data.Article;
import cn.edu.uestc.acm.cdoj.net.data.ArticleInfo;
import cn.edu.uestc.acm.cdoj.net.data.InfoList;

import static cn.edu.uestc.acm.cdoj.Global.userManager;

/**
 * Created by great on 2016/8/25.
 */
public class ContestClarification extends ListFragmentWithGestureLoad implements ViewHandler{
    private SimpleAdapter adapter;
    private int contestID = -1;
    private ArrayList<Map<String, Object>> listItems = new ArrayList<>();
    private ListView mListView;
    private boolean firstLoad = true;
    private ProgressDialog mProgressDialog;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mListView = getListView();
            if (firstLoad && contestID != -1) refresh();
            setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    continuePullUpLoad();
                    refresh();
                }
            });
            setOnPullUpLoadListener(new PullUpLoadListView.OnPullUpLoadListener() {
                @Override
                public void onPullUpLoading() {
                    if (getPageInfo().currentPage < getPageInfo().totalPages) {
                        NetData.getContestComment(contestID, getPageInfo().currentPage + 1, ContestClarification.this);
                    } else {
                        stopPullUpLoad();
                    }
                }
            });
        }
        firstLoad = false;
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void addListItem(Map<String, Object> listItem) {
        listItems.add(listItem);
    }

    @Override
    public void notifyDataSetChanged() {
        if (adapter == null) {
            createAdapter();
        }
        adapter.notifyDataSetChanged();
        super.notifyDataSetChanged();
    }

    private void createAdapter() {
        adapter = new SimpleAdapter(
                Global.currentMainActivity, listItems, R.layout.contest_clarification_item_list,
                new String[]{"header", "user", "submitDate", "content"},
                new int[]{R.id.contestClarification_header, R.id.contestClarification_user,
                        R.id.contestClarification_submitDate, R.id.contestClarification_content});
        adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                if (view instanceof ImageView) {
                    if (data instanceof Integer) {
                        ((ImageView) view).setImageResource((int) data);
                        return true;
                    } else if (data instanceof Bitmap) {
                        ((ImageView) view).setImageBitmap((Bitmap) data);
                        return true;
                    }
                } else if (view instanceof TextView) {
                    ((TextView) view).setText(data.toString());
                    return true;
                }
                return false;
            }
        });
        setListAdapter(adapter);
    }

    public void setContestID(int id) {
        contestID = id;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        mProgressDialog = ProgressDialog
                .show(l.getContext(), getString(R.string.getting), getString(R.string.linking));
        NetData.getArticleDetail((int) listItems.get(position).get("id"), this);
        /*Context context = l.getContext();
        Intent intent = new Intent(context, ItemContentActivity.class);
        intent.putExtra("title", (String) listItems.get(position).get("title"));
        intent.putExtra("type", ViewHandler.ARTICLE_DETAIL);
        intent.putExtra("id", (int) listItems.get(position).get("id"));
        context.startActivity(intent);*/
    }

    @Override
    public void show(int which, Object data, long time) {
        switch (which) {
            case ViewHandler.CONTEST_COMMENT:
                if (((InfoList) data).result){
                    setPageInfo(((InfoList) data).pageInfo);
                    ArrayList<ArticleInfo> infoList_clarification = ((InfoList) data).getInfoList();
                    for (int i = 0; i != infoList_clarification.size(); ++i) {
                        ArticleInfo tem = infoList_clarification.get(i);
                        Map<String, Object> listItem = new HashMap<>();
                        listItem.put("header", R.drawable.logo);
                        listItem.put("content", tem.content.replaceAll("!\\[title].*\\)", "[图片]"));
                        listItem.put("submitDate", tem.timeString);
                        listItem.put("user", tem.ownerName);
                        listItem.put("title", tem.title);
                        listItem.put("id", tem.articleId);
                        listItem.put("email", tem.ownerEmail);
                        addListItem(listItem);
                        userManager.getAvatar(tem.ownerEmail, listItems.size()-1, this);
                    }
                }else {
                    getDataFailure();
                }
                notifyDataSetChanged();
                return;
            case ViewHandler.AVATAR:
                Object[] dataReceive = (Object[]) data;
                int position = (int) dataReceive[0];
                if (position < listItems.size())
                    listItems.get(position).put("header", dataReceive[1]);
                if (position >= mListView.getFirstVisiblePosition() &&
                        position <= mListView.getLastVisiblePosition()) {
                    ViewGroup viewGroup = (ViewGroup) mListView
                            .getChildAt(position - mListView.getFirstVisiblePosition());
                    ImageView headerImage = null;
                    if (viewGroup != null)
                        headerImage = (ImageView) viewGroup.findViewById(R.id.contestClarification_header);
                    if (headerImage != null) headerImage.setImageBitmap((Bitmap) dataReceive[1]);
                }
                return;
            case ViewHandler.ARTICLE_DETAIL:
                mProgressDialog.cancel();
                WebView webView = new WebView(mListView.getContext());
                webView.getSettings().setJavaScriptEnabled(true);
                if (Global.HTMLDATA_ARTICLE == null) {
                    try {
                        InputStream input;
                        byte[] in;
                        input = getResources().getAssets().open("articleRender.html");
                        in = new byte[input.available()];
                        input.read(in);
                        Global.HTMLDATA_ARTICLE = new String(in);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                String webData = Global.HTMLDATA_ARTICLE.replace("{{{replace_data_here}}}", ((Article) data).getContentString());
                webView.loadDataWithBaseURL("http://acm.uestc.edu.cn/", webData, "text/html", "utf-8", null);
                new AlertDialog.Builder(mListView.getContext())
                        .setView(webView)
                        .setNegativeButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
        }

    }


    public void refresh() {
        if (contestID == -1) throw new IllegalStateException("Clarification's contestId is null");
        listItems.clear();
        NetData.getContestComment(contestID, 1, this);
    }
}
