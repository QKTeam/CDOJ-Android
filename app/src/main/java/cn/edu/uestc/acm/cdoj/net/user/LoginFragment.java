package cn.edu.uestc.acm.cdoj.net.user;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import cn.edu.uestc.acm.cdoj.R;

/**
 * Created by lagranmoon on 2017/8/3.
 */

public class LoginFragment extends Fragment implements View.OnClickListener{
    private Context context = getActivity();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_login,container,false);
        initView();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    private void initView() {
        Button button_login = getView().findViewById(R.id.button_login);
        Button button_register = getView().findViewById(R.id.button_register);
        TextView text_forgot_password = getView().findViewById(R.id.text_forgot_password);
        button_login.setOnClickListener(this);
        button_register.setOnClickListener(this);
        text_forgot_password.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }
}
