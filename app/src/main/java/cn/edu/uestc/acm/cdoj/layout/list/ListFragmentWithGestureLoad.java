/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.edu.uestc.acm.cdoj.layout.list;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Map;

import cn.edu.uestc.acm.cdoj.R;
import cn.edu.uestc.acm.cdoj.net.data.PageInfo;


public class ListFragmentWithGestureLoad extends Fragment {
    final private Handler mHandler = new Handler();

    final private Runnable mRequestFocus = new Runnable() {
        public void run() {
            mList.focusableViewAvailable(mList);
        }
    };

    final private AdapterView.OnItemClickListener mOnClickListener
            = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            onListItemClick((ListView) parent, v, position, id);
        }
    };

    ListAdapter mAdapter;
    PullUpLoadListView mList;
    SwipeRefreshLayout mSwipeRefresh;
    View mEmptyView;
    TextView mStandardEmptyView;
    View mProgressContainer;
    View mListContainer;
    CharSequence mEmptyText;
    boolean mListShown;
    Context context;
    View rootView;
    PageInfo pageInfo;
    PullUpLoadListView.OnPullUpLoadListener onPullUpLoadListener;
    SwipeRefreshLayout.OnRefreshListener onRefreshListener;

    public ListFragmentWithGestureLoad() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    final public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                   Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            rootView = inflater.inflate(R.layout.list, container, false);
        }
        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ensureList();
    }


    @Override
    public void onDestroyView() {
        mHandler.removeCallbacks(mRequestFocus);
        mList = null;
        mListShown = false;
        mEmptyView = mProgressContainer = mListContainer = null;
        mStandardEmptyView = null;
        super.onDestroyView();
    }



    public void onListItemClick(ListView l, View v, int position, long id) {
    }


    public void setListAdapter(ListAdapter adapter) {
        boolean hadAdapter = mAdapter != null;
        mAdapter = adapter;
        if (mList != null) {
            mList.setAdapter(adapter);
            if (!mListShown && !hadAdapter) {
                // The list was hidden, and previously didn't have an
                // adapter.  It is now time to show it.
                setListShown(true, rootView.getWindowToken() != null);
            }
        }
    }


    public void setSelection(int position) {
        ensureList();
        mList.setSelection(position);
    }


    public int getSelectedItemPosition() {
        ensureList();
        return mList.getSelectedItemPosition();
    }


    public long getSelectedItemId() {
        ensureList();
        return mList.getSelectedItemId();
    }


    public PullUpLoadListView getListView() {
        return mList;
    }


    public void setEmptyText(CharSequence text) {
        ensureList();
        if (mStandardEmptyView == null) {
            throw new IllegalStateException("Can't be used with a custom content view");
        }
        mStandardEmptyView.setText(text);
        if (mEmptyText == null) {
            mList.setEmptyView(mStandardEmptyView);
        }
        mEmptyText = text;
    }


    public void setListShown(boolean shown) {
        setListShown(shown, true);
    }


    public void setListShownNoAnimation(boolean shown) {
        setListShown(shown, false);
    }


    private void setListShown(boolean shown, boolean animate) {
        ensureList();
        if (mProgressContainer == null) {
            throw new IllegalStateException("Can't be used with a custom content view");
        }
        if (mListShown == shown) {
            return;
        }
        mListShown = shown;
        if (shown) {
            if (animate) {
                mProgressContainer.startAnimation(AnimationUtils.loadAnimation(
                        getActivity(), android.R.anim.fade_out));
                mListContainer.startAnimation(AnimationUtils.loadAnimation(
                        getActivity(), android.R.anim.fade_in));
            } else {
                mProgressContainer.clearAnimation();
                mListContainer.clearAnimation();
            }
            mProgressContainer.setVisibility(View.GONE);
            mListContainer.setVisibility(View.VISIBLE);
        } else {
            if (animate) {
                mProgressContainer.startAnimation(AnimationUtils.loadAnimation(
                        getActivity(), android.R.anim.fade_in));
                mListContainer.startAnimation(AnimationUtils.loadAnimation(
                        getActivity(), android.R.anim.fade_out));
            } else {
                mProgressContainer.clearAnimation();
                mListContainer.clearAnimation();
            }
            mProgressContainer.setVisibility(View.VISIBLE);
            mListContainer.setVisibility(View.GONE);
        }
    }


    public ListAdapter getListAdapter() {
        return mAdapter;
    }

    private void ensureList() {
        if (mList != null) {
            return;
        }
        mSwipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.listSwipeRefresh);
        if (onRefreshListener != null) {
            mSwipeRefresh.setOnRefreshListener(onRefreshListener);
        }
        mStandardEmptyView = (TextView) rootView.findViewById(R.id.internalEmpty);
        if (mStandardEmptyView == null) {
            mEmptyView = rootView.findViewById(android.R.id.empty);
        } else {
            mStandardEmptyView.setVisibility(View.GONE);
        }
        mProgressContainer = rootView.findViewById(R.id.progressContainer);
        mListContainer = rootView.findViewById(R.id.listContainer);
        mList = (PullUpLoadListView) rootView.findViewById(R.id.list);
        if (onPullUpLoadListener != null) {
            mList.setOnPullUpLoadListener(onPullUpLoadListener);
        }
        if (mEmptyView != null) {
            mList.setEmptyView(mEmptyView);
        } else if (mEmptyText != null) {
            mStandardEmptyView.setText(mEmptyText);
            mList.setEmptyView(mStandardEmptyView);
        }
        mListShown = true;
        mList.setOnItemClickListener(mOnClickListener);
        if (mAdapter != null) {
            ListAdapter adapter = mAdapter;
            mAdapter = null;
            setListAdapter(adapter);
        } else {
            // We are starting without an adapter, so assume we won't
            // have our data right away and start with the progress indicator.
            if (mProgressContainer != null) {
                setListShown(false, false);
            }
        }
        mHandler.post(mRequestFocus);
    }


    public  void notifyDataSetChanged() {
        if (mSwipeRefresh != null && mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(false);
        }
        if (mList != null  && mList.isPullUpLoading()) {
            mList.finishAddData();
        }
    }

    public void addListItem(Map<String, Object> listItem) {
    }

    public void setOnPullUpLoadListener(PullUpLoadListView.OnPullUpLoadListener listener) {
        if (mList != null) {
            mList.setOnPullUpLoadListener(listener);
            return;
        }
        onPullUpLoadListener = listener;
    }

    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) {
        if (mSwipeRefresh != null) {
            mSwipeRefresh.setOnRefreshListener(listener);
            return;
        }
        onRefreshListener = listener;
    }

    public void stopPullUpLoad() {
        if (mList != null) {
            mList.pullUpLoadFinish();
        }
    }

    public void continuePullUpLoad() {
        if (mList != null) {
            mList.hasMoreData();
        }
    }

    public void getDataFailure() {
        if (mList != null) {
            mList.getDataFailure();
        }
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

}
