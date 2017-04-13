package com.licrafter.cnode.mvp.presenter;

import com.licrafter.cnode.api.CNodeApi;
import com.licrafter.cnode.cache.UserCache;
import com.licrafter.cnode.model.AccessTokenBody;
import com.licrafter.cnode.model.CollectionBody;
import com.licrafter.cnode.model.TopicDetailModel;
import com.licrafter.cnode.ui.activity.TopicDetailActivity;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * author: shell
 * date 2017/3/1 上午10:08
 **/
public class TopicDetailPresenter extends BasePresenter<TopicDetailActivity> {

    public void getTopicDetailById(String topicId) {
        CNodeApi.getCNodeService().getTopicDetailById(topicId, UserCache.getUserToken(), true)
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

    public void makeUp(String reply_id) {
        android.util.Log.d("ljx", "post");
        mCompositeSubscription.add(CNodeApi.getCNodeService()
                .makeUp(new AccessTokenBody(UserCache.getUserToken()), reply_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        android.util.Log.d("ljx", e.toString());
                    }

                    @Override
                    public void onNext(Void aVoid) {

                    }
                }));
    }

    public void makeCollected(boolean collect, String topic_id) {
        mCompositeSubscription.add(getCollectObservable(collect, topic_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Void aVoid) {

                    }
                }));
    }

    private Observable<Void> getCollectObservable(boolean collect, String topic_id) {
        CollectionBody body = new CollectionBody(UserCache.getUserToken(), topic_id);
        if (collect) {
            return CNodeApi.getCNodeService().collectPost(body);
        } else {
            return CNodeApi.getCNodeService().deCollectPost(body);
        }
    }
}
