package cn.edu.uestc.acm.cdoj.ui.problem;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.getbase.floatingactionbutton.FloatingActionButton;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.Resource;
import cn.edu.uestc.acm.cdoj.net.ConvertNetData;
import cn.edu.uestc.acm.cdoj.net.NetData;
import cn.edu.uestc.acm.cdoj.net.Result;
import cn.edu.uestc.acm.cdoj.net.data.ProblemData;
import cn.edu.uestc.acm.cdoj.net.data.ProblemReceive;
import cn.edu.uestc.acm.cdoj.ui.ItemDetailActivity;
import cn.edu.uestc.acm.cdoj.ui.LoginActivity;
import cn.edu.uestc.acm.cdoj.ui.contest.ContestView;
import cn.edu.uestc.acm.cdoj.ui.modules.detail.DetailWebView;
import cn.edu.uestc.acm.cdoj.net.NetDataPlus;

/**
 * Created by great on 2016/8/25.
 */
public class ProblemView extends FrameLayout implements ConvertNetData{
    private static final String TAG = "问题详情页";
    private DetailWebView mWebView;
    private TextView titleView;
    private FloatingActionButton button_addCode;
    private FloatingActionButton button_checkResult;
    private ProblemData problemData;
    private EditText codeEditView;
    private AlertDialog submitCodeAlert;
    private LayoutInflater mInflater;
    private int contestId = -1;
    private ViewPager contestPager;

    public ProblemView(Context context) {
        this(context, null);
    }

    public ProblemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void initView(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.problem_content, this, true);
        titleView = (TextView) findViewById(R.id.problem_title);
        mWebView = (DetailWebView) findViewById(R.id.problem_webView);
        mWebView.switchType(NetData.PROBLEM_DETAIL);
        mInflater.inflate(R.layout.problem_float_buttons, this, true);
        button_addCode = (FloatingActionButton) findViewById(R.id.problem_button_addCede);
        button_checkResult = (FloatingActionButton) findViewById(R.id.problem_button_checkResult);
        setupButtons();
    }

    private void setupButtons() {
        button_addCode.setIconDrawable(Resource.getProblemIcon_addCode());
        button_checkResult.setIconDrawable(Resource.getProblemIcon_checkResult());
        button_addCode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (submitCodeAlert == null) {
                    codeEditView = (EditText) mInflater.inflate(R.layout.code_text_view, null);
                    submitCodeAlert = new AlertDialog.Builder(getContext())
                            .setView(codeEditView)
                            .setNegativeButton("C", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    submitCodeAlert.cancel();
                                    ProgressDialog progressDialog = ProgressDialog.show(getContext(), "代码提交", "正在提交中...");
                                    NetDataPlus.submitCode(getContext(), codeEditView.getText().toString(), 1, contestId, problemData.getProblemId(), progressDialog, ProblemView.this);
                                }
                            })
                            .setNeutralButton("C++", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    submitCodeAlert.cancel();
                                    ProgressDialog progressDialog = ProgressDialog.show(getContext(), "代码提交", "正在提交中...");
                                    NetDataPlus.submitCode(getContext(), codeEditView.getText().toString(), 2, contestId, problemData.getProblemId(), progressDialog, ProblemView.this);
                                }
                            })
                            .setPositiveButton("Java", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    submitCodeAlert.cancel();
                                    ProgressDialog progressDialog = ProgressDialog.show(getContext(), "代码提交", "正在提交中...");
                                    NetDataPlus.submitCode(getContext(), codeEditView.getText().toString(), 3, contestId, problemData.getProblemId(), progressDialog, ProblemView.this);
                                }
                            })
                            .create();
                }
                submitCodeAlert.show();
            }
        });
        button_checkResult.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showSubmitStatus();
            }
        });
    }

    private void showSubmitStatus() {
        if (contestId < 1) {
            Intent intent = new Intent(getContext(), ItemDetailActivity.class);
            intent.putExtra("type", NetData.STATUS_LIST);
            intent.putExtra("id", problemData.getProblemId());
            intent.putExtra("title", problemData.getTitle());
            getContext().startActivity(intent);
        } else {
            if (contestPager != null) {
                contestPager.setCurrentItem(ContestView.STATUS);
            }
        }
    }

    public ProblemView setTitle(String title) {
        titleView.setText(title);
        return this;
    }

    public void setTitleVisibility(int visibility) {
        titleView.setVisibility(visibility);
    }

    public ProblemData getProblemData() {
        return problemData;
    }

    public void setProblemData(ProblemData problemData) {
        if (problemData == null) return;
        this.problemData = problemData;
        mWebView.setJsonString(problemData.jsonString);
        setTitle(problemData.getTitle());
    }

    @NonNull
    @Override
    public Result onConvertNetData(String jsonString, Result result) {
        switch (result.getDataType()) {
            case NetData.PROBLEM_DETAIL:
                ProblemReceive problemReceive = JSON.parseObject(jsonString, ProblemReceive.class);
                if (!problemReceive.getResult().equals("success")) {
                    result.setStatus(Result.FALSE);
                    return result;
                }
                problemData = problemReceive.getProblem();
                problemData.jsonString = JSON.toJSONString(problemData);
                result.setStatus(Result.SUCCESS);
                break;
            case NetData.STATUS_SUBMIT:
                JSONObject receive = JSON.parseObject(jsonString);
                if (receive.get("result").equals("success")) {
                    result.setStatus(Result.SUCCESS);
                } else {
                    result.setContent(receive.get("error_msg"));
                    result.setStatus(Result.FALSE);
                }
        }
        return result;
    }

    @Override
    public void onNetDataConverted(Result result) {
        switch (result.getDataType()) {
            case NetData.PROBLEM_DETAIL:
                if (result.getStatus() == Result.SUCCESS) {
                    setTitle(problemData.getTitle());
                    mWebView.setJsonString(problemData.jsonString);
                }
                break;
            case NetData.STATUS_SUBMIT:
                ((ProgressDialog) result.getExtra()).cancel();
                if (result.getStatus() == Result.SUCCESS) {
                    showSubmitStatus();
                    Toast.makeText(getContext(), "提交成功！", Toast.LENGTH_LONG)
                            .show();
                } else {
                    if (result.getContent() != null && result.getContent().equals("Please login first.")) {
                        new AlertDialog.Builder(getContext())
                                .setTitle(getContext().getString(R.string.notLogin))
                                .setMessage(getContext().getString(R.string.reminderNotLogin))
                                .setPositiveButton(getContext().getString(R.string.login), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(getContext(), LoginActivity.class);
                                        getContext().startActivity(intent);
                                    }
                                })
                                .setNegativeButton(getContext().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .show();
                    }else {
                        Toast.makeText(getContext(), "提交的代码不符合规定", Toast.LENGTH_LONG)
                                .show();
                    }
                }
        }
    }

    public ProblemView refresh(int id) {
        NetDataPlus.getProblemDetail(getContext(), id, this);
        return this;
    }

    public int getContestId() {
        return contestId;
    }

    public void setContestId(int contestId) {
        this.contestId = contestId;
    }

    public void setContestPager(ViewPager contestPager) {
        this.contestPager = contestPager;
    }
}
