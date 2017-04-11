package com.licrafter.cnode.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.licrafter.cnode.R;
import com.licrafter.cnode.base.BaseFragment;
import com.licrafter.cnode.model.entity.TAB;

import butterknife.BindView;

/**
 * author: shell
 * date 2017/2/24 上午11:33
 **/
public class CategoryFragment extends BaseFragment {

    @BindView(R.id.categoryTabLayout)
    TabLayout mCategoryTabLayout;
    @BindView(R.id.categoryViewPager)
    ViewPager mCategoryViewPager;

    private CategoryAdapter mAdapter;


    public static CategoryFragment newInstance() {
        Bundle bundle = new Bundle();
        CategoryFragment fragment = new CategoryFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isAdded() && mAdapter != null) {
            mAdapter.setPrimaryFragmentUserHint(mVisibleToUser);
        }
    }

    @Override
    public int setContentView() {
        return R.layout.fragment_category;
    }

    @Override
    public void lazyLoad() {

    }

    @Override
    public void initView(View root) {
        mAdapter = new CategoryAdapter(getChildFragmentManager());
        mCategoryViewPager.setAdapter(mAdapter);
        mCategoryTabLayout.setupWithViewPager(mCategoryViewPager);
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

    private class CategoryAdapter extends FragmentPagerAdapter {

        private Fragment primaryFragment;

        private CategoryAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return TopicListFragment.instance(TAB.GOOD.getEnName());
                case 1:
                    return TopicListFragment.instance(TAB.SHARE.getEnName());
                case 2:
                    return TopicListFragment.instance(TAB.ASK.getEnName());
                case 3:
                    return TopicListFragment.instance(TAB.JOB.getEnName());
                default:
                    return null;
            }
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            primaryFragment = (Fragment) object;
            setPrimaryFragmentUserHint(mVisibleToUser);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return TAB.GOOD.getZhName();
                case 1:
                    return TAB.SHARE.getZhName();
                case 2:
                    return TAB.ASK.getZhName();
                case 3:
                    return TAB.JOB.getZhName();
                default:
                    return null;
            }
        }

        private void setPrimaryFragmentUserHint(boolean userHint) {
            if (primaryFragment != null) {
                primaryFragment.setUserVisibleHint(userHint);
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
