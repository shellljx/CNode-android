package com.licrafter.cnode.mvp.presenter;

import com.licrafter.cnode.api.CNodeApi;
import com.licrafter.cnode.model.PostTopicResultModel;
import com.licrafter.cnode.model.entity.PostTopic;
import com.licrafter.cnode.ui.activity.TopicCreateActivity;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lijx on 2017/4/10.
 */

public class TopicCreatePresenter extends BasePresenter<TopicCreateActivity> {


    public void createPost(String token, String title, String tab, String content) {
        PostTopic topic = new PostTopic(token, title, tab, content);
        mCompositeSubscription.add(CNodeApi.getCNodeService().createTopic(topic)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PostTopicResultModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(PostTopicResultModel postTopicResultModel) {

                    }
                }));
    }
}
