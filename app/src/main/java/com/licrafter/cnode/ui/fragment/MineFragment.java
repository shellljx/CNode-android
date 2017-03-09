package com.licrafter.cnode.ui.fragment;

import android.view.View;

import com.licrafter.cnode.R;
import com.licrafter.cnode.base.BaseFragment;
import com.licrafter.cnode.cache.UserCache;
import com.licrafter.cnode.model.UserDetailModel;
import com.licrafter.cnode.mvp.presenter.UserDetailPresenter;
import com.licrafter.cnode.mvp.view.MvpView;

/**
 * author: shell
 * date 2017/2/24 上午11:33
 **/
public class MineFragment extends BaseFragment implements MvpView {

    private UserDetailPresenter mPresenter;

    @Override
    public int setContentView() {
        return R.layout.fragment_mine;
    }

    @Override
    public void lazyLoad() {
        if (UserCache.USER_NAME != null) {
            mPresenter.getUserDetail(UserCache.USER_NAME);
        }
    }

    @Override
    public void initView(View root) {
    }

    @Override
    public void setListeners() {

    }

    @Override
    public void bind() {
        mPresenter = new UserDetailPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void unBind() {

    }

    public void notifyGetUserSuccess(UserDetailModel userDetailModel) {

    }

    @Override
    public void onFailed() {

    }
}
