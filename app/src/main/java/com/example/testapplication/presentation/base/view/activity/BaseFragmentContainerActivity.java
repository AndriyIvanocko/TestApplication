package com.example.testapplication.presentation.base.view.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.fragment.app.Fragment;

import com.example.testapplication.R;
import com.example.testapplication.presentation.base.presenter.Presenter;
import com.example.testapplication.presentation.base.view.BaseView;


public abstract class BaseFragmentContainerActivity<P extends Presenter> extends BaseActivity<P> implements BaseView<P> {

    @Override
    public int getLayoutResource() {
        return R.layout.activity_fragment_container;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentNavigator().replaceFragment(R.id.fragment_container, getFragmentToSetup());
    }

    protected abstract Fragment getFragmentToSetup();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            default:
                return false;
        }
    }
}
