package com.licrafter.cnode.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.licrafter.cnode.R;
import com.licrafter.cnode.base.BaseActivity;
import com.licrafter.cnode.base.BaseFragment;
import com.licrafter.cnode.model.TabModel;
import com.licrafter.cnode.model.entity.Topic;
import com.licrafter.cnode.mvp.presenter.TopicListPresenter;
import com.licrafter.cnode.mvp.view.MvpView;
import com.licrafter.cnode.ui.activity.TopicDetailActivity;
import com.licrafter.cnode.utils.DateUtils;
import com.licrafter.cnode.utils.ImageLoader;
import com.licrafter.cnode.utils.TopicDividerDecoration;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: shell
 * date 2017/2/24 上午11:30
 **/
public class TopicListFragment extends BaseFragment implements MvpView {

    private static final String TAG = TopicListFragment.class.getName();

    @BindView(R.id.topicRecyclerView)
    RecyclerView mTopicRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mRefreshLayout;

    private TopicListPresenter mPresenter = new TopicListPresenter();
    private TopicAdapter mAdapter = new TopicAdapter();
    private String mTab;
    private List<Topic> mTopicList = new ArrayList<>();

    public static TopicListFragment instance(String tab) {
        TopicListFragment fragment = new TopicListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tab", tab);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            mTab = getArguments().getString("tab");
        }
    }

    @Override
    public int setContentView() {
        return R.layout.fragment_topic_list;
    }

    @Override
    public void lazyLoad() {
        if (mTopicList.size() == 0) {
            mPresenter.refresh();
        }
    }

    @Override
    public void initView(View root) {
        mTopicRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mTopicRecyclerView.setHasFixedSize(true);
        mTopicRecyclerView.addItemDecoration(new TopicDividerDecoration(getContext()));
        mTopicRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setListeners() {
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refresh();
            }
        });

        mTopicRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                //加载更多
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int position = manager.findLastVisibleItemPosition();
                if (position == mTopicList.size()
                        && RecyclerView.SCROLL_STATE_IDLE == newState
                        && mPresenter.hasNextPage()) {
                    mPresenter.loadNextPage();
                }
            }
        });
    }

    @Override
    public void bind() {
        mPresenter.attachView(this);
        mPresenter.setTab(mTab);
    }

    @Override
    public void unBind() {
        mPresenter.detachView();
    }

    @Override
    public void onFailed() {
        Toast.makeText(getContext(), "加载错误，请重试!", Toast.LENGTH_SHORT).show();
        mRefreshLayout.setRefreshing(false);
    }

    public void notifySuccess(TabModel tabModel, boolean refresh) {
        mRefreshLayout.setRefreshing(false);
        if (refresh) {
            mTopicList.clear();
        }
        mTopicList.addAll(tabModel.getData());
        mAdapter.notifyDataSetChanged();
    }

    private class TopicAdapter extends RecyclerView.Adapter {
        private int TYPE_ITEM = 0x001;
        private int TYPE_FOOTER = 0x002;

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_ITEM) {
                final TopicHolder holder = new TopicHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_topic, parent, false));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TopicDetailActivity.start((BaseActivity) getActivity(), mTopicList.get(holder.getAdapterPosition()).getId());
                    }
                });
                return holder;
            } else {
                return new FooterHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_footer, parent, false));
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof TopicHolder) {
                TopicHolder topicHolder = (TopicHolder) holder;
                Topic topic = mTopicList.get(position);
                ImageLoader.loadUrl(topicHolder.avatar, topic.getAuthor().getAvatar_url());
                if (topic.isTop()) {
                    topicHolder.tag.setText("置顶");
                } else if (topic.isGood()) {
                    topicHolder.tag.setText("精华");
                } else {
                    topicHolder.tagNormal.setText(parseTabName(topic.getTab()));
                }
                topicHolder.tag.setVisibility((topic.isGood() || topic.isTop()) ? View.VISIBLE : View.GONE);
                topicHolder.tagNormal.setVisibility((topic.isGood() || topic.isTop()) ? View.GONE : View.VISIBLE);
                topicHolder.title.setText(topic.getTitle());
                topicHolder.lastReply.setText(String.format(getString(R.string.last_reply), DateUtils.format(topic.getLast_reply_at())));
                topicHolder.replyCount.setText(String.valueOf(topic.getReply_count()));
                topicHolder.visitCount.setText(String.valueOf(topic.getVisit_count()));
            } else if (holder instanceof FooterHolder) {
                ((FooterHolder) holder).footerInfo.setText(mPresenter.hasNextPage() ? "加载中..." : "已加载全部内容!");
            }

        }

        @Override
        public int getItemCount() {
            return mTopicList.size() + 1;
        }

        @Override
        public int getItemViewType(int position) {
            if (position < mTopicList.size()) {
                return TYPE_ITEM;
            } else {
                return TYPE_FOOTER;
            }
        }
    }

    public class TopicHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_avatar)
        RoundedImageView avatar;
        @BindView(R.id.tv_tag)
        TextView tag;
        @BindView(R.id.tv_tag_normal)
        TextView tagNormal;
        @BindView(R.id.tv_title)
        TextView title;
        @BindView(R.id.tv_last_reply)
        TextView lastReply;
        @BindView(R.id.tv_reply_count)
        TextView replyCount;
        @BindView(R.id.tv_visit_count)
        TextView visitCount;

        private TopicHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class FooterHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.footerInfo)
        TextView footerInfo;

        private FooterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public String parseTabName(String tab) {
        if (TextUtils.isEmpty(tab)) {
            return "未知";
        }
        switch (tab) {
            case "share":
                return "分享";
            case "ask":
                return "问答";
            case "job":
                return "招聘";
            case "good":
                return "精华";
            default:
                return "";
        }
    }
}
