package cn.edu.uestc.acm.cdoj.net.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.UserInfoCallback;

/**
 * Created by lagranmoon on 2017/8/4.
 */

public class Register_Step2_Fragment extends Fragment implements UserInfoCallback{

    private Button button;
    private HandleUserData handleUserData = new HandleUserData(this);
    private String[] register_info_simple_info;
    private String request_json;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_step2,container,false);
        button = view.findViewById(R.id.button_register_step2);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request_json = handleUserData.get_register_json(register_info_simple_info);
                UserConnection.getInstance().register(request_json,Register_Step2_Fragment.this);
            }
        });
    }

    @Override
    public void registerStatus(Bundle bundle) {

    }



    public void setRegister_info_simple_info(String[] register_info_simple_info_data){
        for (int i = 0;i<register_info_simple_info_data.length;i++){
            register_info_simple_info[i] = register_info_simple_info_data[i];
        }
    }

    @Override
    public void loginStatus(Bundle bundle) {

    }

    @Override
    public void getUserInfo(UserInfo userInfo) {

    }
}
