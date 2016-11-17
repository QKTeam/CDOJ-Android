package cn.edu.uestc.acm.cdoj.ui.user;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.ui.UserProblemStatusActivity;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 13662 on 2016/11/1.
 */

public class UserInfo extends LinearLayout {
    private Toolbar toolbar;
    private TextView userName;
    private TextView nickName;
    private CircleImageView avatar;
    private User user;
    private RecyclerView recyclerView;
    private List<String> mData;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private UserInfoAdapter userInfoAdapter;

    public UserInfo(Context context,User user) {
        super(context);
        initView();
    }

    public UserInfo(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }
    private void initView(){
        inflate(getContext(), R.layout.user_info,this);
        user = new User();
        toolbar = (Toolbar) findViewById(R.id.toolbar_user_2);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout_2);
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView_2);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_2);
        userName = (TextView) findViewById(R.id.userName_2);
        nickName = (TextView) findViewById(R.id.nickName_2);
        userInfoAdapter = new UserInfoAdapter(getContext(),user);
        initData();
        setUp();
    }
    private void setUp(){
        userName.setText(user.getUserName());
        nickName.setText(user.getNickName());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(userInfoAdapter);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //appBarLayout展开时
                if (verticalOffset == 0){
                    if (state != CollapsingToolbarLayoutState.EXPANDED){
                        state = CollapsingToolbarLayoutState.EXPANDED;
                        collapsingToolbarLayout.setTitle(" ");
                    }
                }
                //appBarLayout展开后
                else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()){
                    if (state != CollapsingToolbarLayoutState.COLLAPSED){
                        state = CollapsingToolbarLayoutState.COLLAPSED;
                        collapsingToolbarLayout.setTitle("用户中心");
                    }
                }
                //appBarLayout展开过程中
                else {
                    if (state != CollapsingToolbarLayoutState.INTERMEDIATES){
                        collapsingToolbarLayout.setTitle(" ");
                        state = CollapsingToolbarLayoutState.INTERMEDIATES;
                    }
                }
            }
        });
    }

    private void initData() {
        mData = new ArrayList<String>();

        mData.add("题目完成情况");
    }

    private CollapsingToolbarLayoutState state;

    private enum CollapsingToolbarLayoutState{
        EXPANDED,
        COLLAPSED,
        INTERMEDIATES
    }
}
