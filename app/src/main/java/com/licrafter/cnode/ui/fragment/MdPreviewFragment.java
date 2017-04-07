package com.licrafter.cnode.ui.fragment;

import android.view.View;
import android.widget.TextView;

import com.licrafter.cnode.R;
import com.licrafter.cnode.base.BaseFragment;
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


    @Override
    public int setContentView() {
        return R.layout.fragment_md_preview;
    }

    @Override
    public void lazyLoad() {

    }

    @Override
    public void initView(View root) {
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
