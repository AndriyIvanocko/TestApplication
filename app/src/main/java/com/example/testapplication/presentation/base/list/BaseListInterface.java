package com.example.testapplication.presentation.base.list;

import android.content.Context;

import com.example.testapplication.common.recyclerview.BaseListAdapter;

public interface BaseListInterface<M> {
    void onCreateListCallback();

    BaseListAdapter<M, ?> onCreateAdapterCallback();

    Context getListContext();

    BaseListPresenter getListPresenter();
}
