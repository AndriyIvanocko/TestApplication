package com.example.testapplication.common.recyclerview;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BasePaginationAdapter<M, H extends BasePaginationViewHolder<M>> extends BaseSearchListAdapter<M, H> {
    private PaginationAdapterListener mPaginationAdapterListener;
    private boolean mIsLoading;
    private int mVisibleThreshold = 3;
    private int mLastVisibleItem, mTotalItemCount;
    private int mLastSize;
    private boolean mCanLoadMore = true;

    public BasePaginationAdapter() {
    }

    public BasePaginationAdapter(final RecyclerView recyclerView, final PaginationAdapterListener paginationAdapterListener) {
        setPaginationData(recyclerView, paginationAdapterListener);
    }

    @Override
    protected List<M> getListFiltered(String searchQuery) {
        return null;
    }

    public void setPaginationData(final RecyclerView recyclerView, final PaginationAdapterListener paginationAdapterListener) {
        mPaginationAdapterListener = paginationAdapterListener;
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        if (linearLayoutManager == null) {
            throw new IllegalArgumentException("You should set the LayoutManager to recyclerview before setting pagination data.");
        }
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mTotalItemCount = linearLayoutManager.getItemCount();
                mLastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!mIsLoading && mTotalItemCount <= (mLastVisibleItem + mVisibleThreshold)) {
                    mPaginationAdapterListener.onLoadMore();
                    mIsLoading = true;
                }
            }
        });
    }

    @Override
    public void setData(List<M> items) {
        mIsLoading = false;
        if (items.size() == mLastSize) {
            mCanLoadMore = false;
        }
        mLastSize = items.size();
        super.setData(items);
    }

    public boolean canLoadMore() {
        return mCanLoadMore;
    }

    public interface PaginationAdapterListener {
        void onLoadMore();
    }
}
