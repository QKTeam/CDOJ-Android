package cn.edu.uestc.acm.cdoj.ui.modules.list;

import android.os.Parcel;

/**
 * Created by Grea on 2016/10/13.
 */

public class SearchHistory implements com.arlib.floatingsearchview.suggestions.model.SearchSuggestion {

    private String mSearchSuggestion;
    private boolean mIsHistory = false;

    public SearchHistory(String suggestion) {
        this.mSearchSuggestion = suggestion.toLowerCase();
    }

    public SearchHistory(Parcel source) {
        this.mSearchSuggestion = source.readString();
        this.mIsHistory = source.readInt() != 0;
    }

    public void setIsHistory(boolean isHistory) {
        this.mIsHistory = isHistory;
    }

    public boolean getIsHistory() {
        return this.mIsHistory;
    }

    @Override
    public String getBody() {
        return mSearchSuggestion;
    }

    public static final Creator<SearchHistory> CREATOR = new Creator<SearchHistory>() {
        @Override
        public SearchHistory createFromParcel(Parcel in) {
            return new SearchHistory(in);
        }

        @Override
        public SearchHistory[] newArray(int size) {
            return new SearchHistory[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mSearchSuggestion);
        dest.writeInt(mIsHistory ? 1 : 0);
    }
}
