package com.licrafter.cnode.mvp.presenter;

import android.os.Environment;
import android.os.SystemClock;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.ProgressCallback;
import com.avos.avoscloud.SaveCallback;
import com.licrafter.cnode.R;
import com.licrafter.cnode.api.CNodeApi;
import com.licrafter.cnode.cache.UserCache;
import com.licrafter.cnode.model.PostTopicResultModel;
import com.licrafter.cnode.model.entity.PostTopic;
import com.licrafter.cnode.ui.activity.TopicCreateActivity;
import com.licrafter.cnode.utils.BitmapCompressUtil;
import com.licrafter.cnode.widget.dialog.UploadingDialog;

import java.io.FileNotFoundException;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
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
                        getView().dismissSendDialog();
                        getView().onFailed(e);
                    }

                    @Override
                    public void onNext(PostTopicResultModel result) {
                        getView().dismissSendDialog();
                        getView().notifySendPostSuccess(result);
                    }
                }));
    }

    public void uploadImg(final String path) {
        mCompositeSubscription.add(Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                String targetPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + System.currentTimeMillis() + "_compress_avatar.jpg";
                String resultPath = BitmapCompressUtil.compressToPath(path, targetPath);
                String imgName = UserCache.getUserName() + "_" + SystemClock.currentThreadTimeMillis() + ".png";
                try {
                    final AVFile file = AVFile.withAbsoluteLocalPath(imgName, resultPath);
                    file.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                subscriber.onNext(file.getUrl());
                            } else {
                                subscriber.onError(e);
                            }
                        }
                    }, new ProgressCallback() {
                        @Override
                        public void done(Integer integer) {
                            getView().uploadProgress(integer);
                        }
                    });
                } catch (FileNotFoundException e) {
                    Observable.error(e);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().dismissUploadDialog();
                        getView().notifyUploadFailed(e);
                    }

                    @Override
                    public void onNext(String url) {
                        getView().dismissUploadDialog();
                        getView().notifyUploadSuccess(url);
                    }
                }));
    }
}
