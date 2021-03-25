package com.example.testapplication.common.recyclerview;

import android.content.Context;
import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BasePaginationViewHolder<M> extends BaseListViewHolder<M> {
    protected Unbinder mUnbinder;
    protected BasePaginationAdapter mAdapter;

    public BasePaginationViewHolder(BasePaginationAdapter adapter, @NonNull View itemView) {
        super(itemView);
        mUnbinder = ButterKnife.bind(this, itemView);
        mAdapter = adapter;
    }

    @CallSuper
    protected void onBind(M item, int position) {
        if (getLoadMoreIndicator() != null) {
            if (position == mAdapter.getItemCount() - 1 && mAdapter.canLoadMore()) {
                getLoadMoreIndicator().setVisibility(View.VISIBLE);
            } else {
                getLoadMoreIndicator().setVisibility(View.GONE);
            }
        }
    }

    protected abstract View getLoadMoreIndicator();

    public Context getContext() {
        return itemView.getContext();
    }
}
