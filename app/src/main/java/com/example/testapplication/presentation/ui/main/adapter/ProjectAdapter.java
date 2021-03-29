package com.example.testapplication.presentation.ui.main.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.example.testapplication.R;
import com.example.testapplication.common.recyclerview.BaseListAdapter;
import com.example.testapplication.common.recyclerview.BaseListViewHolder;
import com.example.testapplication.domain.model.project.ProjectModel;

import java.util.Locale;

import butterknife.BindView;

public class ProjectAdapter extends BaseListAdapter<ProjectModel, ProjectAdapter.ProjectViewHolder> {
    private final OnProjectClickListener projectClickListener;

    public ProjectAdapter(OnProjectClickListener projectClickListener) {
        this.projectClickListener = projectClickListener;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.item_project;
    }

    @Override
    protected ProjectViewHolder createViewHolder(View itemView) {
        return new ProjectViewHolder(itemView);
    }

    public void updateName(String name) {
        if (getSelectedIndex() > -1) {
            getItem(getSelectedIndex()).setName(name);
            setSelectedIndex(getSelectedIndex());
        }
    }

    protected class ProjectViewHolder extends BaseListViewHolder<ProjectModel> {

        @BindView(R.id.image_project)
        AppCompatImageView mProjectImage;
        @BindView(R.id.project_title)
        AppCompatTextView mProjectTitle;
        @BindView(R.id.worker_count)
        AppCompatTextView mWorkerCount;

        public ProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> {
                setSelectedIndex(getAdapterPosition());
                projectClickListener.onPressed(getItem(getAdapterPosition()));
            });
        }

        @Override
        protected void onBind(ProjectModel item, int position) {
            mProjectTitle.setText(item.getName());
            if (!item.getUsers().isEmpty())
                mWorkerCount.setText(String.format(Locale.getDefault(), "%d workers", item.getUsers().size()));
            else
                mWorkerCount.setText("");
            Glide.with(itemView)
                    .load(item.getLogoUrl())
                    .into(mProjectImage);
        }
    }

    public interface OnProjectClickListener {
        void onPressed(ProjectModel item);
    }
}
