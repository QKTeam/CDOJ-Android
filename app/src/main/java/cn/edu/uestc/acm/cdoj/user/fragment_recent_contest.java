package cn.edu.uestc.acm.cdoj.user;

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

public class fragment_recent_contest extends GeneralFragment implements ReceivedCallback<List<RecentContestListItem>>{

    public fragment_recent_contest(Context context, String type) {
        super(context, type);
    }

    @Override
    public void onDataReceived(List<RecentContestListItem> recentContestListItems) {

    }

    @Override
    public void onLoginDataReceived(ContentReceived dataReceived) {

    }
}
