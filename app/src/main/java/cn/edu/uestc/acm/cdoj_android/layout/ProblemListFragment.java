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
public class ProblemListFragment extends ListFragment {
    SimpleAdapter adapter;
    ArrayList<Map<String,String>> listItems;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    public ProblemListFragment createAdapter(Context context) {
        listItems = new ArrayList<>();
        adapter = new SimpleAdapter(context, listItems, R.layout.article_list_item,
                new String[]{"title", "source", "id", "number"},
                new int[]{R.id.problem_title, R.id.problem_source, R.id.problem_id, R.id.problem_number});
        setListAdapter(adapter);
        return this;
    }

    public void addToList(String title, String source, String id, String number) {
        Map<String,String> listItem = new HashMap<>();
        listItem.put("title",title);
        listItem.put("source", source);
        listItem.put("id", id);
        listItem.put("number", number);
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
