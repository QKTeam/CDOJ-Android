package cn.edu.uestc.acm.cdoj;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.uestc.acm.cdoj.net.LoginCallBack;
import cn.edu.uestc.acm.cdoj.net.user.HandleUserData;
import cn.edu.uestc.acm.cdoj.net.user.UserConnection;

public class LoginActivity extends AppCompatActivity implements LoginCallBack,View.OnClickListener{

    private static final String TAG = "LoginActivity";

    private Button button_login;
    private Button button_register;
    private TextView text_forgot_password;
    final HandleUserData handleUserData = new HandleUserData(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        button_login = (Button) findViewById(R.id.button_login);
        button_register = (Button) findViewById(R.id.button_register);
        text_forgot_password = (TextView) findViewById(R.id.text_forgot_password);
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
                UserConnection.getInstance().login(login_request,LoginActivity.this);
                break;
            case R.id.text_forgot_password:
                break;
        }
    }

    @Override
    public void loginStatus(Bundle bundle) {
        String[] data = bundle.getStringArray("data");
        Intent intent  = new Intent(LoginActivity.this,MainActivity.class);
        if (data[0].equals("success")){
            String username = data[1];
            intent.putExtra("username",username);
            setResult(RESULT_OK,intent);
            finish();
        }else {
            Toast.makeText(this,"登陆失败",Toast.LENGTH_SHORT).show();

        }
    }


}
