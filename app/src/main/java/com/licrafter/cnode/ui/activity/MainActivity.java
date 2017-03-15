package com.licrafter.cnode.ui.activity;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.view.menu.MenuItemImpl;
import android.view.Menu;
import android.view.MenuItem;

import com.licrafter.cnode.R;
import com.licrafter.cnode.base.BaseActivity;
import com.licrafter.cnode.base.BaseFragment;
import com.licrafter.cnode.ui.fragment.CategoryFragment;
import com.licrafter.cnode.ui.fragment.HomePageFragment;
import com.licrafter.cnode.ui.fragment.MineFragment;
import com.licrafter.cnode.ui.fragment.NotificationFragment;

import com.licrafter.cnode.utils.FragmentUtils;
import com.licrafter.cnode.widget.NotScrollViewPager;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.bottomNavigationView)
    BottomNavigationView mBottomNavigationView;
    @BindView(R.id.contentViewPager)
    NotScrollViewPager mContentViewPager;

    private BaseFragment[] mFragments = new BaseFragment[4];
    private int mCurrPosition;


    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        if (savedInstanceState == null) {
            mFragments = new BaseFragment[4];
            mFragments[0] = HomePageFragment.newInstance();
            mFragments[1] = CategoryFragment.newInstance();
            mFragments[2] = NotificationFragment.newInstance();
            mFragments[3] = MineFragment.newInstance();
            FragmentUtils.addMultiple(getSupportFragmentManager(), R.id.content, mCurrPosition, mFragments);
        } else {
            mCurrPosition = savedInstanceState.getInt("currPosition");
            mFragments[0] = findFragment(HomePageFragment.class);
            mFragments[1] = findFragment(CategoryFragment.class);
            mFragments[2] = findFragment(NotificationFragment.class);
            mFragments[3] = findFragment(MineFragment.class);

            if (mCurrPosition != 0) {
                updateNavigationBarState(mCurrPosition);
            }
        }
    }

    @Override
    public void setListeners() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mCurrPosition = item.getItemId();
                switch (item.getItemId()) {
                    case R.id.item_home_page:
                        showHideFragment(0);
                        break;
                    case R.id.item_category:
                        showHideFragment(1);
                        break;
                    case R.id.item_notification:
                        showHideFragment(2);
                        break;
                    case R.id.item_mine:
                        showHideFragment(3);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currPosition", mCurrPosition);
    }

    @Override
    public void bind() {

    }

    @Override
    public void unBind() {

    }

    private void showHideFragment(int position) {
        mFragments[position].setUserVisibleHint(true);
        FragmentUtils.showHideFragment(getSupportFragmentManager(), mFragments[position], null);
    }

    private <T extends BaseFragment> T findFragment(Class<T> tClass) {
        return FragmentUtils.findFragment(getSupportFragmentManager(), tClass);
    }

    private void updateNavigationBarState(int actionId) {
        Menu menu = mBottomNavigationView.getMenu();
        for (int i = 0, size = menu.size(); i < size; i++) {
            MenuItem menuItem = menu.getItem(i);
            ((MenuItemImpl) menuItem).setExclusiveCheckable(false);
            menuItem.setChecked(menuItem.getItemId() == actionId);
            ((MenuItemImpl) menuItem).setExclusiveCheckable(true);
        }
    }
}
