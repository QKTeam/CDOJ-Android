package cn.edu.uestc.acm.cdoj.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.ConvertNetData;
import cn.edu.uestc.acm.cdoj.net.NetData;
import cn.edu.uestc.acm.cdoj.net.NetDataPlus;
import cn.edu.uestc.acm.cdoj.net.NetHandler;
import cn.edu.uestc.acm.cdoj.net.Result;
import cn.edu.uestc.acm.cdoj.ui.modules.Global;
import cn.edu.uestc.acm.cdoj.ui.statusBar.FlyMeUtils;
import cn.edu.uestc.acm.cdoj.ui.statusBar.MIUIUtils;
import cn.edu.uestc.acm.cdoj.ui.statusBar.StatusBarUtil;
import cn.edu.uestc.acm.cdoj.ui.user.User;

public class LoginActivity extends AppCompatActivity implements ConvertNetData{
    ProgressDialog loggingDialog;
    EditText et_username;
    EditText et_password;
    String username;
    String passwordSHA1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupSystemBar();
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
    }

    private void setupSystemBar() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        StatusBarUtil.setStatusBarColor(this, R.color.statusBar_background_white, R.color.statusBar_background_gray, true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M || MIUIUtils.isMIUI() || FlyMeUtils.isFlyMe()) {
            StatusBarUtil.StatusBarLightMode(this);
        }
    }

    public void go(View view) {
        username = et_username.getText().toString();
        passwordSHA1 = NetData.sha1(et_password.getText().toString());
        NetDataPlus.login(this, username, passwordSHA1, true, this);
        loggingDialog = ProgressDialog.show(this, getString(R.string.login), getString(R.string.linking));
    }

    @NonNull
    @Override
    public Result onConvertNetData(String jsonString, Result result) {
        User user = JSON.parseObject(jsonString, User.class);
        if (user.getResult().equals("success")) {
            result.setStatus(Result.SUCCESS);
            result.setContent(user);
        } else {
            result.setStatus(Result.FALSE);
        }
        return result;
    }

    @Override
    public void onNetDataConverted(Result result) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        switch (result.getStatus()) {
            case Result.SUCCESS:
                saveUserInfo();
                dialog.setMessage(R.string.loginSuccess)
                    .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                break;

            case Result.FALSE:
                dialog.setMessage(R.string.loginFail)
                        .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                break;
        }
        loggingDialog.cancel();
        dialog.show();
    }

    private void saveUserInfo() {
        File file = new File(Global.getFilesDirPath() + "user");
        if (file.exists()) file.delete();
        try {
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(username);
            writer.write("\n");
            writer.write(passwordSHA1);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
