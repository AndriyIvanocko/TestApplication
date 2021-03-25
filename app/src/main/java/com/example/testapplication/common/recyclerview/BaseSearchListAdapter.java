package com.example.testapplication.common.recyclerview;

import android.widget.Filter;
import android.widget.Filterable;

import java.util.List;

public abstract class BaseSearchListAdapter<V, H extends BaseListViewHolder<V>> extends BaseListAdapter<V, H>
        implements Filterable {

    private List<V> mListNotFiltered;

    @Override
    public void setData(List<V> items) {
        mListNotFiltered = items;
        super.setData(items);
    }

    public List<V> getNotFilteredList() {
        return mListNotFiltered;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                filterResults.values = getListFiltered(charSequence != null ? charSequence.toString() : null);
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                BaseSearchListAdapter.super.setData((List<V>) filterResults.values);
            }
        };
    }

    protected abstract List<V> getListFiltered(String searchQuery);

}
