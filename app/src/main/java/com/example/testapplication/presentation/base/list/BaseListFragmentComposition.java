package com.example.testapplication.presentation.base.list;

import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.testapplication.R;
import com.example.testapplication.common.recyclerview.BaseListAdapter;
import com.example.testapplication.common.recyclerview.BasePaginationAdapter;
import com.example.testapplication.presentation.base.view.activity.BaseActivity;

import java.util.List;

public class BaseListFragmentComposition<M> implements BasePaginationAdapter.PaginationAdapterListener {
    private boolean mStandartListCalled = false;
    private boolean mCustomListCalled = false;

    private BaseListInterface mFragment;
    private BaseListPresenter mPresenter;

    int mStandardListViewId;
    boolean mUseSwipeAsProgress;
    boolean mEnableSwipeRefresh;
    String mPlaceHolderText;
    boolean mUsePagination;
    int mCustomRecyclerViewId;
    int mSwipeLayoutId;
    int mPlaceHolderTextId;

    RecyclerView mRecycler;
    SwipeRefreshLayout mSwipeLayout;
    AppCompatTextView mTextPlaceholder;
    BaseListAdapter<M, ?> mAdapter;

    public BaseListFragmentComposition(BaseListInterface fragment) {
        mFragment = fragment;
    }

    void showPlaceHolder(boolean visible) {
        mTextPlaceholder.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    protected void createStandardList(final int standardListViewId,
                                      final boolean useSwipeAsProgress,
                                      final boolean enableSwipeRefresh,
                                      final String placeHolderText,
                                      final boolean usePagination) {
        if (standardListViewId != BaseActivity.INVALID_RES_ID) {
            mStandardListViewId = standardListViewId;
            mUseSwipeAsProgress = useSwipeAsProgress;
            mEnableSwipeRefresh = enableSwipeRefresh;
            mPlaceHolderText = placeHolderText;
            mUsePagination = usePagination;
            mCustomRecyclerViewId = BaseActivity.INVALID_RES_ID;
            mSwipeLayoutId = BaseActivity.INVALID_RES_ID;
            mPlaceHolderTextId = BaseActivity.INVALID_RES_ID;

            mStandartListCalled = true;
        }
    }


    protected void createCustomList(final int customRecyclerViewId,
                                    final int swipeLayoutId,
                                    final int placeHolderTextId,
                                    final boolean enableSwipeRefresh,
                                    final boolean usePagination) {
        if (customRecyclerViewId != BaseActivity.INVALID_RES_ID) {
            mStandardListViewId = BaseActivity.INVALID_RES_ID;
            mUseSwipeAsProgress = swipeLayoutId != BaseActivity.INVALID_RES_ID;
            mEnableSwipeRefresh = enableSwipeRefresh;
            mPlaceHolderText = mPlaceHolderTextId != BaseActivity.INVALID_RES_ID ? "" : null;
            mUsePagination = usePagination;
            mCustomRecyclerViewId = customRecyclerViewId;
            mSwipeLayoutId = swipeLayoutId;
            mPlaceHolderTextId = placeHolderTextId;

            mCustomListCalled = true;
        }
    }

    protected void setupView(View mRootView) {
        mPresenter = mFragment.getListPresenter();

        mStandartListCalled = false;
        mCustomListCalled = false;

        mFragment.onCreateListCallback();

        if (!mStandartListCalled) {
            createStandardList(BaseActivity.INVALID_RES_ID,
                    false,
                    false,
                    null,
                    false);
        }

        if (!mCustomListCalled) {
            createCustomList(BaseActivity.INVALID_RES_ID,
                    BaseActivity.INVALID_RES_ID,
                    BaseActivity.INVALID_RES_ID,
                    false,
                    false);
        }
        if (!mStandartListCalled && !mCustomListCalled) {
            throw new IllegalStateException("There are no lists created. To use a standard list, you should call createStandardList() in onCreateListCallback()." +
                    "Otherwise if you want to use your custom list - you should call createCustomList() in onCreateCustomListCallback() method.");
        }

        if (mCustomRecyclerViewId == BaseActivity.INVALID_RES_ID) {
            //USED StandardListView
            mRecycler = mRootView.findViewById(R.id.recycler_base_list);
            mSwipeLayout = mRootView.findViewById(R.id.swipe_layout);
            mTextPlaceholder = mRootView.findViewById(R.id.text_empty_list);
        } else {
            //USED Custom RecyclerView
            mRecycler = mRootView.findViewById(mCustomRecyclerViewId);
            if (mSwipeLayoutId != BaseActivity.INVALID_RES_ID)
                mSwipeLayout = mRootView.findViewById(mSwipeLayoutId);
            if (mPlaceHolderTextId != BaseActivity.INVALID_RES_ID)
                mTextPlaceholder = mRootView.findViewById(mPlaceHolderTextId);
        }

        mAdapter = mFragment.onCreateAdapterCallback();
        setupAdapter();
        setupSwipe();
        setupPlaseholder();
    }

    public void setData(List<M> data) {
        mAdapter.setData(data);
        if (mTextPlaceholder != null && mPlaceHolderText != null) {
            mTextPlaceholder.setVisibility(data.isEmpty() ? View.VISIBLE : View.GONE);
        }
    }

    public void notifyItemChanged(int index) {
        mAdapter.notifyItemChanged(index);
    }

    private void setupAdapter() {
        mPresenter.setUsePagination(mUsePagination);
        mRecycler.setLayoutManager(new LinearLayoutManager(mFragment.getListContext(), RecyclerView.VERTICAL, false));
        if (mUsePagination) {
            if (!(mAdapter instanceof BasePaginationAdapter)) {
                throw new IllegalArgumentException("When using pagination - adapter must extend BasePaginationAdapter.");
            }
            ((BasePaginationAdapter) mAdapter).setPaginationData(mRecycler, this);
        }
        mRecycler.setAdapter(mAdapter);
    }

    private void setupSwipe() {
        if (mSwipeLayout != null) {
            //TODO set swipe color
            //mSwipeLayout.setColorSchemeColors(ContextCompat.getColor(mFragment.getListContext(), R.color.green_dark));
            if (mEnableSwipeRefresh) {
                mSwipeLayout.setOnRefreshListener(this::onSwipeRefressh);
            } else {
                mSwipeLayout.setEnabled(false);
            }
        }
    }

    private void setupPlaseholder() {
        if (mTextPlaceholder != null && mPlaceHolderText != null && !mPlaceHolderText.isEmpty()) {
            mTextPlaceholder.setText(mPlaceHolderText);
        }
    }

    public boolean showProgress(boolean visible) {
        if (mUseSwipeAsProgress && mSwipeLayout != null) {
            mSwipeLayout.setRefreshing(visible);
            return true;
        } else {
            return false;
        }
    }

    protected void onSwipeRefressh() {
        mPresenter.startLoading();
    }

    @Override
    public void onLoadMore() {
        mPresenter.performDataRequestCallback();
    }
}
