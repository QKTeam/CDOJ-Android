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
import cn.edu.uestc.acm.cdoj.net.Connection;
import cn.edu.uestc.acm.cdoj.utils.JsonUtil;
import cn.edu.uestc.acm.cdoj.utils.Request;
import cn.edu.uestc.acm.cdoj.utils.ThreadUtil;

/**
 * Created by 14779 on 2017-8-9.
 */

public class CodeFragment extends Fragment implements View.OnClickListener {
    private EditText codeEdit;
    private Button submitButton;
    private Button jumpButton;
    private int id;

    public CodeFragment(int id){
        this.id = id;
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
                String[] key = {"problemId","codeContent","languageId"};
                Object[] value = {id, codeContent, 1};
                final String request = JsonUtil.getJsonString(key, value);
                ThreadUtil.getInstance().execute(new Runnable() {
                    @Override
                    public void run() {
                        Request.post("http://acm.uestc.edu.cn","/status/submit/",request);
                    }
                });
                getFragmentManager().beginTransaction().remove(this).commit();
                break;
            case R.id.jump_to_codeEditor:
                break;
        }
    }
}
