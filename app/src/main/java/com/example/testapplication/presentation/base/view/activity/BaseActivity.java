package com.example.testapplication.presentation.base.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.LongSparseArray;
import androidx.fragment.app.FragmentManager;

import com.example.testapplication.presentation.App;
import com.google.android.material.snackbar.Snackbar;
import com.example.testapplication.R;
import com.example.testapplication.di.components.ActivityComponent;
import com.example.testapplication.di.components.ConfigPersistentComponent;
import com.example.testapplication.di.modules.ActivityModule;
import com.example.testapplication.presentation.base.presenter.Presenter;
import com.example.testapplication.presentation.base.view.BaseView;
import com.example.testapplication.presentation.base.view.dialog.CustomProgressDialog;
import com.example.testapplication.presentation.base.view.dialog.DialogListener;
import com.example.testapplication.presentation.base.view.fragment.BaseFragment;
import com.example.testapplication.presentation.base.view.fragment.FragmentNavigator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<P extends Presenter> extends AppCompatActivity implements BaseView<P> {
    public static final int INVALID_RES_ID = -1;

    private static final String KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID";
    private static final AtomicLong NEXT_ID = new AtomicLong(0);
    private static final LongSparseArray<ConfigPersistentComponent> sComponentsMap = new LongSparseArray<>();
    private long mActivityId;

    @Inject
    P mPresenter;

    protected View mRootView;
    protected FragmentNavigator mFragmentNavigator;

    private AlertDialog mDialog;
    protected Snackbar mSnackbar;
    private CustomProgressDialog mProgressDialog;
    private ActivityComponent mActivityComponent;
    private Unbinder mUnbinder;
    private long mLastClick = 0L;
    boolean mDialogPressedFlag = false;
    private List<OnActivityResultListener> mOnActivityResultListeners = new ArrayList<>();

    //========================= LIFE CYCLE ========================

    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = LayoutInflater.from(this).inflate(getLayoutResource(), null);
        setContentView(mRootView);
        mUnbinder = ButterKnife.bind(this);
        createActivityComponent(savedInstanceState);
        injectToComponent();
        mPresenter.attachView(this);
        mSnackbar = Snackbar.make(mRootView, "", Snackbar.LENGTH_SHORT);
        createFirebaseChannel();
    }

    @CallSuper
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putLong(KEY_ACTIVITY_ID, mActivityId);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        hideKeyboard();
        super.onPause();
    }

    @CallSuper
    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        if (isChangingConfigurations()) {
            mPresenter.detachView(false);
        } else {
            if (isFinishing()) {
                mPresenter.detachView(true);
                sComponentsMap.remove(mActivityId);
            } else {
                mPresenter.detachView(false);
            }
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        final FragmentManager fragmentManager = getFragmentNavigator().getFragmentManager();
        int count = fragmentManager.getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
        } else {
            int index = fragmentManager.getBackStackEntryCount() - 1;
            FragmentManager.BackStackEntry backEntry = fragmentManager.getBackStackEntryAt(index);
            String tag = backEntry.getName();
            BaseFragment fragment = (BaseFragment) fragmentManager.findFragmentByTag(tag);
            if (fragment == null) {
                getFragmentNavigator().popBackStack();
            } else {
                fragment.popBackStack();
            }
        }
    }


    //====================================== ON ACTIVITY RESULT ====================================

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (OnActivityResultListener listener : mOnActivityResultListeners) {
            listener.onActivityResult(requestCode, resultCode, data);
        }
    }

    public interface OnActivityResultListener {
        void onActivityResult(int requestCode, int resultCode, @Nullable Intent data);
    }

    public void addOnActivityResultListener(OnActivityResultListener listener) {
        mOnActivityResultListeners.add(listener);
    }

    public void removeOnActivityResultListener(OnActivityResultListener listener) {
        mOnActivityResultListeners.remove(listener);
    }

    public void clearOnActivityResultListeners() {
        mOnActivityResultListeners.clear();
    }

    //======================================== PROTECTED ===========================================

    protected boolean toggleClick() {
        if (System.currentTimeMillis() - mLastClick > 300L) {
            mLastClick = System.currentTimeMillis();
            return true;
        } else {
            return false;
        }
    }

    protected void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void showProgress(boolean visible) {
        showProgressDialog(visible);
    }

    @Override
    public void showProgressDialog(boolean visible) {
        if (visible) {
            if (mProgressDialog == null || !mProgressDialog.isShowing()) {
                mProgressDialog = CustomProgressDialog.newInstance(getString(R.string.dialog_loading));
                mProgressDialog.show(getSupportFragmentManager(), CustomProgressDialog.class.getSimpleName());
            }
        } else {
            if (mProgressDialog != null && mProgressDialog.isShowing())
                mProgressDialog.dismiss();
        }
    }

    /**
     * Starts activity. And resets all app backstack. So after new activity close - app closes
     * before: a -> b -> c
     * task  : a opens c
     * after : a
     *
     * @param intent
     */
    public void startActivityResetBackStack(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    /**
     * Starts from activity (d) activity (b) which was shown earlier and clears stack to this activity.
     * If activity (b) has never been in stack - the new one just added.
     * before: a -> b -> c -> d
     * task  : d opens b
     * after : a -> b
     *
     * @param intent
     */
    public void returnToActivityClearingBackStack(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public int getActionBarHeight() {
        int result = 0;
        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            result = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }
        return result;
    }

    public int getNavigationBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    protected void createFirebaseChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // TODO set fcm real data
            //String channelId = getString(R.string.fcm_channel_id);
            //String channelName = getString(R.string.fcm_channel_name);
            /*String channelId = "";
            String channelName = "";
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH));*/
        }
    }
    //======================================== ABSTRACT ============================================

    protected abstract void injectToComponent();

    //===================================== GETTERS / SETTERS ======================================

    @Override
    public P getPresenter() {
        return mPresenter;
    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    public FragmentNavigator getFragmentNavigator() {
        if (mFragmentNavigator == null)
            mFragmentNavigator = new FragmentNavigator(getSupportFragmentManager());
        return mFragmentNavigator;
    }

    //=========================================== MESSAGES =========================================

    @Override
    public void showMessage(String title, String message) {
        showMessage(title, message, getString(R.string.dialog_ok), null);
    }

    @Override
    public void showMessage(String title, String message, DialogListener pListener) {
        showMessage(title, message, getString(R.string.dialog_ok), pListener);
    }

    @Override
    public void showMessage(String title, String message, String positiveButtonText, DialogListener listener) {
        if (mDialog != null && mDialog.isShowing()) mDialog.dismiss();

//        mDialog = MessageDialog.newInstance(title, message, positiveButtonText, null, listener);
//        mDialog.show(getSupportFragmentManager(), MessageDialog.class.getSimpleName());

        mDialogPressedFlag = false;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        mDialog = builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButtonText, (dialog, which) -> {
                    if (listener != null) {
                        mDialogPressedFlag = true;
                        dialog.dismiss();
                        listener.onPositivePressed();
                    }
                })
                .setOnDismissListener(dialog -> {
                    if (listener != null && !mDialogPressedFlag) {
                        listener.onCancelPressed();
                    }
                })
                //.setIcon(android.R.drawable.ic_dialog_alert)
                .create();
        if (title == null)
            mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setOnShowListener(dialogInterface -> handleDialogSize());
        mDialog.show();
    }

    private void handleDialogSize() {
        //WindowManager wm = (WindowManager) mDialog.getContext().getSystemService(Context.WINDOW_SERVICE);
        //if (wm == null) return;
        //Display display = wm.getDefaultDisplay();
        //DisplayMetrics metrics = new DisplayMetrics();
        //display.getMetrics(metrics);
        //if (mDialog.getWindow() == null) return;
        //int width = (int) (metrics.widthPixels * 0.9f);
        //int height = mDialog.getWindow().getAttributes().height;
        //Window win = mDialog.getWindow();
        //win.setLayout(width, height);
    }

    @Override
    public void showConfirmation(String title, String message, DialogListener pListener) {
        showConfirmation(title, message, getString(R.string.dialog_yes), getString(R.string.dialog_no), pListener);
    }

    @Override
    public void showConfirmation(String title, String message, String positiveButtonText, String negativeButtonText, DialogListener listener) {
        if (mDialog != null && mDialog.isShowing()) mDialog.dismiss();
//        mDialog = MessageDialog.newInstance(title, message, positiveButtonText, negativeButtonText, listener);
//        mDialog.show(getSupportFragmentManager(), MessageDialog.class.getSimpleName());

        mDialogPressedFlag = false;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        mDialog = builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButtonText, (dialog, which) -> {
                    if (listener != null) {
                        mDialogPressedFlag = true;
                        dialog.dismiss();
                        listener.onPositivePressed();
                    }
                })
                .setNegativeButton(negativeButtonText, (dialog, which) -> {
                    if (listener != null) {
                        mDialogPressedFlag = true;
                        dialog.dismiss();
                        listener.onNegativePressed();
                    }
                })
                .setOnDismissListener(dialog -> {
                    if (listener != null && !mDialogPressedFlag) {
                        listener.onCancelPressed();
                    }
                })
                //.setIcon(android.R.drawable.ic_dialog_alert)
                .create();
        if (title == null)
            mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setOnShowListener(dialogInterface -> handleDialogSize());
        mDialog.show();
    }

    @Override
    public void showError(final String title, final String message) {
        showMessage(title, message);
    }

    @Override
    public void showError(String title, String message, DialogListener listener) {
        showMessage(title, message, getString(R.string.dialog_ok), listener);
    }

    @Override
    public void showErrorWithRetry(final String title, final String message, final Runnable retryAction) {
        showConfirmation(title, message, getString(R.string.dialog_retry), getString(R.string.dialog_cancel), new DialogListener() {
            @Override
            public void onPositivePressed() {
                retryAction.run();
            }
        });
    }

    @Override
    public void showSnackbar(final String message) {
        mSnackbar.setText(message).show();
    }

    //========================================== PRIVATE ===========================================

    private void createActivityComponent(Bundle savedInstanceState) {
        if (savedInstanceState != null)
            mActivityId = savedInstanceState.getLong(KEY_ACTIVITY_ID);
        else
            mActivityId = NEXT_ID.getAndIncrement();
        ConfigPersistentComponent configPersistentComponent = sComponentsMap.get(mActivityId);
        if (configPersistentComponent == null) {
            configPersistentComponent = App.getInstance().getAppComponent().plusConfigPersistentComponent();
            sComponentsMap.put(mActivityId, configPersistentComponent);
        }
        mActivityComponent = configPersistentComponent.plusActivityComponent(new ActivityModule(this));
    }

    //===================================== CURRENT APP SPECIFIC ===================================

    @Override
    public void showNotAuthorizedError() {
        Toast.makeText(this, R.string.dialog_error_authorization, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openLoginActivity() {
        // TODO add first activity
        //startActivityResetBackStack(LoginActivity.getLaunchIntent(this));
    }
}
