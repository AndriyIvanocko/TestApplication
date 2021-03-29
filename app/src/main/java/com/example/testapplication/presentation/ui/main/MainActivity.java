package com.example.testapplication.presentation.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.testapplication.R;
import com.example.testapplication.common.recyclerview.BaseListAdapter;
import com.example.testapplication.domain.model.project.ProjectModel;
import com.example.testapplication.presentation.base.list.BaseListActivity;
import com.example.testapplication.presentation.base.view.activity.BaseActivity;
import com.example.testapplication.presentation.ui.login.LoginActivity;
import com.example.testapplication.presentation.ui.main.adapter.ProjectAdapter;

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
                "List is empty", false);
    }

    @Override
    public BaseListAdapter<ProjectModel, ?> onCreateAdapterCallback() {
        return new ProjectAdapter();
    }
}