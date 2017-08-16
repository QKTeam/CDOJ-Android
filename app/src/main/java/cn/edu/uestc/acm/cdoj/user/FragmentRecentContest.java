package cn.edu.uestc.acm.cdoj.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.genaralData.ContentReceived;
import cn.edu.uestc.acm.cdoj.genaralData.GeneralFragment;
import cn.edu.uestc.acm.cdoj.net.Connection;
import cn.edu.uestc.acm.cdoj.net.ReceivedCallback;
import cn.edu.uestc.acm.cdoj.net.homePage.RecentContestListItem;

/**
 * Created by lagranmoon on 2017/8/10.
 * method:get
 * url: http://acm.uestc.edu.cn/recentContest
 */

public class FragmentRecentContest extends GeneralFragment implements ReceivedCallback<List<RecentContestListItem>>{

    @SuppressLint("ValidFragment")
    public FragmentRecentContest(Context context, String type) {
        super(context, type);
    }

    public FragmentRecentContest() {
    }

    @Override
    public void onDataReceived(List<RecentContestListItem> recentContestListItems) {

    }

    @Override
    public void onLoginDataReceived(ContentReceived dataReceived) {

    }
}
