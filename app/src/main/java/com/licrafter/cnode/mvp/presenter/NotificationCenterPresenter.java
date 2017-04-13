package com.licrafter.cnode.mvp.presenter;

import com.licrafter.cnode.api.CNodeApi;
import com.licrafter.cnode.cache.UserCache;
import com.licrafter.cnode.model.AccessTokenBody;
import com.licrafter.cnode.model.MarkResultModel;
import com.licrafter.cnode.model.NotificationModel;
import com.licrafter.cnode.ui.activity.NotificationCenterActivity;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by lijx on 2017/4/11.
 */

public class NotificationCenterPresenter extends BasePresenter<NotificationCenterActivity> {

    public void getAllNotification() {
        mCompositeSubscription.add(CNodeApi.getCNodeService().getAllNotifications(UserCache.getUserToken(), true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        getView().stopRefresh();
                    }
                })
                .subscribe(new Subscriber<NotificationModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().onFailed(e);
                    }

                    @Override
                    public void onNext(NotificationModel notificationModel) {
                        getView().notifyGetSuccess(notificationModel);
                    }
                }));
    }

    public void readAll() {
        mCompositeSubscription.add(CNodeApi.getCNodeService()
                .markAllMsg(new AccessTokenBody(UserCache.getUserToken()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MarkResultModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MarkResultModel markResultModel) {

                    }
                }));
    }
}
