package cn.edu.uestc.acm.cdoj.ui.modules.list;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import android.widget.Filter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import cn.edu.uestc.acm.cdoj.ui.modules.Global;

/**
 * Created by Grea on 2016/10/13.
 */

public class SearchHistoryManager {

    public static void deleteSuggestionFile(String type) {
        File file = new File(Global.filesDirPath + type + "Suggestion");
        file.delete();
    }

    public static void addSuggestion(String type, ArrayList<SearchHistory> searchHistoryList, boolean clearHistory) {
        File file = new File(Global.filesDirPath + type + "Suggestion");
        if (clearHistory) {
            file.delete();
        }
        try {
            if (!file.exists()) {
                if (!file.createNewFile()) return;
            }
            FileWriter writer = new FileWriter(file);
            for (SearchHistory searchHistory : searchHistoryList) {
                writer.write(searchHistory.getBody() + "\n");
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addSuggestion(String type, String searchString) {
        File file = new File(Global.filesDirPath + type + "Suggestion");
        try {
            if (!file.exists()) {
                if (!file.createNewFile()) return;
            }
            FileWriter writer = new FileWriter(file);
            writer.write(searchString + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<SearchHistory> getAllHistories(String type) {
        return getHistories(type, 10);
    }

    public static ArrayList<SearchHistory> getHistories(String type, int count) {
        if (count > 10) count = 10;
        ArrayList<SearchHistory> suggestionList = new ArrayList<>();
        File file = new File(Global.filesDirPath + type + "Suggestion");
        if (!file.exists()) return suggestionList;
        try {
            Scanner input = new Scanner(file);
            for (int i = 0; i < count && input.hasNextLine(); ++i) {
                suggestionList.add(new SearchHistory(input.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return suggestionList;
    }

    public static void findMatchHistories(final ArrayList<SearchHistory> searchHistories, final String str,
                                          final int maxCount, final OnFindHistoriesListener listener) {
        new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ArrayList<SearchHistory> result = new ArrayList<>();
                for (SearchHistory searchHistory : searchHistories) {
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
                    listener.onResults((ArrayList<SearchHistory>)results.values);
                }
            }
        }.filter(str);
    }

    public interface OnFindHistoriesListener {
        void onResults(ArrayList<SearchHistory> results);
    }
}
