package com.example.testapplication.presentation.base.list;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.testapplication.common.recyclerview.BaseListAdapter;
import com.example.testapplication.presentation.base.view.fragment.BaseFragment;

import java.util.List;

public abstract class BaseListFragment<M, P extends BaseListPresenter> extends BaseFragment<P> implements BaseListView<M, P>, BaseListInterface<M> {
    private BaseListFragmentComposition mComposition = new BaseListFragmentComposition(this);

    /**
     * Creates the list from a StandardListView
     *
     * @param standardListViewId - id of your StandardListView
     * @param useSwipeAsProgress - true if you want to show swipe circle instead of progress dialog.
     * @param enableSwipeRefresh - if the swipe refresh by dragging your finger is enabled. Makes
     *                           effect only if useSwipeAsProgress = true.
     * @param placeHolderText    - the text of the placeholder.
     * @param usePagination      - do we use pagination manager when making requests.
     */
    protected void createStandardList(final int standardListViewId,
                                      final boolean useSwipeAsProgress,
                                      final boolean enableSwipeRefresh,
                                      final String placeHolderText,
                                      final boolean usePagination) {
        mComposition.createStandardList(
                standardListViewId,
                useSwipeAsProgress,
                enableSwipeRefresh,
                placeHolderText,
                usePagination);
    }

    /**
     * Creates the list from your own views.
     *
     * @param customRecyclerViewId - id of your RecyclerView
     * @param swipeLayoutId        - id of your SwipeRefreshLayout. -1 you do not want to use it.
     * @param placeHolderTextId    - id of the TextView of your placeholder. -1 if you do not want
     *                             to use placeholders.
     * @param enableSwipeRefresh   - if the swipe refresh by dragging your finger is enabled. Makes
     *                             effect only if swipeLayoutId != 0.
     * @param usePagination        - do we use pagination manager when making requests.
     */
    protected void createCustomList(final int customRecyclerViewId,
                                    final int swipeLayoutId,
                                    final int placeHolderTextId,
                                    final boolean enableSwipeRefresh,
                                    final boolean usePagination) {
        mComposition.createCustomList(
                customRecyclerViewId,
                swipeLayoutId,
                placeHolderTextId,
                enableSwipeRefresh,
                usePagination);
    }

    @CallSuper
    @Override
    protected void setupView(Bundle savedInstanceState) {
        mComposition.setupView(mRootView);
    }

    @Override
    public void setData(List<M> data) {
        mComposition.setData(data);
    }

    @Override
    public void notifyItemChanged(int index) {
        mComposition.notifyItemChanged(index);
    }

    @Override
    public void showProgress(boolean visible) {
        if (!mComposition.showProgress(visible)) {
            super.showProgress(visible);
        }
    }

    protected RecyclerView getRecycler() {
        return mComposition.mRecycler;
    }

    protected BaseListAdapter getAdapter() {
        return mComposition.mAdapter;
    }

    protected SwipeRefreshLayout getSwipeLayout() {
        return mComposition.mSwipeLayout;
    }

    protected void showPlaceHolder(boolean visible) {
        mComposition.showPlaceHolder(visible);
    }

    @Override
    public BaseListPresenter getListPresenter() {
        return getPresenter();
    }

    @Override
    public Context getListContext() {
        return getContext();
    }
}
