package cn.edu.uestc.acm.cdoj.net.user;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import cn.edu.uestc.acm.cdoj.R;

/**
 * Created by lagranmoon on 2017/8/2.
 */

public class Register_Step1_Fragment extends Fragment {

    public interface SendDataCallback {
        void onDataReceived(String[] register_info_simple_info);
    }

    private Button button;
    private HandleUserData handleUserData = new HandleUserData(this);
    private SendDataCallback sendDataCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SendDataCallback) {
            sendDataCallback = (SendDataCallback) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_step1, container, false);
        button = view.findViewById(R.id.button_next_step);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] register_info_simple_info = handleUserData.get_register_simple_info();
                sendDataCallback.onDataReceived(register_info_simple_info);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            }
        });
    }
}
