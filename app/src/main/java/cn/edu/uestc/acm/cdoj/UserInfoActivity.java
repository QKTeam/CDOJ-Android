package cn.edu.uestc.acm.cdoj;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import cn.edu.uestc.acm.cdoj.net.UserInfoCallback;
import cn.edu.uestc.acm.cdoj.net.user.HandleUserData;
import cn.edu.uestc.acm.cdoj.net.user.UserConnection;
import cn.edu.uestc.acm.cdoj.net.user.UserInfoReceived;
import cn.edu.uestc.acm.cdoj.utils.FileUtil;

public class UserInfoActivity extends AppCompatActivity implements UserInfoCallback,View.OnClickListener{

    private static final String TAG = "LoginActivity";

    private final HandleUserData handleUserData = new HandleUserData(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        Button button_login = (Button) findViewById(R.id.button_login);
        Button button_register = (Button) findViewById(R.id.button_register);
        TextView text_forgot_password = (TextView) findViewById(R.id.text_forgot_password);
        button_login.setOnClickListener(this);
        button_register.setOnClickListener(this);
        text_forgot_password.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_register:

                break;
            case R.id.button_login:
                String login_request = handleUserData.handle_login_json();
                UserConnection.getInstance().login(login_request,UserInfoActivity.this);
                break;
            case R.id.text_forgot_password:
                break;
        }
    }

    @Override
    public void loginStatus(Bundle bundle) {
        String[] data = bundle.getStringArray("data");
        if (data!=null&&data[0].equals("success")){
            String userName = data[1];
            UserConnection.getInstance().getUserInfo(UserInfoActivity.this, userName,this,120);
        }else {
            Toast.makeText(this,"登陆失败",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getUserInfo(UserInfoReceived.UserBean userBean) {
        String UserInfo = JSON.toJSONString(userBean);
        FileUtil.saveUserInfo(this,UserInfo,userBean.getUserName());
        Intent intent  = new Intent(UserInfoActivity.this,MainActivity.class);
        Log.d(TAG, "userName:"+userBean.getUserName());
        intent.putExtra("userName",userBean.getUserName());
        startActivity(intent);
    }

}
