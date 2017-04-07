package com.licrafter.cnode.ui.fragment;

import android.view.View;
import android.widget.EditText;

import com.licrafter.cnode.R;
import com.licrafter.cnode.base.BaseFragment;
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
