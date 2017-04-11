package com.licrafter.cnode.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.licrafter.cnode.R;
import com.licrafter.cnode.base.BaseActivity;
import com.licrafter.cnode.model.TopicDetailModel;
import com.licrafter.cnode.model.entity.Reply;
import com.licrafter.cnode.model.entity.TAB;
import com.licrafter.cnode.model.entity.TopicDetail;
import com.licrafter.cnode.mvp.presenter.TopicDetailPresenter;
import com.licrafter.cnode.mvp.view.MvpView;
import com.licrafter.cnode.utils.DateUtils;
import com.licrafter.cnode.utils.ImageLoader;
import com.licrafter.cnode.utils.SwipeRefreshUtils;
import com.licrafter.cnode.widget.CNodeWebView;
import com.licrafter.cnode.widget.richTextView.RichTextView;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;

/**
 * author: shell
 * date 2017/2/27 下午3:53
 **/
public class TopicDetailActivity extends BaseActivity implements MvpView {

    @BindView(R.id.comments_recyclerview)
    RecyclerView mDetailRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.layout_bottom_sheet)
    View mBottomSheet;

    //header
    @BindView(R.id.iv_avatar)
    RoundedImageView iv_avatar;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_created_at)
    TextView tv_created_at;
    @BindView(R.id.tv_visit_count)
    TextView tv_visit_count;
    @BindView(R.id.tv_tab)
    TextView tv_tab;
    @BindView(R.id.wv_content)
    CNodeWebView mk_content;

    private TopicDetailPresenter mPresenter = new TopicDetailPresenter();
    private DetailAdapter mAdapter;
    private TopicDetailModel mDetail;
    private String mTopicId;

    @Override
    public int getContentView() {
        return R.layout.activity_topic_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        if (getIntent() != null) {
            mTopicId = getIntent().getStringExtra("topicId");
        }
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.title_topic_detail);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        SwipeRefreshUtils.initStyle(mRefreshLayout);
        mAdapter = new DetailAdapter();
        mDetailRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDetailRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setListeners() {
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getTopicDetailById(mTopicId);
            }
        });

        BottomSheetBehavior behavior = BottomSheetBehavior.from(mBottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        mToolbar.setTitle(getString(R.string.title_topic_detail));
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        if (mDetail != null) {
                            mToolbar.setTitle(mDetail.getData().getTitle());
                        }
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    @Override
    public void bind() {
        mPresenter.attachView(this);
        mRefreshLayout.setRefreshing(true);
        mPresenter.getTopicDetailById(mTopicId);
    }

    @Override
    public void unBind() {

    }

    public static void start(BaseActivity activity, String topicId) {
        Intent intent = new Intent(activity, TopicDetailActivity.class);
        intent.putExtra("topicId", topicId);
        activity.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_topic_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFailed(Throwable e) {
        mRefreshLayout.setRefreshing(false);
    }

    public void notifyGetDetailSuccess(TopicDetailModel topicDetailModel) {
        mRefreshLayout.setRefreshing(false);
        mDetail = topicDetailModel;
        initHeader();
        mAdapter.notifyDataSetChanged();
    }

    private void initHeader() {
        TopicDetail detail = mDetail.getData();
        ImageLoader.loadUrl(iv_avatar, detail.getAuthor().getAvatar_url());
        tv_title.setText(detail.getTitle());
        tv_user_name.setText(detail.getAuthor().getLoginname());
        mk_content.loadHtml(detail.getContent());
        tv_created_at.setText(DateUtils.format(detail.getCreate_at()));
        tv_visit_count.setText(String.format(getString(R.string.visit_count), detail.getVisit_count()));
        tv_tab.setText(String.format(getString(R.string.tab_name), TAB.ValueOf(detail.getTab())));
    }

    private class DetailAdapter extends RecyclerView.Adapter {

        private static final int TYPE_HEADER = 0x001;
        private static final int TYPE_ITEM = 0x002;
        private static final int TYPE_EMPTY = 0x003;

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case TYPE_HEADER:
                    return new ReplyCountHolder(LayoutInflater.from(TopicDetailActivity.this).inflate(R.layout.item_reply_count, parent, false));
                case TYPE_ITEM:
                    return new ReplyHolder(LayoutInflater.from(TopicDetailActivity.this).inflate(R.layout.item_reply, parent, false));
                case TYPE_EMPTY:
                    return new EmptyHolder(LayoutInflater.from(TopicDetailActivity.this).inflate(R.layout.item_empty, parent, false));
                default:
                    return null;
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ReplyHolder) {
                ReplyHolder reply = (ReplyHolder) holder;
                Reply rep = mDetail.getData().getReplies().get(position - 1);
                ImageLoader.loadUrl(reply.iv_avatar, rep.getAuthor().getAvatar_url());
                reply.tv_user_name.setText(rep.getAuthor().getLoginname());
                reply.tv_created_at.setText(DateUtils.format(rep.getCreate_at()));
                reply.tv_content.setRichText(rep.getContent());
            } else if (holder instanceof ReplyCountHolder) {
                ((ReplyCountHolder) holder).tv_reply_count.setText(String.format(getString(R.string.reply_count), mDetail.getData().getReplies().size()));
            }
        }

        @Override
        public int getItemCount() {
            if (mDetail != null) {
                if (mDetail.getData().getReplies().size() != 0) {
                    return mDetail.getData().getReplies().size() + 1;
                } else {
                    return 2;
                }
            } else {
                return 0;
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return TYPE_HEADER;
            } else {
                if (mDetail.getData().getReplies().size() != 0) {
                    return TYPE_ITEM;
                } else {
                    return TYPE_EMPTY;
                }
            }
        }
    }

    private class ReplyHolder extends RecyclerView.ViewHolder {

        public RoundedImageView iv_avatar;
        public TextView tv_user_name;
        TextView tv_created_at;
        RichTextView tv_content;

        ReplyHolder(View itemView) {
            super(itemView);
            iv_avatar = (RoundedImageView) itemView.findViewById(R.id.iv_avatar);
            tv_user_name = (TextView) itemView.findViewById(R.id.tv_user_name);
            tv_created_at = (TextView) itemView.findViewById(R.id.tv_created_at);
            tv_content = (RichTextView) itemView.findViewById(R.id.tv_content);
        }
    }

    private class ReplyCountHolder extends RecyclerView.ViewHolder {

        TextView tv_reply_count;

        ReplyCountHolder(View itemView) {
            super(itemView);
            tv_reply_count = (TextView) itemView.findViewById(R.id.tv_reply_count);
        }
    }

    private class EmptyHolder extends RecyclerView.ViewHolder {

        TextView tv_info;

        EmptyHolder(View itemView) {
            super(itemView);
            tv_info = (TextView) itemView.findViewById(R.id.tv_info);
            tv_info.setText(getString(R.string.empty_comments));
        }
    }
}
