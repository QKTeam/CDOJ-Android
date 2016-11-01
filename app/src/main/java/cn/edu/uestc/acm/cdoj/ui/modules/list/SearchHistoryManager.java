package cn.edu.uestc.acm.cdoj.ui.modules.list;

import android.util.Log;
import android.widget.Filter;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import cn.edu.uestc.acm.cdoj.Resource;


/**
 * Created by Grea on 2016/10/13.
 */

public class SearchHistoryManager {

    static String TAG = "历史记录";

    public static void deleteSuggestionFile(String type) {
        File file = new File(Resource.getFilesDirPath() + type + "Suggestion");
        file.delete();
    }

    public static void addSuggestion(String type, List<SearchSuggestion> searchHistoryList, boolean clearHistory) {
        File file = new File(Resource.getFilesDirPath() + type + "Suggestion");
        if (clearHistory) {
            file.delete();
        }
        try {
            if (!file.exists()) {
                if (!file.createNewFile()) return;
            }
            FileWriter writer = new FileWriter(file);
            for (SearchSuggestion searchHistory : searchHistoryList) {
                writer.write(searchHistory.getBody() + "\n");
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addSuggestion(String type, String searchString) {
        File file = new File(Resource.getFilesDirPath() + type + "Suggestion");
        try {
            if (!file.exists()) {
                if (!file.createNewFile()) return;
            }
            FileWriter writer = new FileWriter(file);
            writer.write(searchString + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<SearchSuggestion> getAllHistories(String type) {
        List<SearchSuggestion> suggestionList = new ArrayList<>();
        File file = new File(Resource.getFilesDirPath() + type + "Suggestion");
        if (!file.exists()) return suggestionList;
        try {
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) {
                String tem = input.nextLine();
                Log.d(TAG, "getAllHistories: " + tem);
                suggestionList.add(new SearchHistory(tem));
            }
            input.close();
            if (suggestionList.size() > 10) {
                List<SearchSuggestion> tem = suggestionList;
                suggestionList = new ArrayList<>();
                for (int i = tem.size()-10; i < tem.size(); i++) {
                    suggestionList.add(tem.get(i));
                }
                addSuggestion(type, suggestionList, true);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return suggestionList;
    }

    public static void findMatchHistories(final List<SearchSuggestion> searchHistories, final String str,
                                          final int maxCount, final OnFindHistoriesListener listener) {
        new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<SearchSuggestion> result = new ArrayList<>();
                for (SearchSuggestion searchHistory : searchHistories) {
                    if (searchHistory.getBody().startsWith(str)) {
                        result.add(searchHistory);
                        if (result.size() >= maxCount) {
                            break;
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = result;
                filterResults.count = result.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (listener != null) {
                    listener.onResults((List<SearchSuggestion>)results.values);
                }
            }
        }.filter(str);
    }

    public interface OnFindHistoriesListener {
        void onResults(List<SearchSuggestion> results);
    }
}
