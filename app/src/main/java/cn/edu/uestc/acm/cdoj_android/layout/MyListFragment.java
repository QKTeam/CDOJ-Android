package cn.edu.uestc.acm.cdoj_android.layout;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;


/**
 * Created by great on 2016/8/10.
 */
public class MyListFragment extends ListFragment{
    ArrayAdapter<String> adapter;
    ArrayList<String> list = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public MyListFragment createAdapter(Context context, ArrayList<String> list) {
        this.list.addAll(list);
        adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, this.list);
        setListAdapter(adapter);
        return this;
    }

    public MyListFragment createAdapter(Context context) {
        adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, this.list);
        setListAdapter(adapter);
        return this;
    }

    public void updateList(ArrayList<String> list) {
        this.list.addAll(list);
        adapter.notifyDataSetChanged();
    }

    public void addToList(String title) {
        list.add(title);
    }

    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }
}
