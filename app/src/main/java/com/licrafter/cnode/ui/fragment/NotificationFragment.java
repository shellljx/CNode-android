package com.licrafter.cnode.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.licrafter.cnode.R;
import com.licrafter.cnode.base.BaseFragment;

/**
 * author: shell
 * date 2017/3/10 上午11:40
 **/
public class NotificationFragment extends BaseFragment {

    public static NotificationFragment newInstance() {
        NotificationFragment fragment = new NotificationFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public int setContentView() {
        return R.layout.fragment_notification;
    }

    @Override
    public void lazyLoad() {

    }

    @Override
    public void initView(View root) {

    }

    @Override
    public void setListeners() {

    }

    @Override
    public void bind() {

    }

    @Override
    public void unBind() {

    }
}
