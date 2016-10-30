package cn.edu.uestc.acm.cdoj.ui.modules.list;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.Resource;
import cn.edu.uestc.acm.cdoj.net.ConvertNetData;
import cn.edu.uestc.acm.cdoj.net.NetDataPlus;
import cn.edu.uestc.acm.cdoj.ui.MainUIActivity;
import cn.edu.uestc.acm.cdoj.ui.contest.ContestList;
import cn.edu.uestc.acm.cdoj.ui.problem.ProblemList;
import cn.edu.uestc.acm.cdoj.ui.statusBar.FlyMeUtils;
import cn.edu.uestc.acm.cdoj.ui.statusBar.MIUIUtils;
import cn.edu.uestc.acm.cdoj.ui.statusBar.StatusBarUtil;

/**
 * Created by Great on 2016/10/12.
 */

public class SearchResultActivity extends AppCompatActivity {
    private static final String TAG = "搜索结果";
    private FloatingSearchView mSearchView;
    private int page;
    private String keyword;
    private String searchStr;
    private int startId;
    private ConvertNetData result;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        setupSystemBar();
        if (savedInstanceState == null) {
            initViews();
        }
    }

    private void setupSystemBar() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        StatusBarUtil.setStatusBarColor(this, R.color.statusBar_background_white, R.color.statusBar_background_gray, true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M || MIUIUtils.isMIUI() || FlyMeUtils.isFlyMe()) {
            StatusBarUtil.StatusBarLightMode(this);
        }
    }

    private void initViews() {
        Intent intent = getIntent();
        page = intent.getIntExtra("page", 1);
        keyword = intent.getStringExtra("keyword");
        startId = intent.getIntExtra("startId", 1);

        mSearchView = (FloatingSearchView) findViewById(R.id.search_result_floating_search_view);
        setupSearchView();
        setupResultView();
        showSearchResult(keyword);
        ((ViewGroup) findViewById(R.id.search_result_container))
                .addView((View) result);
    }

    private void setupResultView() {
        switch (page) {
            case MainUIActivity.PROBLEMLIST:
                result = new ProblemList(this);
                ((ProblemList) result).setKey(keyword);
                ((ProblemList) result).setStartId(startId);
                NetDataPlus.getProblemList(this, 1, keyword, startId, result);
                break;
            case MainUIActivity.CONTESTLIST:
                result = new ContestList(this);
                ((ContestList) result).setKey(keyword);
                ((ContestList) result).setStartId(startId);
                NetDataPlus.getContestList(this, 1, keyword, startId, result);
                break;
        }
    }


    private void setupSearchView() {
        mSearchView.setSearchHint(getString(R.string.searchWithId));

        mSearchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {
                switch (page) {
                    case MainUIActivity.PROBLEMLIST:
                        Collections.reverse(Resource.getProblemSearchHistory());
                        mSearchView.swapSuggestions(Resource.getProblemSearchHistory());
                        break;
                    case MainUIActivity.CONTESTLIST:
                        Collections.reverse(Resource.getContestSearchHistory());
                        mSearchView.swapSuggestions(Resource.getContestSearchHistory());
                }
            }

            @Override
            public void onFocusCleared() {
                mSearchView.clearQuery();
            }
        });

        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                if (!oldQuery.equals("") && newQuery.equals("")) {
                    switch (page) {
                        case MainUIActivity.PROBLEMLIST:
                            Collections.reverse(Resource.getProblemSearchHistory());
                            mSearchView.swapSuggestions(Resource.getProblemSearchHistory());
                            break;
                        case MainUIActivity.CONTESTLIST:
                            Collections.reverse(Resource.getContestSearchHistory());
                            mSearchView.swapSuggestions(Resource.getContestSearchHistory());
                    }
                } else {
                    List<SearchSuggestion> histories = null;
                    switch (page) {
                        case MainUIActivity.PROBLEMLIST:
                            histories = Resource.getProblemSearchHistory();
                            break;
                        case MainUIActivity.CONTESTLIST:
                            histories = Resource.getContestSearchHistory();
                    }
                    SearchHistoryManager.findMatchHistories(histories, newQuery, 5, new SearchHistoryManager.OnFindHistoriesListener() {
                        @Override
                        public void onResults(List<SearchSuggestion> results) {
                            Collections.reverse(results);
                            mSearchView.swapSuggestions(results);
                        }
                    });
                }
            }
        });

        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                showSearchResult(searchSuggestion.getBody());
            }

            @Override
            public void onSearchAction(String currentQuery) {
                switch (page) {
                    case MainUIActivity.PROBLEMLIST:
                        Resource.getProblemSearchHistory().add(new SearchHistory(currentQuery));
                        SearchHistoryManager.addSuggestion("problem", currentQuery);
                        break;
                    case MainUIActivity.CONTESTLIST:
                        Resource.getContestSearchHistory().add(new SearchHistory(currentQuery));
                        SearchHistoryManager.addSuggestion("contest", currentQuery);
                }
                showSearchResult(currentQuery);
            }
        });

        mSearchView.setOnBindSuggestionCallback(new SearchSuggestionsAdapter.OnBindSuggestionCallback() {
            @Override
            public void onBindSuggestion(View suggestionView, ImageView leftIcon,
                                         TextView textView, SearchSuggestion item, int itemPosition) {
                leftIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.ic_history_black_24dp, null));
                leftIcon.setAlpha(.36f);
                textView.setTextColor(Color.parseColor("#000000"));
            }

        });
    }

    private void showSearchResult(String str) {
        searchStr = str;
        if (isId(str)) {
            startId = Integer.valueOf(str.replaceFirst("#", ""));
            keyword = "";
        } else {
            keyword = str;
        }
        updateResultView();
    }

    private boolean isId(String string) {
        Pattern pattern = Pattern.compile("^#[0-9]*");
        return pattern.matcher(string).matches();
    }

    private void updateResultView() {
        mSearchView.setSearchHint(String.format(Locale.CHINA, "\"%s\" search result:", searchStr));
        switch (page) {
            case MainUIActivity.PROBLEMLIST:
                ((ProblemList)result).setStartId(startId);
                ((ProblemList)result).setKey(keyword);
                ((ProblemList)result).refresh();
                break;
            case MainUIActivity.CONTESTLIST:
                ((ContestList)result).setStartId(startId);
                ((ContestList)result).setKey(keyword);
                ((ContestList)result).refresh();
        }
    }
}
