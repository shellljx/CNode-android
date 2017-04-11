package com.licrafter.cnode.mvp.presenter;

import com.licrafter.cnode.api.CNodeApi;
import com.licrafter.cnode.model.TopicDetailModel;
import com.licrafter.cnode.ui.activity.TopicDetailActivity;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * author: shell
 * date 2017/3/1 上午10:08
 **/
public class TopicDetailPresenter extends BasePresenter<TopicDetailActivity> {

    public void getTopicDetailById(String topicId) {
        CNodeApi.getCNodeService().getTopicDetailById(topicId, null, true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TopicDetailModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().onFailed(e);
                    }

                    @Override
                    public void onNext(TopicDetailModel topicDetailModel) {
                        if (topicDetailModel.isSuccess()) {
                            getView().notifyGetDetailSuccess(topicDetailModel);
                        } else {
                            getView().onFailed(new Throwable("获取详情失败"));
                        }
                    }
                });
    }
}
