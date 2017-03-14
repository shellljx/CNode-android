package com.licrafter.cnode.mvp.presenter;

import com.licrafter.cnode.api.CNodeApi;
import com.licrafter.cnode.model.LoginBody;
import com.licrafter.cnode.model.LoginResultModel;
import com.licrafter.cnode.ui.activity.LoginActivity;
import com.licrafter.cnode.utils.LogUtils;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * author: shell
 * date 2017/3/13 下午8:22
 **/
public class LoginPresenter extends BasePresenter<LoginActivity> {

    public void login(String token) {
        LoginBody body = new LoginBody();
        body.setAccesstoken(token);

        mCompositeSubscription.add(CNodeApi.getInstance().getService().login(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginResultModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.error(LoginPresenter.class.getName(), e.toString());
                    }

                    @Override
                    public void onNext(LoginResultModel model) {
                        getView().notifyLoginSuccess(model);
                    }
                }));
    }
}
