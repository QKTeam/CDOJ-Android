package cn.edu.uestc.acm.cdoj.ui.user;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.edu.uestc.acm.cdoj.R;

/**
 * Created by 13662 on 2016/11/1.
 */

public class UserInfoAdapter extends RecyclerView.Adapter<UserInfoAdapter.VH> {
    private Context context;
    private List data;
    private User user;

    public UserInfoAdapter(Context context,User user){
        this.context = context;
        this.user = user;
    }

    @Override
    public UserInfoAdapter.VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(context).inflate(R.layout.user_info_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(UserInfoAdapter.VH holder, int position) {
        holder.hint.setText("hint");
        holder.detail.setText("detail");
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public static class VH extends RecyclerView.ViewHolder{

        public VH(View itemView) {
            super(itemView);
        }
        TextView hint = (TextView) itemView.findViewById(R.id.userInfoHint);
        TextView detail = (TextView) itemView.findViewById(R.id.userInfoDetail);
    }
}
