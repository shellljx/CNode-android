package com.licrafter.cnode.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.licrafter.cnode.R;
import com.licrafter.cnode.base.BaseFragment;
import com.licrafter.cnode.ui.activity.MarkdownEditActivity;
import com.licrafter.cnode.widget.richEditView.engine.PerformEditable;

import butterknife.BindView;

/**
 * Created by lijx on 2017/4/5.
 */

public class MdEditFragment extends BaseFragment {

    @BindView(R.id.input_content)
    EditText mInputContent;
    @BindView(R.id.input_title)
    EditText mInputTitle;

    private PerformEditable mPerformEditable;
    private int mType = MarkdownEditActivity.NEW_TOPIC;
    private String mAuthor;

    public static MdEditFragment newInstance(int type, String author) {
        MdEditFragment fragment = new MdEditFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putString("author", author);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getInt("type");
            mAuthor = getArguments().getString("author");
        }
    }

    @Override
    public int setContentView() {
        return R.layout.fragment_md_edit;
    }

    @Override
    public void lazyLoad() {

    }


    @Override
    public void initView(View root) {
        mPerformEditable = new PerformEditable(mInputContent);
        if (mType == MarkdownEditActivity.NEW_TOPIC) {
            mInputTitle.setVisibility(View.VISIBLE);
            mInputTitle.requestFocus();
        } else {
            mInputTitle.setVisibility(View.GONE);
            mInputContent.requestFocus();
        }
        if (!TextUtils.isEmpty(mAuthor)) {
            mInputContent.setText("@" + mAuthor);
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

    public PerformEditable getPerformEditable() {
        return mPerformEditable;
    }

    public String getContent() {
        return mInputContent.getText().toString();
    }

    public String getTitle() {
        return mInputTitle.getText().toString();
    }
}
