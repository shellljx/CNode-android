package com.licrafter.cnode.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.licrafter.cnode.R;
import com.licrafter.cnode.base.BaseFragment;

import butterknife.BindView;

/**
 * author: shell
 * date 2017/3/10 上午11:24
 **/
public class MineTopicListFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Override
    public int setContentView() {
        return R.layout.fragment_mine_topic_list;
    }

    @Override
    public void lazyLoad() {

    }

    @Override
    public void initView(View root) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(new TopicAdapter());
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

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new TopicHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_topic, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 30;
        }
    }

    public class TopicHolder extends RecyclerView.ViewHolder {

        public TopicHolder(View itemView) {
            super(itemView);
        }
    }
}
