package com.licrafter.cnode.ui.activity;

import com.licrafter.cnode.R;
import com.licrafter.cnode.base.BaseActivity;
import com.licrafter.cnode.ui.fragment.HomePageFragment;
import com.licrafter.cnode.utils.FragmentUtils;

/**
 * author: shell
 * date 2017/2/27 下午3:53
 **/
public class TopicDetailActivity extends BaseActivity {

    @Override
    public int getContentView() {
        return R.layout.activity_topic_detail;
    }

    @Override
    public void initView() {
        FragmentUtils.replace(getSupportFragmentManager(),R.id.layout, new HomePageFragment(),false,"TopicListFragment");
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
