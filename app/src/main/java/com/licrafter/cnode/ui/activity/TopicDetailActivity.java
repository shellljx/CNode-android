package com.licrafter.cnode.ui.activity;

import android.content.Intent;
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
import com.licrafter.cnode.model.entity.TopicDetail;
import com.licrafter.cnode.mvp.presenter.TopicDetailPresenter;
import com.licrafter.cnode.mvp.view.MvpView;
import com.licrafter.cnode.utils.DateUtils;
import com.licrafter.cnode.utils.DipConvertUtils;
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

    @BindView(R.id.detailRecyclerView)
    RecyclerView mDetailRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefreshLayout;

    private TopicDetailPresenter mPresenter = new TopicDetailPresenter();
    private DetailAdapter mAdapter;
    private TopicDetailModel mDetail;
    private String mTopicId;
    private int mScrolledY;
    private int mMaxScroll;

    @Override
    public int getContentView() {
        return R.layout.activity_topic_detail;
    }

    @Override
    public void initView() {
        if (getIntent() != null) {
            mTopicId = getIntent().getStringExtra("topicId");
        }
        mMaxScroll = DipConvertUtils.dip2px(this, 150);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.title_topic_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

        mDetailRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mScrolledY += dy;
                if (mScrolledY >= mMaxScroll) {
                    mToolbar.setTitle(mDetail.getData().getTitle());
                } else {
                    mToolbar.setTitle(getString(R.string.title_topic_detail));
                }
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
    public void onFailed() {
        mRefreshLayout.setRefreshing(false);
    }

    public void notifyGetDetailSuccess(TopicDetailModel topicDetailModel) {
        mRefreshLayout.setRefreshing(false);
        mDetail = topicDetailModel;
        mAdapter.notifyDataSetChanged();
    }

    public class DetailAdapter extends RecyclerView.Adapter {

        private static final int TYPE_HEADER = 0x001;
        private static final int TYPE_ITEM = 0x002;

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case TYPE_HEADER:
                    return new DetailHeaderHolder(LayoutInflater.from(TopicDetailActivity.this).inflate(R.layout.item_detail_header, parent, false));
                case TYPE_ITEM:
                    return new ReplyHolder(LayoutInflater.from(TopicDetailActivity.this).inflate(R.layout.item_reply, parent, false));
                default:
                    return null;
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof DetailHeaderHolder) {
                TopicDetail detail = mDetail.getData();
                DetailHeaderHolder header = (DetailHeaderHolder) holder;
                ImageLoader.loadUrl(header.iv_avatar, detail.getAuthor().getAvatar_url());
                header.tv_title.setText(detail.getTitle());
                header.tv_user_name.setText(detail.getAuthor().getLoginname());
                header.mk_content.loadHtml(detail.getContent());
                header.tv_created_at.setText(DateUtils.format(detail.getCreate_at()));
                header.tv_visit_count.setText(String.format(getString(R.string.visit_count), detail.getVisit_count()));
                header.tv_tab.setText(String.format(getString(R.string.tab_name), detail.getTab()));
            } else if (holder instanceof ReplyHolder) {
                ReplyHolder reply = (ReplyHolder) holder;
                Reply rep = mDetail.getData().getReplies().get(position - 1);
                ImageLoader.loadUrl(reply.iv_avatar, rep.getAuthor().getAvatar_url());
                reply.tv_user_name.setText(rep.getAuthor().getLoginname());
                reply.tv_created_at.setText(DateUtils.format(rep.getCreate_at()));
                reply.tv_content.setRichText(rep.getContent());
            }
        }

        @Override
        public int getItemCount() {
            if (mDetail != null) {
                return mDetail.getData().getReplies().size() + 1;
            } else {
                return 0;
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return TYPE_HEADER;
            } else {
                return TYPE_ITEM;
            }
        }
    }

    public class DetailHeaderHolder extends RecyclerView.ViewHolder {

        private RoundedImageView iv_avatar;
        private TextView tv_user_name;
        private TextView tv_title;
        private TextView tv_created_at;
        private TextView tv_visit_count;
        private TextView tv_tab;
        private CNodeWebView mk_content;

        public DetailHeaderHolder(View itemView) {
            super(itemView);
            iv_avatar = (RoundedImageView) itemView.findViewById(R.id.iv_avatar);
            tv_user_name = (TextView) itemView.findViewById(R.id.tv_user_name);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_created_at = (TextView) itemView.findViewById(R.id.tv_created_at);
            tv_visit_count = (TextView) itemView.findViewById(R.id.tv_visit_count);
            tv_tab = (TextView) itemView.findViewById(R.id.tv_tab);
            mk_content = (CNodeWebView) itemView.findViewById(R.id.wv_content);
        }
    }

    public class ReplyHolder extends RecyclerView.ViewHolder {

        public RoundedImageView iv_avatar;
        public TextView tv_user_name;
        public TextView tv_created_at;
        public RichTextView tv_content;

        public ReplyHolder(View itemView) {
            super(itemView);
            iv_avatar = (RoundedImageView) itemView.findViewById(R.id.iv_avatar);
            tv_user_name = (TextView) itemView.findViewById(R.id.tv_user_name);
            tv_created_at = (TextView) itemView.findViewById(R.id.tv_created_at);
            tv_content = (RichTextView) itemView.findViewById(R.id.tv_content);
        }
    }
}
