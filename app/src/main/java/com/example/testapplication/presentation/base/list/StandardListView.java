package com.example.testapplication.presentation.base.list;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.example.testapplication.R;


public class StandardListView extends RelativeLayout {
    private RecyclerView mRecyclerView;

    public StandardListView(Context context) {
        super(context);
        init(context, null);
    }

    public StandardListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public StandardListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_standard_list, this, true);
        mRecyclerView = findViewById(R.id.recycler_base_list);
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }
}
