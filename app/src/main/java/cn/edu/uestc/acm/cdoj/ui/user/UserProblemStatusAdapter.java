package cn.edu.uestc.acm.cdoj.ui.user;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.uestc.acm.cdoj.R;

/**
 * Created by 13662 on 2016/11/1.
 */

public class UserProblemStatusAdapter extends RecyclerView.Adapter<UserProblemStatusAdapter.VH> {
    private List<Integer> dataList;
    private Context context;
    private UserProblemsStatus status;

    public UserProblemStatusAdapter(Context context, ArrayList<Integer> data,UserProblemsStatus status) {
        this.dataList = data;
        this.context = context;
        this.status = status;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        VH vh = new VH(LayoutInflater.from(context).inflate(R.layout.user_problem_item,parent,false));
        vh.setIsRecyclable(false);
        return vh;
    }



    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.mTextView.setText(dataList.get(position)+"");
        holder.mTextView.setGravity(Gravity.CENTER);
        if ((status.getProblemsStatus(position+1)) == 1){
            holder.mTextView.setBackgroundColor(Color.GREEN);
        }
        if ((status.getProblemsStatus(position+1)) == 2){
            holder.mTextView.setBackgroundColor(Color.YELLOW);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class VH extends RecyclerView.ViewHolder {
        TextView mTextView;
        public VH(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("item", "onClick: "+ getAdapterPosition());

                }
            });
            mTextView = (TextView) itemView.findViewById(R.id.user_problem_num);
        }
    }
}
