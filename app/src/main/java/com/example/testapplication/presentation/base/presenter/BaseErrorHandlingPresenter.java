package com.example.testapplication.presentation.base.presenter;

import android.content.Context;

import com.example.testapplication.R;
import com.example.testapplication.data.local.UserStorage;
import com.example.testapplication.data.network.config.error.ResponseError;
import com.example.testapplication.data.network.config.error.RetrofitException;
import com.example.testapplication.domain.interactors.base.AsyncUseCase;
import com.example.testapplication.domain.interactors.base.BaseCompletableObserver;
import com.example.testapplication.domain.interactors.base.BaseDisposableObserver;
import com.example.testapplication.domain.interactors.base.ErrorHandler;
import com.example.testapplication.domain.interactors.base.SimpleErrorHandler;
import com.example.testapplication.presentation.base.list.PaginationListModel;
import com.example.testapplication.presentation.base.list.PaginationManager;
import com.example.testapplication.presentation.base.view.BaseView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class BaseErrorHandlingPresenter<V extends BaseView> extends BasePresenter<V> implements Presenter<V> {
    @Inject
    protected Context mContext;
    @Inject
    protected UserStorage mUserStorage;

    protected Runnable mRetryAction;
    private final PaginationManager mPaginationManager = new PaginationManager();

    public PaginationManager getPaginationManager() {
        return mPaginationManager;
    }

    protected <T> void runBackgroundAction(final AsyncUseCase<BaseDisposableObserver, Observable> asyncUseCase,
                                           final BackgroundAction<T> backgroundAction) {
        runBackgroundAction(asyncUseCase, true, backgroundAction, null);
    }

    protected <T> void runBackgroundAction(final AsyncUseCase<BaseDisposableObserver, Observable> asyncUseCase,
                                           final boolean showProgress,
                                           final BackgroundAction<T> backgroundAction) {
        runBackgroundAction(asyncUseCase, showProgress, backgroundAction, null);
    }

    /**
     * Starts the new action in background thread. If action finishes when activity is changing configuration
     * or recreating - the result will be delivered after activity is ready. When error occurs - the message is
     * shown. In case of the network error - there is shown retry message. So there no need to chek if
     * is view attached. Method shows the progress dialog and hides it when error, but not closes it within onNext()!!!
     *
     * @param asyncUseCase
     * @param backgroundAction - Action when complete
     * @param <T>              - The type which must be returned
     */
    protected <T> void runBackgroundAction(final AsyncUseCase<BaseDisposableObserver, Observable> asyncUseCase,
                                           final boolean showProgress, final BackgroundAction<T> backgroundAction,
                                           final ErrorHandler customErrorHandler) {
        mRetryAction = () -> {
            if (!isViewAttached()) return;
            addDisposable(asyncUseCase.execute(new BaseDisposableObserver<T>() {
                @Override
                protected void onStart() {
                    if (showProgress) getView().showProgress(true);
                }

                @Override
                public void onNext(T data) {
                    startSafeAction(() -> {
                        backgroundAction.onComplete(data);
                    });
                }

                @Override
                public void onError(Throwable e) {
                    startSafeAction(() -> {
                        getView().showProgress(false);
                        super.onError(e);
                    });
                }

                @Override
                public boolean handleNetworkError(RetrofitException exception) {
                    if (customErrorHandler != null && customErrorHandler.handleNetworkError(exception)) {
                        return true;
                    }
                    return mErrorHandler.handleNetworkError(exception);
                }

                @Override
                public boolean handleUnauthorizedError(ResponseError error) {
                    if (customErrorHandler != null && customErrorHandler.handleUnauthorizedError(error)) {
                        return true;
                    }
                    return mErrorHandler.handleUnauthorizedError(error);
                }

                @Override
                public boolean handleNotFoundError(ResponseError error) {
                    if (customErrorHandler != null && customErrorHandler.handleNotFoundError(error)) {
                        return true;
                    }
                    return mErrorHandler.handleNotFoundError(error);
                }

                @Override
                public boolean handleResponseError(ResponseError error) {
                    if (customErrorHandler != null && customErrorHandler.handleResponseError(error)) {
                        return true;
                    }
                    return mErrorHandler.handleResponseError(error);
                }

                @Override
                public boolean handleUnknownHttpError(RetrofitException exception) {
                    if (customErrorHandler != null && customErrorHandler.handleUnknownHttpError(exception)) {
                        return true;
                    }
                    return mErrorHandler.handleUnknownHttpError(exception);
                }

                @Override
                public boolean handleAppError(Throwable throwable) {
                    if (customErrorHandler != null && customErrorHandler.handleAppError(throwable)) {
                        return true;
                    }
                    return mErrorHandler.handleAppError(throwable);
                }

                @Override
                public boolean handleServerError(RetrofitException exception) {
                    if (customErrorHandler != null && customErrorHandler.handleServerError(exception)) {
                        return true;
                    }
                    return mErrorHandler.handleServerError(exception);
                }

                @Override
                public void onComplete() {
                }
            }));
        };
        mRetryAction.run();
    }

    protected <T> void runBackgroundWithPaginationManager(final AsyncUseCase<BaseDisposableObserver, Observable> asyncUseCase,
                                                          final BackgroundAction<List<T>> backgroundAction) {
        runBackgroundWithPaginationManager(asyncUseCase, true, backgroundAction, null);
    }

    protected <T> void runBackgroundWithPaginationManager(final AsyncUseCase<BaseDisposableObserver, Observable> asyncUseCase,
                                                          final boolean showProgress,
                                                          final BackgroundAction<List<T>> backgroundAction) {
        runBackgroundWithPaginationManager(asyncUseCase, showProgress, backgroundAction, null);
    }

    protected <T> void runBackgroundWithPaginationManager(final AsyncUseCase<BaseDisposableObserver, Observable> asyncUseCase,
                                                          final boolean showProgress,
                                                          final BackgroundAction<List<T>> backgroundAction,
                                                          final ErrorHandler customErrorHandler) {
        mRetryAction = () -> {
            if (!isViewAttached()) return;
            addDisposable(asyncUseCase.execute(new BaseDisposableObserver<PaginationListModel<T>>() {
                @Override
                protected void onStart() {
                    if (showProgress) getView().showProgress(true);
                }

                @Override
                public void onNext(PaginationListModel<T> data) {
                    startSafeAction(() -> {
                        mPaginationManager.onDataLoaded(data.getCurrentPage(), data.getData().size());
                        backgroundAction.onComplete(data.getData());
                    });
                }

                @Override
                public void onError(Throwable e) {
                    startSafeAction(() -> {
                        getView().showProgress(false);
                        super.onError(e);
                    });
                }

                @Override
                public boolean handleNetworkError(RetrofitException exception) {
                    if (customErrorHandler != null && customErrorHandler.handleNetworkError(exception)) {
                        return true;
                    }
                    return mErrorHandler.handleNetworkError(exception);
                }

                @Override
                public boolean handleUnauthorizedError(ResponseError error) {
                    if (customErrorHandler != null && customErrorHandler.handleUnauthorizedError(error)) {
                        return true;
                    }
                    return mErrorHandler.handleUnauthorizedError(error);
                }

                @Override
                public boolean handleNotFoundError(ResponseError error) {
                    if (customErrorHandler != null && customErrorHandler.handleNotFoundError(error)) {
                        return true;
                    }
                    return mErrorHandler.handleNotFoundError(error);
                }

                @Override
                public boolean handleResponseError(ResponseError error) {
                    if (customErrorHandler != null && customErrorHandler.handleResponseError(error)) {
                        return true;
                    }
                    return mErrorHandler.handleResponseError(error);
                }

                @Override
                public boolean handleUnknownHttpError(RetrofitException exception) {
                    if (customErrorHandler != null && customErrorHandler.handleUnknownHttpError(exception)) {
                        return true;
                    }
                    return mErrorHandler.handleUnknownHttpError(exception);
                }

                @Override
                public boolean handleAppError(Throwable throwable) {
                    if (customErrorHandler != null && customErrorHandler.handleAppError(throwable)) {
                        return true;
                    }
                    return mErrorHandler.handleAppError(throwable);
                }

                @Override
                public boolean handleServerError(RetrofitException exception) {
                    if (customErrorHandler != null && customErrorHandler.handleServerError(exception)) {
                        return true;
                    }
                    return mErrorHandler.handleServerError(exception);
                }

                @Override
                public void onComplete() {
                }
            }));
        };
        mRetryAction.run();
    }

    protected void runCompletableAction(final AsyncUseCase<BaseCompletableObserver, Completable> asyncUseCase,
                                        final CompletableAction completableAction) {
        runCompletableAction(asyncUseCase, true, completableAction, null);
    }

    protected void runCompletableAction(final AsyncUseCase<BaseCompletableObserver, Completable> asyncUseCase,
                                        final boolean showProgress, final CompletableAction completableAction) {
        runCompletableAction(asyncUseCase, showProgress, completableAction, null);
    }

    protected void runCompletableAction(final AsyncUseCase<BaseCompletableObserver, Completable> asyncUseCase,
                                        final boolean showProgress, final CompletableAction completableAction,
                                        final ErrorHandler customErrorHandler) {
        mRetryAction = () -> {
            if (!isViewAttached()) return;
            addDisposable(asyncUseCase.execute(new BaseCompletableObserver() {
                @Override
                protected void onStart() {
                    if (showProgress) getView().showProgress(true);
                }

                @Override
                public void onComplete() {
                    startSafeAction(completableAction::onComplete);
                }

                @Override
                public void onError(Throwable e) {
                    startSafeAction(() -> {
                        getView().showProgress(false);
                        super.onError(e);
                    });
                }

                @Override
                public boolean handleNetworkError(RetrofitException exception) {
                    if (customErrorHandler != null && customErrorHandler.handleNetworkError(exception)) {
                        return true;
                    }
                    return mErrorHandler.handleNetworkError(exception);
                }

                @Override
                public boolean handleUnauthorizedError(ResponseError error) {
                    if (customErrorHandler != null && customErrorHandler.handleUnauthorizedError(error)) {
                        return true;
                    }
                    return mErrorHandler.handleUnauthorizedError(error);
                }

                @Override
                public boolean handleNotFoundError(ResponseError error) {
                    if (customErrorHandler != null && customErrorHandler.handleNotFoundError(error)) {
                        return true;
                    }
                    return mErrorHandler.handleNotFoundError(error);
                }

                @Override
                public boolean handleResponseError(ResponseError error) {
                    if (customErrorHandler != null && customErrorHandler.handleResponseError(error)) {
                        return true;
                    }
                    return mErrorHandler.handleResponseError(error);
                }

                @Override
                public boolean handleUnknownHttpError(RetrofitException exception) {
                    if (customErrorHandler != null && customErrorHandler.handleUnknownHttpError(exception)) {
                        return true;
                    }
                    return mErrorHandler.handleUnknownHttpError(exception);
                }

                @Override
                public boolean handleAppError(Throwable throwable) {
                    if (customErrorHandler != null && customErrorHandler.handleAppError(throwable)) {
                        return true;
                    }
                    return mErrorHandler.handleAppError(throwable);
                }

                @Override
                public boolean handleServerError(RetrofitException exception) {
                    if (customErrorHandler != null && customErrorHandler.handleServerError(exception)) {
                        return true;
                    }
                    return mErrorHandler.handleServerError(exception);
                }
            }));
        };
        mRetryAction.run();
    }

    protected <T> void runAsyncAction(final boolean showProgress,
                                      final BackgroundAction<T> backgroundAction,
                                      final ObservableOnSubscribe<T> observable,
                                      final SimpleErrorHandler errorHandler) {
        mRetryAction = () -> {
            if (!isViewAttached()) return;
            DisposableObserver<T> observer = new DisposableObserver<T>() {
                @Override
                protected void onStart() {
                    if (showProgress) getView().showProgress(true);
                }

                @Override
                public void onNext(T data) {
                    backgroundAction.onComplete(data);
                }

                @Override
                public void onError(Throwable e) {
                    errorHandler.handleError(e);
                }

                @Override
                public void onComplete() {

                }
            };
            addDisposable(observer);
            Observable.create(observable)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        };
        mRetryAction.run();
    }

    private final ErrorHandler mErrorHandler = new ErrorHandler() {
        @Override
        public boolean handleNetworkError(RetrofitException exception) {
            getView().showErrorWithRetry(mContext.getString(R.string.dialog_title_problem),
                    mContext.getString(R.string.dialog_error_network), mRetryAction);
            return true;
        }

        @Override
        public boolean handleUnauthorizedError(ResponseError error) {
            mUserStorage.clearUserData();
            getView().showNotAuthorizedError();
            getView().openLoginActivity();
            return true;
        }

        @Override
        public boolean handleNotFoundError(ResponseError error) {
            getView().showError(mContext.getString(R.string.dialog_title_problem), error.getFormattedMessage());
            return true;
        }

        @Override
        public boolean handleResponseError(ResponseError error) {
            getView().showError(mContext.getString(R.string.dialog_title_problem), error.getFormattedMessage());
            return true;
        }

        @Override
        public boolean handleUnknownHttpError(RetrofitException exception) {
            getView().showError(mContext.getString(R.string.dialog_title_problem),
                    mContext.getString(R.string.dialog_error_unknown));
            return true;
        }

        @Override
        public boolean handleServerError(RetrofitException exception) {
            //Not used yet in this app
            return false;
        }

        @Override
        public boolean handleAppError(Throwable throwable) {
            getView().showError(mContext.getString(R.string.dialog_title_problem),
                    throwable.getMessage());
            return true;
        }
    };
}
