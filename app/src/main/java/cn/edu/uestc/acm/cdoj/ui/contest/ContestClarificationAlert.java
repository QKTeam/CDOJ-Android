package cn.edu.uestc.acm.cdoj.ui.contest;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.webkit.WebView;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.ViewHandler;
import cn.edu.uestc.acm.cdoj.net.data.Article;
import cn.edu.uestc.acm.cdoj.net.data.Result;
import cn.edu.uestc.acm.cdoj.ui.modules.Global;

/**
 * Created by Great on 2016/10/19.
 */

public class ContestClarificationAlert extends WebView implements ViewHandler{

    private final String acmWebUrl = "http://acm.uestc.edu.cn/";
    private final String mimeType = "text/html";
    private final String encoding = "utf-8";

    private ProgressDialog progressDialog;

    public ContestClarificationAlert(Context context) {
        super(context);
    }

    public ContestClarificationAlert(Context context, ProgressDialog progressDialog) {
        super(context);
        this.progressDialog = progressDialog;
        init();
    }

    private void init() {
        getSettings().setJavaScriptEnabled(true);
    }

    @Override
    public void show(int which, Result result, long time) {
        if (result.result){
            progressDialog.cancel();
            String webData = Global.HTMLDATA_ARTICLE.replace("{{{replace_data_here}}}", ((Article) result.getContent()).getContentString());
            loadDataWithBaseURL(acmWebUrl, webData, mimeType, encoding, null);
            new AlertDialog.Builder(getContext())
                    .setView(this)
                    .setNegativeButton(R.string.confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        }
    }
}
