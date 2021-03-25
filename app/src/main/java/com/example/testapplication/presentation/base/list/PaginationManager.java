package com.example.testapplication.presentation.base.list;

public class PaginationManager {
    public static final int PAGINATION_START_POSITION = 0;
    private boolean mCanloadMore = true;
    private int mNextPage = PAGINATION_START_POSITION;

    public boolean canLoadMore() {
        return mCanloadMore;
    }

    public int getNextPage() {
        return mNextPage;
    }

    public void reset() {
        mCanloadMore = true;
        mNextPage = PAGINATION_START_POSITION;
    }

    public void onDataLoaded(int currentPage, int loadedItemsSize) {
        mNextPage = currentPage;
        if (loadedItemsSize == 0) {
            mCanloadMore = false;
        } else {
            mCanloadMore = true;
            mNextPage++;
        }
    }
}
