package com.example.testapplication.presentation.base.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.collection.LongSparseArray;
import androidx.fragment.app.Fragment;

import com.example.testapplication.common.logger.Logger;
import com.example.testapplication.di.components.ConfigPersistentComponent;
import com.example.testapplication.di.components.FragmentComponent;
import com.example.testapplication.presentation.App;
import com.example.testapplication.presentation.base.presenter.Presenter;
import com.example.testapplication.presentation.base.view.BaseView;
import com.example.testapplication.presentation.base.view.activity.BaseActivity;
import com.example.testapplication.presentation.base.view.dialog.DialogListener;

import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<P extends Presenter> extends Fragment implements BaseView<P> {
    private static final String KEY_FRAGMENT_ID = "KEY_FRAGMENT_ID";
    private static final String TAG = "FRAGMENT_LIFECYCLE";

    @Inject
    P mPresenter;

    protected View mRootView;
    protected Bundle mSavedInstanceState;
    protected FragmentNavigator mFragmentNavigator;

    private long mFragmentDiId;
    private static final AtomicLong NEXT_ID = new AtomicLong(0);
    private static final LongSparseArray<ConfigPersistentComponent> sComponentsMap = new LongSparseArray<>();
    private Unbinder mUnbinder;
    private FragmentComponent mFragmentComponent;
    private boolean mMustSetView = false;
    private long mLastClick = 0L;

    //======================== LIFE CYCLE ===========================

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Logger.i(TAG, "onCreate() " + getClass().getSimpleName());
        super.onCreate(createBundleNoFragmentRestore(savedInstanceState));
        createFragmentComponent(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        Logger.i(TAG, "onCreateView() " + getClass().getSimpleName());
        mRootView = inflater.inflate(getLayoutResource(), container, false);
        mUnbinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        Logger.i(TAG, "onActivityCreated() " + getClass().getSimpleName());
        super.onActivityCreated(savedInstanceState);
        injectToComponent();
        mPresenter.attachView(this);
        setBackKeyListener();
    }

    @Override
    public void onViewStateRestored(@Nullable final Bundle savedInstanceState) {
        Logger.i(TAG, "onViewStateRestored() " + getClass().getSimpleName());
        super.onViewStateRestored(savedInstanceState);
        mSavedInstanceState = savedInstanceState;
        if (getUserVisibleHint()) {
            setupView(savedInstanceState);
            onVisibleToUser();
        } else {
            mMustSetView = true;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mMustSetView && isVisibleToUser && getPresenter() != null) {
            setupView(mSavedInstanceState);
            mMustSetView = false;
        }
        if (isVisibleToUser && getPresenter() != null) onVisibleToUser();
    }

    @CallSuper
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putLong(KEY_FRAGMENT_ID, mFragmentDiId);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        Logger.i(TAG, "onPause() " + getClass().getSimpleName());
        hideKeyboard();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        Logger.i(TAG, "onDestroyView() " + getClass().getSimpleName());
        mUnbinder.unbind();
        mPresenter.detachView(false);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Logger.i(TAG, "onDestroy() " + getClass().getSimpleName());
        if (mPresenter != null) mPresenter.detachView(true);
        sComponentsMap.remove(mFragmentDiId);
        super.onDestroy();
    }

    public void popBackStack() {
        getParentFragmentNavigator().popBackStack();
    }

    /**
     * Improve bundle to prevent restoring of fragments.
     *
     * @param bundle bundle container
     * @return improved bundle with removed "fragments parcelable"
     */
    private static Bundle createBundleNoFragmentRestore(Bundle bundle) {
        if (bundle != null) {
            bundle.remove("android:support:fragments");
        }
        return bundle;
    }

    //====================== ABSTRACT ====================

    protected abstract void injectToComponent();

    protected abstract void setupView(Bundle savedInstanceState);

    protected void onVisibleToUser() {
    }

    //====================== PUBLIC ====================

    //======================== MESSAGES ===========================
    @Override
    public void showMessage(String title, String message) {
        if (!isActivityAlive()) return;
        ((BaseActivity) getActivity()).showMessage(title, message);
    }

    @Override
    public void showMessage(String title, String message, DialogListener pListener) {
        if (!isActivityAlive()) return;
        ((BaseActivity) getActivity()).showMessage(title, message, pListener);
    }

    @Override
    public void showMessage(String title, String message, String positiveButtonText, DialogListener pListener) {
        if (!isActivityAlive()) return;
        ((BaseActivity) getActivity()).showMessage(title, message, positiveButtonText, pListener);
    }

    @Override
    public void showConfirmation(String title, String message, DialogListener pListener) {
        if (!isActivityAlive()) return;
        ((BaseActivity) getActivity()).showConfirmation(title, message, pListener);
    }

    @Override
    public void showConfirmation(String title, String message, String positiveButtonText, String negativeButtonText, DialogListener listener) {
        if (!isActivityAlive()) return;
        ((BaseActivity) getActivity()).showConfirmation(title, message, positiveButtonText, negativeButtonText, listener);
    }

    @Override
    public void showError(final String title, final String message) {
        if (!isActivityAlive()) return;
        ((BaseActivity) getActivity()).showError(title, message);
    }

    @Override
    public void showError(String title, String message, DialogListener pListener) {
        if (!isActivityAlive()) return;
        ((BaseActivity) getActivity()).showError(title, message, pListener);
    }

    @Override
    public void showErrorWithRetry(final String title, final String message, final Runnable retryAction) {
        if (!isActivityAlive()) return;
        ((BaseActivity) getActivity()).showErrorWithRetry(title, message, retryAction);
    }

    @Override
    public void showProgress(boolean visible) {
        if (!isActivityAlive()) return;
        ((BaseActivity) getActivity()).showProgress(visible);
    }

    @Override
    public void showProgressDialog(boolean visible) {
        if (!isActivityAlive()) return;
        ((BaseActivity) getActivity()).showProgressDialog(visible);
    }

    @Override
    public void showSnackbar(String message) {
        if (!isActivityAlive()) return;
        ((BaseActivity) getActivity()).showSnackbar(message);
    }

    //======================== GETTERS ===========================

    public FragmentComponent getFragmentComponent() {
        return mFragmentComponent;
    }

    @Override
    public P getPresenter() {
        return mPresenter;
    }

    protected BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    public boolean isActivityAlive() {
        return getActivity() != null && !getActivity().isFinishing();
    }

    public FragmentNavigator getChildFragmentNavigator() {
        if (mFragmentNavigator == null)
            mFragmentNavigator = new FragmentNavigator(getChildFragmentManager());
        return mFragmentNavigator;
    }

    public FragmentNavigator getParentFragmentNavigator() {
        return getBaseActivity().getFragmentNavigator();
    }

    //========================PROTECTED===========================

    protected void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * Called when the hardware back key pressed
     *
     * @return - true if action handled and there are no need to dispatch event to parents
     */
    protected boolean onBackKeyPressed() {
        return false;
    }

    //======================== BUTTONS MULTIPLE PRESSING ===========================

    protected boolean toggleClick() {
        if (System.currentTimeMillis() - mLastClick > 300L) {
            mLastClick = System.currentTimeMillis();
            return true;
        } else {
            return false;
        }
    }

    //========================PRIVATE===========================

    private void createFragmentComponent(Bundle savedInstanceState) {
        if (savedInstanceState != null)
            mFragmentDiId = savedInstanceState.getLong(KEY_FRAGMENT_ID);
        else
            mFragmentDiId = NEXT_ID.getAndIncrement();
        ConfigPersistentComponent configPersistentComponent = sComponentsMap.get(mFragmentDiId);
        if (configPersistentComponent == null) {
            configPersistentComponent = App.getInstance().getAppComponent().plusConfigPersistentComponent();
            sComponentsMap.put(mFragmentDiId, configPersistentComponent);
        }
        mFragmentComponent = configPersistentComponent.plusFragmentComponent();
    }

    private void setBackKeyListener() {
        mRootView.setFocusableInTouchMode(true);
        mRootView.requestFocus();
        mRootView.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                return onBackKeyPressed();
            }
            return false;
        });
    }

    //====================== CURRENT APP SPECIFIC ====================


    @Override
    public void showNotAuthorizedError() {
        if (!isActivityAlive()) return;
        ((BaseActivity) getActivity()).showNotAuthorizedError();
    }

    @Override
    public void openLoginActivity() {
        if (!isActivityAlive()) return;
        ((BaseActivity) getActivity()).openLoginActivity();
    }
}
