package com.licrafter.cnode.mvp.presenter;

import com.licrafter.cnode.mvp.view.MvpView;

import rx.subscriptions.CompositeSubscription;

/**
 * author: shell
 * date 2017/2/24 下午4:17
 **/
public class BasePresenter<T extends MvpView> {

    private T mvpView;
    public CompositeSubscription mCompositeSubscription;

    public void attachView(T view) {
        this.mvpView = view;
        this.mCompositeSubscription = new CompositeSubscription();
    }

    public void detachView() {
        mvpView = null;
        mCompositeSubscription.unsubscribe();
        mCompositeSubscription = null;
    }

    public boolean isViewAttached() {
        return mvpView != null;
    }

    public T getView() {
        return mvpView;
    }
}
