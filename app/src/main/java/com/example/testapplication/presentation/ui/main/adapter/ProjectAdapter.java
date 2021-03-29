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

    @Override
    protected int getLayoutResource() {
        return R.layout.item_project;
    }

    @Override
    protected ProjectViewHolder createViewHolder(View itemView) {
        return new ProjectViewHolder(itemView);
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
        }

        @Override
        protected void onBind(ProjectModel item, int position) {
            mProjectTitle.setText(item.getName());
            if(!item.getUsers().isEmpty())
                mWorkerCount.setText(String.format(Locale.getDefault(), "%d workers", item.getUsers().size()));
            Glide.with(itemView)
                    .load(item.getLogoUrl())
                    .into(mProjectImage);
        }
    }
}
