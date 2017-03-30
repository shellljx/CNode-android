package com.licrafter.cnode.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * author: shell
 * date 2017/2/24 上午11:27
 **/
public abstract class BaseFragment extends Fragment {

    protected boolean mPrepared;
    protected boolean mVisibleToUser;
    private BaseActivity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (BaseActivity) activity;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mVisibleToUser = isVisibleToUser;
        tryLoad();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(setContentView(), container, false);
        ButterKnife.bind(this, root);
        mPrepared = true;
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        bind();
        tryLoad();
        setListeners();
    }

    private void tryLoad() {
        if (mVisibleToUser && mPrepared) {
            lazyLoad();
        }
    }

    public abstract int setContentView();

    public abstract void lazyLoad();

    public abstract void initView(View root);

    public abstract void setListeners();

    public abstract void bind();

    public abstract void unBind();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPrepared = false;
        unBind();
    }

    public BaseActivity getBaseActivity() {
        return mActivity;
    }
}
