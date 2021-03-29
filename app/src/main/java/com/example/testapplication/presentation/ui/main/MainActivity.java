package com.example.testapplication.presentation.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.testapplication.R;
import com.example.testapplication.common.recyclerview.BaseListAdapter;
import com.example.testapplication.domain.model.project.ProjectModel;
import com.example.testapplication.presentation.base.list.BaseListActivity;
import com.example.testapplication.presentation.base.view.activity.BaseActivity;
import com.example.testapplication.presentation.ui.main.adapter.ProjectAdapter;
import com.example.testapplication.presentation.ui.main.detail.ProjectDetailActivity;
import com.example.testapplication.presentation.utils.Consts;
import com.example.testapplication.presentation.utils.Extras;

public class MainActivity extends BaseListActivity<ProjectModel, MainPresenter> implements MainView {

    public static Intent getLaunchIntent(BaseActivity baseActivity) {
        return new Intent(baseActivity, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter().startLoading();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null){
            if(requestCode == Consts.EDIT_PROJECT ){
                ((ProjectAdapter) getAdapter()).updateName(data.getExtras().getString(Extras.PROJECT_NAME));
            }
        }
    }

    @Override
    protected void injectToComponent() {
        getActivityComponent().inject(this);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreateListCallback() {
        createStandardList(R.id.recycler_base_list, true, true,
                getString(R.string.error_empty_list), false);
    }

    @Override
    public BaseListAdapter<ProjectModel, ?> onCreateAdapterCallback() {
        return new ProjectAdapter(item -> {
            startActivityForResult(ProjectDetailActivity.getLaunchIntent(this,
                    item.getName(), item.getId()),
                    Consts.EDIT_PROJECT);
        });
    }
}