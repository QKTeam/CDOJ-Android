package cn.edu.uestc.acm.cdoj.ui.toolbar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;

/**
 * Created by fitzz on 16-9-5.
 */
public class ToolBarWithTab extends Toolbar {

    private TabLayout tabLayout;
    private int toolBarTabPosition = 0;
    private String[] title = new String[]{"Overview","Problems","Clarification","Status","Print","Rank"};

    public ToolBarWithTab(Context context) {
        super(context);
    }

    public ToolBarWithTab(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        tabLayout = new TabLayout(getContext());
        for (int i = 0;i < title.length;i++) {
            tabLayout.addTab(tabLayout.newTab().setText(title[i]));
        }
        tabLayout.setLayoutParams(new LayoutParams(Gravity.CENTER));
        tabLayout.setMinimumWidth(400);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                toolBarTabPosition = tab.getPosition();
                Log.d("ToolBarTab", "onClick: "+tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        this.addView(tabLayout);
    }

    public ToolBarWithTab(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public  int getToolBarTabSelected(){
        return toolBarTabPosition;
    }
}