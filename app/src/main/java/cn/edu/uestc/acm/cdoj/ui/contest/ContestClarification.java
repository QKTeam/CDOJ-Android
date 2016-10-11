package cn.edu.uestc.acm.cdoj.ui.contest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.edu.uestc.acm.cdoj.net.data.PageInfo;
import cn.edu.uestc.acm.cdoj.ui.modules.Global;
import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.ui.modules.list.ListViewWithGestureLoad;
import cn.edu.uestc.acm.cdoj.net.NetData;
import cn.edu.uestc.acm.cdoj.net.ViewHandler;
import cn.edu.uestc.acm.cdoj.net.data.Article;
import cn.edu.uestc.acm.cdoj.net.data.ArticleInfo;
import cn.edu.uestc.acm.cdoj.net.data.InfoList;

import static cn.edu.uestc.acm.cdoj.ui.modules.Global.userManager;

/**
 * Created by great on 2016/8/25.
 */
public class ContestClarification extends Fragment implements ViewHandler{
    private final String acmWebUrl = "http://acm.uestc.edu.cn/";
    private final String mimeType = "text/html";
    private final String encoding = "utf-8";

    private SimpleAdapter mListAdapter;
    private int contestID = -1;
    private ArrayList<Map<String, Object>> listItems = new ArrayList<>();
    private ProgressDialog mProgressDialog;
    private ListViewWithGestureLoad mListView;
    private PageInfo mPageInfo;
    private Context context;
    private boolean refreshed;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mListView = new ListViewWithGestureLoad(context);
        mListView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        mListView.setOnPullUpLoadListener(new ListViewWithGestureLoad.OnPullUpLoadListener() {
            @Override
            public void onPullUpLoading() {
                if (mPageInfo != null && mPageInfo.currentPage < mPageInfo.totalPages) {
                    NetData.getContestComment(contestID, mPageInfo.currentPage + 1, ContestClarification.this);
                } else {
                    mListView.setPullUpLoadFinish();
                }
            }
        });
        configOnListItemClick();
        if (refreshed) refresh();
        mListView.setLayoutParams(container.getLayoutParams());
        return mListView;
    }

    public void addListItem(Map<String, Object> listItem) {
        listItems.add(listItem);
    }

    public void notifyDataSetChanged() {
        if (mListAdapter == null) {
            mListView.setAdapter(setupAdapter());
        }else {
            mListAdapter.notifyDataSetChanged();
        }
        mListAdapter.notifyDataSetChanged();
        if (mListView.isPullUpLoading()) {
            mListView.setPullUpLoading(false);
        }
        if (mListView.isRefreshing()) {
            mListView.setRefreshing(false);
        }
    }

    private ListAdapter setupAdapter() {
        mListAdapter = new SimpleAdapter(
                Global.currentMainUIActivity, listItems, R.layout.contest_clarification_item_list,
                new String[]{"header", "user", "submitDate", "content"},
                new int[]{R.id.contestClarification_header, R.id.contestClarification_user,
                        R.id.contestClarification_submitDate, R.id.contestClarification_content}){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                v.setTag(position);
                return v;
            }
        };
        mListAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
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
        return mListAdapter;
    }


    private void configOnListItemClick() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mProgressDialog = ProgressDialog
                        .show(parent.getContext(), getString(R.string.getting), getString(R.string.linking));
                NetData.getArticleDetail((int) listItems.get(position).get("id"), ContestClarification.this);
            }
        });
    }

    @Override
    public void show(int which, Object data, long time) {
        switch (which) {
            case ViewHandler.CONTEST_COMMENT:
                if (refreshed) {
                    listItems.clear();
                    notifyDataSetChanged();
                    refreshed = false;
                }
                if (((InfoList) data).result){
                    mPageInfo = ((InfoList) data).pageInfo;
                    ArrayList<ArticleInfo> infoList_clarification = ((InfoList) data).getInfoList();
                    if (infoList_clarification.size() == 0) {
                        mListView.setDataIsNull();
                        notifyDataSetChanged();
                        return;
                    }
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
                    if (mPageInfo.currentPage == mPageInfo.totalItems) {
                        mListView.setPullUpLoadFinish();
                    }
                }else {
                    mListView.setPullUpLoadFailure();
                }
                notifyDataSetChanged();
                return;
            case ViewHandler.AVATAR:
                Object[] dataReceive = (Object[]) data;
                int position = (int) dataReceive[0];
                if (position < listItems.size())
                    listItems.get(position).put("header", dataReceive[1]);
                View view = mListView.findItemViewWithTag(position);
                if (view != null) {
                    ImageView imageView = (ImageView) view.findViewById(R.id.contestClarification_header);
                    if (imageView != null) imageView.setImageBitmap((Bitmap) dataReceive[1]);
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
                webView.loadDataWithBaseURL(acmWebUrl, webData, mimeType, encoding, null);
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

    private void refresh() {
        if (contestID != -1) refresh(contestID);
    }

    public ContestClarification refresh(int contestID) {
        if (contestID < 1) return this;
        refreshed = true;
        this.contestID = contestID;
        if (mListView != null) {
            mListView.resetPullUpLoad();
            NetData.getContestComment(contestID, 1, this);
        }
        return this;
    }
}
