package com.licrafter.cnode.ui.activity;


import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.MenuItem;
import android.view.WindowManager;

import com.licrafter.cnode.R;
import com.licrafter.cnode.base.BaseActivity;
import com.licrafter.cnode.ui.fragment.CategoryFragment;
import com.licrafter.cnode.ui.fragment.HomePageFragment;
import com.licrafter.cnode.ui.fragment.MineFragment;
import com.licrafter.cnode.ui.fragment.NotificationFragment;
import com.licrafter.cnode.ui.fragment.TopicListFragment;
import com.licrafter.cnode.widget.NotScrollViewPager;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.bottomNavigationView)
    BottomNavigationView mBottomNavigationView;
    @BindView(R.id.contentViewPager)
    NotScrollViewPager mContentViewPager;


    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mContentViewPager.setAdapter(new ContentAdapter(getSupportFragmentManager()));
        mContentViewPager.setOffscreenPageLimit(4);
    }

    @Override
    public void setListeners() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_home_page:
                        mContentViewPager.setCurrentItem(0, false);
                        break;
                    case R.id.item_category:
                        mContentViewPager.setCurrentItem(1, false);
                        break;
                    case R.id.item_notification:
                        mContentViewPager.setCurrentItem(2, false);
                        break;
                    case R.id.item_mine:
                        mContentViewPager.setCurrentItem(3, false);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void bind() {

    }

    @Override
    public void unBind() {

    }

    public class ContentAdapter extends FragmentPagerAdapter {

        public ContentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new HomePageFragment();
                case 1:
                    return new CategoryFragment();
                case 2:
                    return new NotificationFragment();
                case 3:
                    return new MineFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
