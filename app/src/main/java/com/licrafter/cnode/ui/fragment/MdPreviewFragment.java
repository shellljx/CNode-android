package com.licrafter.cnode.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.licrafter.cnode.R;
import com.licrafter.cnode.base.BaseFragment;
import com.licrafter.cnode.ui.activity.MarkdownEditActivity;
import com.licrafter.cnode.widget.MarkdownPreview;

import butterknife.BindView;

/**
 * Created by lijx on 2017/4/5.
 */

public class MdPreviewFragment extends BaseFragment {

    @BindView(R.id.markdownPreview)
    MarkdownPreview mMarkdownPreview;
    @BindView(R.id.title)
    TextView mTitleView;

    private String mContent;
    private boolean mPageLoadFinished;
    private int mType = MarkdownEditActivity.NEW_TOPIC;

    public static MdPreviewFragment newInstance(int type) {
        MdPreviewFragment fragment = new MdPreviewFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getInt("type");
        }
    }

    @Override
    public int setContentView() {
        return R.layout.fragment_md_preview;
    }

    @Override
    public void lazyLoad() {

    }

    @Override
    public void initView(View root) {
        mTitleView.setVisibility(mType == MarkdownEditActivity.NEW_TOPIC ? View.VISIBLE : View.GONE);
        mMarkdownPreview.setOnLoadingFinishListener(new MarkdownPreview.OnLoadingFinishListener() {
            @Override
            public void onLoadingFinish() {
                mPageLoadFinished = true;
            }
        });
    }

    public void refresh(String title, String content) {
        mContent = content;
        if (mPageLoadFinished && mContent != null) {
            mMarkdownPreview.parseMarkdown(mContent, true);
        }
        if (title != null) {
            mTitleView.setText(title);
        }
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
}
