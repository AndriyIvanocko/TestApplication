package com.example.testapplication.presentation.ui.main.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.testapplication.R;
import com.example.testapplication.presentation.utils.Extras;
import com.example.testapplication.presentation.base.view.ToolbarButtonType;
import com.example.testapplication.presentation.base.view.activity.BaseActivity;
import com.example.testapplication.presentation.base.view.activity.BaseToolbarActivity;

import butterknife.BindView;

public class ProjectDetailActivity extends BaseToolbarActivity<ProjectDetailPresenter> implements ProjectDetailView {

    @BindView(R.id.project_name_edit)
    AppCompatEditText mProjectName;

    public static Intent getLaunchIntent(BaseActivity baseActivity, String name, int id) {
        Intent intent = new Intent(baseActivity, ProjectDetailActivity.class);
        intent.putExtra(Extras.PROJECT_NAME, name);
        intent.putExtra(Extras.PROJECT_ID, id);
        return intent;
    }

    @NonNull
    @Override
    protected ToolbarButtonType getToolbarButtonType() {
        return ToolbarButtonType.BACK;
    }

    @Override
    protected String getToolbarTitle() {
        return getString(R.string.activity_project_detail_title);
    }

    @Override
    protected void injectToComponent() {
        getActivityComponent().inject(this);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_project_detail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mProjectName.setText(bundle.getString(Extras.PROJECT_NAME));
            getPresenter().setProjectId(bundle.getInt(Extras.PROJECT_ID, -1));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.save){
            getPresenter().saveNewName(mProjectName.getText());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateSuccessFull(String name) {
        Intent data = new Intent();
        data.putExtra(Extras.PROJECT_NAME, name);
        setResult(RESULT_OK, data);
        finish();
    }
}
