package cn.edu.uestc.acm.cdoj.ui.detailFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.genaralData.ContentReceived;
import cn.edu.uestc.acm.cdoj.net.Connection;
import cn.edu.uestc.acm.cdoj.net.ReceivedCallback;
import cn.edu.uestc.acm.cdoj.utils.JsonUtil;
import cn.edu.uestc.acm.cdoj.utils.Request;
import cn.edu.uestc.acm.cdoj.utils.ThreadUtil;

/**
 * Created by 14779 on 2017-8-9.
 */

public class CodeFragment extends Fragment implements View.OnClickListener, ReceivedCallback<ContentReceived> {
    private EditText codeEdit;
    private Button submitButton;
    private Button jumpButton;
    private int id;
    /**
     * 用于判断是比赛提交还是问题提交*/
    private String type;

    public CodeFragment(int id, String type){
        this.id = id;
        this.type = type;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.code_frament, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        codeEdit = view.findViewById(R.id.code_edit_text);
        submitButton = view.findViewById(R.id.submit_code_button);
        jumpButton = view.findViewById(R.id.jump_to_codeEditor);
        submitButton.setOnClickListener(this);
        jumpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.submit_code_button:
                String codeContent = codeEdit.getText().toString();
                if (type.equals("contestProblem")){
                    Connection.instance.submitContestCode(id, codeContent, 1, this);
                } else {
                    Connection.instance.submitProblemCode(id, codeContent, 1, this);
                }
                getFragmentManager().beginTransaction().remove(this).commit();
                break;
            case R.id.jump_to_codeEditor:
                break;
        }
    }

    @Override
    public void onDataReceived(ContentReceived contentReceived) {

    }

    @Override
    public void onLoginDataReceived(ContentReceived dataReceived) {

    }
}
