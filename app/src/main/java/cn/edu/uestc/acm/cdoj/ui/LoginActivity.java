package cn.edu.uestc.acm.cdoj.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.data.Result;
import cn.edu.uestc.acm.cdoj.ui.modules.Global;
import cn.edu.uestc.acm.cdoj.net.ViewHandler;
import cn.edu.uestc.acm.cdoj.ui.statusBar.FlyMeUtils;
import cn.edu.uestc.acm.cdoj.ui.statusBar.MIUIUtils;
import cn.edu.uestc.acm.cdoj.ui.statusBar.StatusBarUtil;

public class LoginActivity extends AppCompatActivity implements ViewHandler{
    ProgressDialog loggingDialog;
    EditText et_username;
    EditText et_password;
    String username;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        configStatusBar();
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
    }

    private void configStatusBar() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        View statusColorView = StatusBarUtil.setStatusBarColor(this, R.color.statusBar_background_white, R.color.gray);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M || MIUIUtils.isMIUI() || FlyMeUtils.isFlyMe()) {
            StatusBarUtil.StatusBarLightMode(this);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            DrawerLayout.LayoutParams layoutParams = new DrawerLayout.LayoutParams()
            if (MIUIUtils.isMIUI() || FlyMeUtils.isFlyMe()) {
                StatusBarUtil.StatusBarLightMode(this);
            }
        }
    }

    public void go(View view) {
        username = et_username.getText().toString();
        password = et_password.getText().toString();
        Global.userManager.login(username, password, this);
        loggingDialog = ProgressDialog.show(this, getString(R.string.login), getString(R.string.linking));
    }

    @Override
    public void show(int which, Result result, long time) {
        final boolean isSuccessful = result.result;
        loggingDialog.cancel();
        AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (isSuccessful) {
                            finish();
                        }
                    }
                });
        if (isSuccessful) {
            dialog.setMessage(R.string.loginSuccess);
        }else {
            dialog.setMessage(R.string.loginFail);
        }
        dialog.show();
    }
}
