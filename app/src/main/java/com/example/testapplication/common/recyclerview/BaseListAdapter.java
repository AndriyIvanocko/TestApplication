package com.example.testapplication.common.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseListAdapter<M, H extends BaseListViewHolder<M>> extends RecyclerView.Adapter<H> {
    private int mSelectedIndex = -1;
    private List<M> mItems;

    public void setData(List<M> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    public List<M> getItems() {
        return mItems;
    }

    public M getItem(int index) {
        return getItems().get(index);
    }

    public int getSelectedIndex() {
        return mSelectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        notifyItemChanged(mSelectedIndex);
        mSelectedIndex = selectedIndex;
        notifyItemChanged(mSelectedIndex);
    }

    @Override
    public int getItemCount() {
        return getItems() != null ? getItems().size() : 0;
    }

    @NonNull
    @Override
    public H onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(getLayoutResource(viewType), parent, false);
        return createViewHolder(v, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull H holder, int position) {
        holder.onBind(getItem(position), position);
    }

    @LayoutRes
    protected abstract int getLayoutResource();

    protected abstract H createViewHolder(View itemView);

    protected H createViewHolder(View itemView, int viewType) {
        return createViewHolder(itemView);
    }

    protected int getLayoutResource(int viewType) {
        return getLayoutResource();
    }
}