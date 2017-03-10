package com.licrafter.cnode.ui.fragment;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.licrafter.cnode.R;
import com.licrafter.cnode.base.BaseFragment;
import com.licrafter.cnode.cache.UserCache;
import com.licrafter.cnode.model.UserDetailModel;
import com.licrafter.cnode.mvp.presenter.UserDetailPresenter;
import com.licrafter.cnode.mvp.view.MvpView;
import com.licrafter.cnode.utils.DateUtils;
import com.licrafter.cnode.utils.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;

/**
 * author: shell
 * date 2017/2/24 上午11:33
 **/
public class MineFragment extends BaseFragment implements MvpView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;

    @BindView(R.id.iv_avatar)
    RoundedImageView mAvatarView;
    @BindView(R.id.tv_user_name)
    TextView mUserNameView;
    @BindView(R.id.tv_github)
    TextView mGithubView;
    @BindView(R.id.tv_points)
    TextView mPointsView;
    @BindView(R.id.tv_register_at)
    TextView mRegisterTimeView;
    @BindView(R.id.layout_profit)
    RelativeLayout mProfitLayout;
    @BindView(R.id.tv_login)
    TextView mLoginView;

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    private UserDetailPresenter mPresenter;

    @Override
    public int setContentView() {
        return R.layout.fragment_mine;
    }

    @Override
    public void lazyLoad() {
//        if (UserCache.USER_NAME != null) {
//            mPresenter.getUserDetail(UserCache.USER_NAME);
//        } else {
//            mProfitLayout.setVisibility(View.INVISIBLE);
//            mLoginView.setVisibility(View.VISIBLE);
//        }
        mPresenter.getUserDetail("shellljx");

    }

    @Override
    public void initView(View root) {
        mViewPager.setAdapter(new Adapter(getChildFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void setListeners() {

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void bind() {
        mPresenter = new UserDetailPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void unBind() {

    }

    public void notifyGetUserSuccess(UserDetailModel model) {
        ImageLoader.loadUrl(mAvatarView, model.getData().getAvatar_url());
        mUserNameView.setText(model.getData().getLoginname());
        mGithubView.setText(String.format(getString(R.string.github_name), model.getData().getGithubUsername()));
        mPointsView.setText(String.format(getString(R.string.user_score), model.getData().getScore()));
        mRegisterTimeView.setText(String.format(getString(R.string.register_time), DateUtils.format(model.getData().getCreate_at())));
    }

    @Override
    public void onFailed() {

    }

    public class Adapter extends FragmentPagerAdapter {

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new MineTopicListFragment();
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "已创建";
                case 1:
                    return "已参与";
                default:
                    return "";
            }
        }
    }
}
