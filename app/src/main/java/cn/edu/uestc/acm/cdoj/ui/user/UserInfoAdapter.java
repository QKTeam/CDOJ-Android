package cn.edu.uestc.acm.cdoj.ui.user;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.ui.UserProblemStatusActivity;

/**
 * Created by 13662 on 2016/11/1.
 */

public class UserInfoAdapter extends RecyclerView.Adapter<UserInfoAdapter.VH> {
    private Context context;
    private User user;


    public UserInfoAdapter(Context context,User user){
        this.context = context;
        this.user = user;
    }

    @Override
    public UserInfoAdapter.VH onCreateViewHolder(final ViewGroup parent, int viewType) {
        UserInfoAdapter.VH vh = new VH(LayoutInflater.from(context).inflate(R.layout.user_info_list_item,parent,false));

        vh.showProblemsStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new VH(LayoutInflater.from(context).inflate(R.layout.user_info_list_item,parent,false));
                Intent intent = new Intent(parent.getContext(),UserProblemStatusActivity.class);
                parent.getContext().startActivity(intent);
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(UserInfoAdapter.VH holder, int position) {
        holder.nickNameDetail.setText(getInfoDetail(2));
        holder.emailDetail.setText(getInfoDetail(3));
        holder.mottoDetail.setText(getInfoDetail(4));
        holder.nameDetail.setText(getInfoDetail(5));
        holder.phoneNumberDetail.setText(getInfoDetail(6));
        holder.schoolDetail.setText(getInfoDetail(7));
        holder.studentIdDetail.setText(getInfoDetail(8));
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    private String getInfoDetail(int hint){
        String result = null;
        switch (hint){
            //case 1 : result = user.getUserName();break;
            case 2 : result = user.getNickName();break;
            case 3 : result = user.getEmail();break;
            case 4 : result = user.getMotto();break;
            case 5 : result = user.getName();break;
            case 6 : result = user.getPhone();break;
            case 7 : result = user.getSchool();break;
            case 8 : result = user.getStudentId();break;
        }
        return result;
    }

    public static class VH extends RecyclerView.ViewHolder{

        public VH(View itemView) {
            super(itemView);
        }
        TextView nickNameDetail = (TextView) itemView.findViewById(R.id.nick_name_detail);
        TextView emailDetail = (TextView) itemView.findViewById(R.id.email_detail);
        TextView mottoDetail = (TextView) itemView.findViewById(R.id.motto_detail);
        TextView nameDetail = (TextView) itemView.findViewById(R.id.name_detail);
        TextView phoneNumberDetail = (TextView) itemView.findViewById(R.id.phone_number_detail);
        TextView schoolDetail = (TextView) itemView.findViewById(R.id.school_detail);
        TextView studentIdDetail = (TextView) itemView.findViewById(R.id.student_id_detail);
        Button showProblemsStatus = (Button) itemView.findViewById(R.id.show_problems_status);

    }
}
