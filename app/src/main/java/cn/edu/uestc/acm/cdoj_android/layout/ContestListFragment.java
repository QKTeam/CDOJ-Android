package cn.edu.uestc.acm.cdoj_android.layout;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.edu.uestc.acm.cdoj_android.R;

/**
 * Created by great on 2016/8/17.
 */
public class ContestListFragment extends ListFragment {
    SimpleAdapter adapter;
    ArrayList<Map<String,String>> listItems;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    public ContestListFragment createAdapter(Context context) {
        listItems = new ArrayList<>();
        adapter = new SimpleAdapter(context, listItems, R.layout.contest_list_item,
                new String[]{"title", "releaseTime", "timeLimit", "id", "state", "permissions"},
                new int[]{R.id.contest_title, R.id.contest_releaseTime, R.id.contest_timeLimit, R.id.contest_id, R.id.contest_state, R.id.contest_permissions});
        setListAdapter(adapter);
        return this;
    }

    public void addToList(String title, String releaseTime, String timeLimit, String id, String state, String permissions) {
        Map<String,String> listItem = new HashMap<>();
        listItem.put("title",title);
        listItem.put("releaseTime", releaseTime);
        listItem.put("timeLimit", timeLimit);
        listItem.put("id", id);
        listItem.put("state", state);
        listItem.put("permissions", permissions);
        listItems.add(listItem);
    }

    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Log.d("onListItemClick:",""+position+"  "+id );
    }
}
