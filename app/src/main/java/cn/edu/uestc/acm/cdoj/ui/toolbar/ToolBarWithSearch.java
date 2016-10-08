package cn.edu.uestc.acm.cdoj.ui.toolbar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;

/**
 * Created by fitzz on 16-9-5.
 */
public class ToolBarWithSearch extends Toolbar {


    private SearchView searchView;
    private boolean isShowSearchView;
    OnSearchListener listener;
    OnSearchCloseListener closeListener;


    public interface OnSearchListener {
        void showSearchUI();
        void search(String key);
    }

    public interface OnSearchCloseListener{
        void recovery();
    }

    public ToolBarWithSearch(Context context) {
        super(context);
    }

    public ToolBarWithSearch(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ToolBarWithSearch(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnSearchListener(OnSearchListener listener) {
        this.listener = listener;
    }

    public void setOnCloseListener(OnSearchCloseListener closeListener){this.closeListener = closeListener;}

    public void setSearchEnable(boolean change){
        if (change && !isShowSearchView){
            addSearchView();
            isShowSearchView = true;
            return;
        }
        if (!change && isShowSearchView) {
            removeAllViews();
            isShowSearchView = false;
        }
    }

    public void addSearchView(){
        searchView = new SearchView(getContext());
        searchView.setSubmitButtonEnabled(true);
        searchView.setLayoutParams(new LayoutParams(Gravity.RIGHT));
        searchView.setMaxWidth(1000);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (listener != null) {
                    listener.search(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                if (closeListener != null) {
                    closeListener.recovery();
                }
                return false;
            }
        });
        this.addView(searchView);
    }
}
