package com.licrafter.cnode.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.licrafter.cnode.R;
import com.licrafter.cnode.base.BaseFragment;
import com.licrafter.cnode.cache.UserCache;
import com.licrafter.cnode.model.UnReadCountModel;
import com.licrafter.cnode.model.UserDetailModel;
import com.licrafter.cnode.mvp.presenter.UserDetailPresenter;
import com.licrafter.cnode.mvp.view.MvpView;
import com.licrafter.cnode.ui.activity.LoginActivity;
import com.licrafter.cnode.ui.activity.NotificationCenterActivity;
import com.licrafter.cnode.utils.DateUtils;
import com.licrafter.cnode.utils.ImageLoader;
import com.licrafter.cnode.utils.SwipeRefreshUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;

/**
 * author: shell
 * date 2017/2/24 上午11:33
 **/
public class MineFragment extends BaseFragment implements MvpView, View.OnClickListener {

    private static final int REQ_LOGIN = 0x111;
    private static final int REQ_NOTIFICATION = 0x223;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefreshLayout;

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
    @BindView(R.id.tv_profit_name)
    TextView mTitle;
    @BindView(R.id.btn_settings)
    ImageButton mSettingsBtn;
    @BindView(R.id.btn_notification)
    ImageButton mNotificationBtn;
    @BindView(R.id.tv_dot)
    TextView mDot;

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    private Adapter mAdapter;
    private UserDetailPresenter mPresenter;

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
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
        return R.layout.fragment_mine;
    }

    @Override
    public void lazyLoad() {
    }

    @Override
    public void initView(View root) {
        mAdapter = new Adapter(getChildFragmentManager());
        SwipeRefreshUtils.initStyle(mRefreshLayout);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void setListeners() {
        mLoginView.setOnClickListener(this);
        mSettingsBtn.setOnClickListener(this);
        mNotificationBtn.setOnClickListener(this);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshUserProfit();
                mPresenter.getUnReadCount();
            }
        });

        mAppbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
                    mProfitLayout.setVisibility(View.INVISIBLE);
                } else if (mProfitLayout.getVisibility() != View.VISIBLE) {
                    mProfitLayout.setVisibility(View.VISIBLE);
                }
                if (Math.abs(verticalOffset) == 0) {
                    mRefreshLayout.setEnabled(true);
                } else if (mRefreshLayout.isEnabled()) {
                    mRefreshLayout.setRefreshing(false);
                    mRefreshLayout.setEnabled(false);
                }
                float alpha = (float) Math.abs(verticalOffset) / appBarLayout.getTotalScrollRange();
                mTitle.setAlpha(alpha);
            }
        });
    }

    @Override
    public void bind() {
        mPresenter = new UserDetailPresenter();
        mPresenter.attachView(this);
        refreshUserProfit();
        mPresenter.getUnReadCount();
    }

    private void refreshUserProfit() {
        if (UserCache.getUserName() != null) {
            mProfitLayout.setVisibility(View.VISIBLE);
            mLoginView.setVisibility(View.INVISIBLE);

            mTitle.setText(UserCache.getUserName());
            mPresenter.getUserDetail(UserCache.getUserName());
        } else {
            mProfitLayout.setVisibility(View.INVISIBLE);
            mLoginView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void unBind() {

    }

    public void notifyGetUserSuccess(UserDetailModel model) {
        mRefreshLayout.setRefreshing(false);
        ImageLoader.loadUrl(mAvatarView, model.getData().getAvatar_url());
        mUserNameView.setText(model.getData().getLoginname());
        mGithubView.setText(String.format(getString(R.string.github_name), model.getData().getGithubUsername()));
        mPointsView.setText(String.format(getString(R.string.user_score), model.getData().getScore()));
        mRegisterTimeView.setText(String.format(getString(R.string.register_time), DateUtils.format(model.getData().getCreate_at())));
        mAdapter.getPage(0).refresh(model.getData().getRecent_topics());
        mAdapter.getPage(1).refresh(model.getData().getRecent_replies());
    }

    @Override
    public void onFailed(Throwable e) {
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                startActivityForResult(new Intent(getActivity(), LoginActivity.class), REQ_LOGIN);
                break;
            case R.id.btn_settings:
                UserCache.clear();
                break;
            case R.id.btn_notification:
                if (UserCache.getUserName() == null) {
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), REQ_LOGIN);
                } else {
                    startActivityForResult(new Intent(getActivity(), NotificationCenterActivity.class), REQ_NOTIFICATION);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQ_LOGIN:
                    refreshUserProfit();
                    break;
                case REQ_NOTIFICATION:
                    mDot.setVisibility(View.GONE);
                    break;
            }
        }
    }

    public void notifyUnReadCount(UnReadCountModel model) {
        mRefreshLayout.setRefreshing(false);
        if (model.getData() != 0) {
            mDot.setVisibility(View.VISIBLE);
        } else {
            mDot.setVisibility(View.GONE);
        }
    }

    private class Adapter extends FragmentPagerAdapter {

        private SparseArray<MineTopicListFragment> fragmentArray = new SparseArray<>();

        Adapter(FragmentManager fm) {
            super(fm);
            fragmentArray.append(0, new MineTopicListFragment());
            fragmentArray.append(1, new MineTopicListFragment());
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentArray.get(position);
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

        MineTopicListFragment getPage(int position) {
            return fragmentArray.get(position);
        }
    }
}
