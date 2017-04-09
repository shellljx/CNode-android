package com.licrafter.cnode.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.licrafter.cnode.R;
import com.licrafter.cnode.base.BaseFragment;
import com.licrafter.cnode.model.entity.Topic;
import com.licrafter.cnode.ui.activity.TopicDetailActivity;
import com.licrafter.cnode.utils.DateUtils;
import com.licrafter.cnode.utils.ImageLoader;
import com.licrafter.cnode.utils.TopicDividerDecoration;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * author: shell
 * date 2017/3/10 上午11:24
 **/
public class MineTopicListFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private List<Topic> mTopicList = new ArrayList<>();
    private TopicAdapter mAdapter = new TopicAdapter();

    @Override
    public int setContentView() {
        return R.layout.fragment_mine_topic_list;
    }

    @Override
    public void lazyLoad() {

    }

    public void refresh(List<Topic> topics) {
        mTopicList.clear();
        mTopicList.addAll(topics);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void initView(View root) {
        setRetainInstance(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new TopicDividerDecoration(getContext()));
        mRecyclerView.setAdapter(mAdapter);
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

    public class TopicAdapter extends RecyclerView.Adapter {

        static final int TYPE_ITEM = 0x001;
        static final int TYPE_EMPTY = 0x002;

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case TYPE_ITEM:
                    final TopicHolder holder = new TopicHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_topic, parent, false));
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TopicDetailActivity.start(getBaseActivity(), mTopicList.get(holder.getAdapterPosition()).getId());
                        }
                    });
                    return holder;
                case TYPE_EMPTY:
                    return new EmptyHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_empty, parent, false));
                default:
                    return null;
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof TopicHolder) {
                Topic topic = mTopicList.get(position);
                ((TopicHolder) holder).tv_last_reply.setText(DateUtils.format(topic.getLast_reply_at()));
                ((TopicHolder) holder).tv_title.setText(topic.getTitle());
                ImageLoader.loadUrl(((TopicHolder) holder).iv_avatar, topic.getAuthor().getAvatar_url());
            } else if (holder instanceof EmptyHolder) {
                ((EmptyHolder) holder).tv_info.setText(getString(R.string.empty_topics));
            }
        }

        @Override
        public int getItemCount() {
            return mTopicList.size() == 0 ? 1 : mTopicList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return mTopicList.size() == 0 ? TYPE_EMPTY : TYPE_ITEM;
        }
    }

    private class TopicHolder extends RecyclerView.ViewHolder {

        public RoundedImageView iv_avatar;
        TextView tv_title;
        TextView tv_last_reply;

        TopicHolder(View itemView) {
            super(itemView);
            iv_avatar = (RoundedImageView) itemView.findViewById(R.id.iv_avatar);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_last_reply = (TextView) itemView.findViewById(R.id.tv_last_reply);
            itemView.findViewById(R.id.tv_visit_count).setVisibility(View.GONE);
            itemView.findViewById(R.id.tv_reply_count).setVisibility(View.GONE);
            itemView.findViewById(R.id.tv_tag_normal).setVisibility(View.GONE);
        }
    }

    private class EmptyHolder extends RecyclerView.ViewHolder {
        TextView tv_info;

        EmptyHolder(View itemView) {
            super(itemView);
            tv_info = (TextView) itemView.findViewById(R.id.tv_info);
        }
    }
}
