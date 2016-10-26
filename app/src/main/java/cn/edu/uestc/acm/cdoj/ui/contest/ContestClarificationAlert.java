package cn.edu.uestc.acm.cdoj.ui.contest;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.webkit.WebView;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.ConvertNetData;
import cn.edu.uestc.acm.cdoj.net.NetHandler;
import cn.edu.uestc.acm.cdoj.net.Result;
import cn.edu.uestc.acm.cdoj.ui.modules.Global;

/**
 * Created by Great on 2016/10/19.
 */

public class ContestClarificationAlert extends WebView implements ConvertNetData{

    private final String acmWebUrl = "http://acm.uestc.edu.cn/";
    private final String mimeType = "text/html";
    private final String encoding = "utf-8";

    private ProgressDialog progressDialog;
    private AlertDialog.Builder alertDialog;

    public ContestClarificationAlert(Context context) {
        super(context);
    }

    public ContestClarificationAlert(Context context, ProgressDialog progressDialog) {
        super(context);
        this.progressDialog = progressDialog;
        initView();
    }

    private void initView() {
        alertDialog = new AlertDialog.Builder(getContext())
                .setNegativeButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
    }

    @NonNull
    @Override
    public Result onConvertNetData(String jsonString, Result result) {
        result.setContent(Global.HTMLDATA_ARTICLE.replace("{{{replace_data_here}}}", jsonString));
        return result;
    }

    @Override
    public void onNetDataConverted(Result result) {
        progressDialog.cancel();
        switch (result.getType()) {
            case NetHandler.Status.SUCCESS:
                loadDataWithBaseURL(acmWebUrl, String.valueOf(result.getContent()), mimeType, encoding, null);
                alertDialog.setView(this);
                break;
            case NetHandler.Status.DATAISNULL:
                break;
            case NetHandler.Status.FINISH:
                break;
            case NetHandler.Status.NETNOTCONECT:
                alertDialog.setTitle(R.string.netNotConnect);
                break;
            case NetHandler.Status.CONECTOVERTIME:
                alertDialog.setTitle(R.string.connectOvertime);
                break;
            case NetHandler.Status.FALSE:
                alertDialog.setTitle(R.string.loadError);
                break;
            case NetHandler.Status.DEFAULT:
                break;
        }
        alertDialog.show();
    }
}
