package cn.edu.uestc.acm.cdoj;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cn.edu.uestc.acm.cdoj.net.LoginCallBack;
import cn.edu.uestc.acm.cdoj.net.user.HandleUserData;
import cn.edu.uestc.acm.cdoj.net.user.UserConnection;

public class LoginActivity extends AppCompatActivity implements LoginCallBack{

    private static final String TAG = "LoginActivity";
    public static boolean isLogined = false;
    Button button_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        final HandleUserData handleUserData = new HandleUserData(this);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login_request = handleUserData.handle_login_json();
                Log.d(TAG, "login_request"+login_request);
                UserConnection.getInstance().login(login_request,LoginActivity.this);
            }
        });
    }

    private void initView() {
        button_login = (Button) findViewById(R.id.button_login);
    }


    @Override
    public void loginStatus(Bundle bundle) {
        String[] data = bundle.getStringArray("data");
        Intent intent  = new Intent(LoginActivity.this,MainActivity.class);
        if (data[0].equals("success")){
            String username = data[1];
            intent.putExtra("username",username);
            setResult(RESULT_OK,intent);
            isLogined = true;
            finish();
        }else {
            Toast.makeText(this,"登陆失败",Toast.LENGTH_SHORT).show();

        }
    }

}
