package cn.edu.uestc.acm.cdoj.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import cn.edu.uestc.acm.cdoj.R;
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
        initStatusBar();
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
    }

    private void initStatusBar() {

    }

    public void go(View view) {
        username = et_username.getText().toString();
        password = et_password.getText().toString();
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
