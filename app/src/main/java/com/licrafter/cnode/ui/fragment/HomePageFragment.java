package com.licrafter.cnode.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.licrafter.cnode.R;
import com.licrafter.cnode.base.BaseFragment;
import com.licrafter.cnode.cache.UserCache;
import com.licrafter.cnode.ui.activity.LoginActivity;
import com.licrafter.cnode.ui.activity.MarkdownEditActivity;
import com.licrafter.cnode.utils.FragmentUtils;

import butterknife.BindView;

/**
 * author: shell
 * date 2017/2/27 下午3:12
 **/
public class HomePageFragment extends BaseFragment {

    @BindView(R.id.btn_create_topic)
    ImageButton mBtnCreate;

    public static HomePageFragment newInstance() {
        Bundle bundle = new Bundle();
        HomePageFragment fragment = new HomePageFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int setContentView() {
        return R.layout.fragment_home_page;
    }

    @Override
    public void lazyLoad() {

    }

    @Override
    public void initView(View root) {
        if (getChildFragmentManager().findFragmentByTag(TopicListFragment.class.getName()) == null) {
            TopicListFragment fragment = TopicListFragment.instance(null);
            fragment.setUserVisibleHint(true);
            FragmentUtils.replace(getChildFragmentManager(), R.id.home_page_content, fragment, false, TopicListFragment.class.getName());
        } else {
            FragmentUtils.findFragment(getChildFragmentManager(), TopicListFragment.class).setUserVisibleHint(true);
        }
    }

    @Override
    public void setListeners() {
        mBtnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserCache.getUserToken() != null) {
                    MarkdownEditActivity.createTopic(getBaseActivity());
                } else {
                    startActivity(new Intent(getBaseActivity(), LoginActivity.class));
                }
            }
        });
    }

    @Override
    public void bind() {

    }

    @Override
    public void unBind() {

    }
}
