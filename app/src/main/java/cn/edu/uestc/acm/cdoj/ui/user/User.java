package cn.edu.uestc.acm.cdoj.ui.user;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import cn.edu.uestc.acm.cdoj.ui.modules.Global;
import cn.edu.uestc.acm.cdoj.ui.LoginActivity;
import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.ViewHandler;

/**
 * Created by great on 2016/9/12.
 */
public class User extends Fragment implements ViewHandler{

    private TextView textView;
    private View rootView;
    private Button button;
    private boolean isLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            rootView = inflater.inflate(R.layout.user, container, false);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            textView = (TextView) rootView.findViewById(R.id.user_text);
            button = (Button) rootView.findViewById(R.id.user_button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isLogin) {
                        Global.userManager.logout(User.this);
                        new AlertDialog.Builder(v.getContext())
                                .setMessage("已成功退出")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .show();
                        setLogin(false);
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }

    @Override
    public void onResume() {
        setLogin(Global.userManager.isLogin());
        super.onResume();
    }

    public void setLogin(boolean login) {
        isLogin = login;
        if (login) {
            textView.setText("已登录");
            button.setText("退出");
            return;
        }
        textView.setText("未登录");
        button.setText("登录");
    }

    @Override
    public void show(int which, Object data, long time) {

    }
}
