package com.example.testapplication.presentation.base.list;


import com.example.testapplication.presentation.base.view.BaseView;

import java.util.List;

public interface BaseListView<M, P extends BaseListPresenter> extends BaseView<P> {
    void setData(List<M> data);

    void notifyItemChanged(int index);
}
