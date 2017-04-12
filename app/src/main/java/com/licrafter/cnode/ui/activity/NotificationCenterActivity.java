package com.licrafter.cnode.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.licrafter.cnode.R;
import com.licrafter.cnode.base.BaseActivity;
import com.licrafter.cnode.model.NotificationModel;
import com.licrafter.cnode.model.entity.Notification;
import com.licrafter.cnode.model.entity.TopicDetail;
import com.licrafter.cnode.mvp.presenter.NotificationCenterPresenter;
import com.licrafter.cnode.mvp.view.MvpView;
import com.licrafter.cnode.utils.DateUtils;
import com.licrafter.cnode.utils.ImageLoader;
import com.licrafter.cnode.utils.SwipeRefreshUtils;
import com.licrafter.cnode.utils.TopicDividerDecoration;
import com.licrafter.cnode.widget.CNodeWebView;
import com.licrafter.cnode.widget.richTextView.RichTextView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: shell
 * date 2017/3/16 下午2:25
 **/
public class NotificationCenterActivity extends BaseActivity implements MvpView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefreshLayout;

    private NotificationAdapter mAdapter;
    private NotificationCenterPresenter mPresenter;
    private List<Notification> mNotificationList = new ArrayList<>();

    @Override
    public int getContentView() {
        return R.layout.activity_notification_center;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.title_notification_center);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        SwipeRefreshUtils.initStyle(mRefreshLayout);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new NotificationAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new TopicDividerDecoration(this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_OK);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void setListeners() {
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getAllNotification();
            }
        });
    }

    @Override
    public void bind() {
        mPresenter = new NotificationCenterPresenter();
        mPresenter.attachView(this);
        mRefreshLayout.setRefreshing(true);
        mPresenter.getAllNotification();
    }

    @Override
    public void unBind() {
        mPresenter.detachView();
    }

    public void stopRefresh() {
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onFailed(Throwable e) {

    }

    public void notifyGetSuccess(NotificationModel model) {
        mPresenter.readAll();
        mNotificationList.clear();
        mNotificationList.addAll(model.getData().getHasnot_read_messages());
        mNotificationList.addAll(model.getData().getHas_read_messages());
        mAdapter.notifyDataSetChanged();
    }

    private class NotificationAdapter extends RecyclerView.Adapter<NotificationHolder> {

        @Override
        public NotificationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final NotificationHolder holder = new NotificationHolder(LayoutInflater.from(NotificationCenterActivity.this)
                    .inflate(R.layout.item_notification, parent, false));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TopicDetailActivity.start(NotificationCenterActivity.this
                            , mNotificationList.get(holder.getAdapterPosition()).getTopic().getId());
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(NotificationHolder holder, int position) {
            Notification notification = mNotificationList.get(position);
            ImageLoader.loadUrl(holder.iv_avatar, notification.getAuthor().getAvatar_url());
            holder.tv_user_name.setText(notification.getAuthor().getLoginname());
            holder.tv_action.setText(notification.getType().equals("at") ? "@了你" : "回复了你");
            holder.tv_created_at.setText(DateUtils.format(notification.getReply().getCreate_at()));
            holder.tv_content.setRichText(notification.getReply().getContent());
            holder.tv_topic.setText(notification.getTopic().getTitle());
        }

        @Override
        public int getItemCount() {
            return mNotificationList.size();
        }
    }

    class NotificationHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_avatar)
        RoundedImageView iv_avatar;
        @BindView(R.id.tv_user_name)
        TextView tv_user_name;
        @BindView(R.id.tv_action)
        TextView tv_action;
        @BindView(R.id.tv_created_at)
        TextView tv_created_at;
        @BindView(R.id.tv_content)
        RichTextView tv_content;
        @BindView(R.id.tv_topic)
        TextView tv_topic;

        public NotificationHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
