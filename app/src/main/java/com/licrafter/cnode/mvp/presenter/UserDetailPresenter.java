package com.licrafter.cnode.mvp.presenter;

import com.licrafter.cnode.api.CNodeApi;
import com.licrafter.cnode.cache.UserCache;
import com.licrafter.cnode.model.UnReadCountModel;
import com.licrafter.cnode.model.UserDetailModel;
import com.licrafter.cnode.ui.fragment.MineFragment;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * author: shell
 * date 2017/3/6 下午2:51
 **/
public class UserDetailPresenter extends BasePresenter<MineFragment> {

    public void getUserDetail(String userName) {
        mCompositeSubscription.add(CNodeApi.getCNodeService().getUserDetailByName(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserDetailModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(UserDetailModel userDetailModel) {
                        getView().notifyGetUserSuccess(userDetailModel);
                    }
                }));
    }

    public void getUnReadCount() {
        if (UserCache.getUserToken() == null) {
            return;
        }
        mCompositeSubscription.add(CNodeApi.getCNodeService().getUnReadCount(UserCache.getUserToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UnReadCountModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(UnReadCountModel model) {
                        getView().notifyUnReadCount(model);
                    }
                }));
    }
}
