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
public class ArticleListFragment extends ListFragment {
    SimpleAdapter adapter;
    ArrayList<Map<String,String>> listItems;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    public ArticleListFragment createAdapter(Context context) {
        listItems = new ArrayList<>();
        adapter = new SimpleAdapter(context, listItems, R.layout.article_list_item,
                new String[]{"title", "content", "releaseTime", "author"},
                new int[]{R.id.article_title, R.id.article_content, R.id.article_releaseTime, R.id.article_author});
        setListAdapter(adapter);
        return this;
    }

    public void addToList(String title, String content, String releaseTime, String author) {
        Map<String,String> listItem = new HashMap<>();
        listItem.put("title",title);
        listItem.put("content", content);
        listItem.put("releaseTime", releaseTime);
        listItem.put("author", author);
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
