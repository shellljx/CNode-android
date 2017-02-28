package com.licrafter.cnode.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.licrafter.cnode.R;
import com.licrafter.cnode.base.BaseFragment;
import com.licrafter.cnode.utils.FragmentUtils;

/**
 * author: shell
 * date 2017/2/27 下午3:12
 **/
public class HomePageFragment extends BaseFragment {

    @Override
    public int setContentView() {
        return R.layout.fragment_home_page;
    }

    @Override
    public void lazyLoad() {

    }

    @Override
    public void initView(View root) {
        FragmentUtils.replace(getChildFragmentManager(), R.id.home_page_content, TopicListFragment.instance(null), true, "TopicListFragment");
        root.findViewById(R.id.home_page_content).post(new Runnable() {
            @Override
            public void run() {
                if (getChildFragmentManager().getFragments() != null) {
                    getChildFragmentManager().getFragments().get(0).setUserVisibleHint(mVisibleToUser);
                }
            }
        });
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
