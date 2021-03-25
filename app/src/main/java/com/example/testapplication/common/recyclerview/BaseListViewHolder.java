package com.example.testapplication.common.recyclerview;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseListViewHolder<M> extends RecyclerView.ViewHolder {
    protected Unbinder mUnbinder;

    public BaseListViewHolder(@NonNull View itemView) {
        super(itemView);
        mUnbinder = ButterKnife.bind(this, itemView);
    }

    protected abstract void onBind(M item, int position);


    public Context getContext() {
        return itemView.getContext();
    }
}
