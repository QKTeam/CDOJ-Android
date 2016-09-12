package cn.edu.uestc.acm.cdoj_android;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import cn.edu.uestc.acm.cdoj_android.net.ViewHandler;
import cn.edu.uestc.acm.cdoj_android.statusBar.FlyMeUtils;
import cn.edu.uestc.acm.cdoj_android.statusBar.MIUIUtils;
import cn.edu.uestc.acm.cdoj_android.statusBar.StatusBarUtil;

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
        initStatusBar();
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
    }

    private void initStatusBar() {
        if (MIUIUtils.isMIUI() || FlyMeUtils.isFlyMe() || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) {
            StatusBarUtil.setStatusBarColor(this, R.color.statusBar_background_white);
            StatusBarUtil.StatusBarLightMode(this);
        } else {
            StatusBarUtil.setStatusBarColor(this, R.color.statusBar_background_gray);
        }
    }

    public void go(View view) {
        username = et_username.getText().toString();
        password = et_password.getText().toString();
        Log.d("账号", username);
        Log.d("密码", password);
        Global.userManager.login(username, password, this);
        loggingDialog = ProgressDialog.show(this, getString(R.string.login), getString(R.string.linking));
    }

    @Override
    public void show(int which, Object data, long time) {
        final boolean isSuccessful = (boolean) data;
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
