package cn.edu.uestc.acm.cdoj_android.layout;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import cn.edu.uestc.acm.cdoj_android.Global;
import cn.edu.uestc.acm.cdoj_android.LoginActivity;
import cn.edu.uestc.acm.cdoj_android.R;

/**
 * Created by great on 2016/9/12.
 */
public class User extends Fragment {

    private TextView textView;
    private View rootView;
    private Button button;
    private boolean isLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);
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
//                        Global.userManager.
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                    }
                }
            });
        }
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
}
