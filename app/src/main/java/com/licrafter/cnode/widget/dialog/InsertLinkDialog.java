package com.licrafter.cnode.widget.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.licrafter.cnode.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lijx on 2017/4/10.
 */

public class InsertLinkDialog extends AlertDialog implements View.OnClickListener {

    @BindView(R.id.inputNameHint)
    TextInputLayout mTitleHint;
    @BindView(R.id.inputHint)
    TextInputLayout mLinkHint;
    @BindView(R.id.titleInput)
    TextInputEditText mTitle;
    @BindView(R.id.linkInput)
    TextInputEditText mLink;
    @BindView(R.id.confirm)
    TextView mConfirm;
    @BindView(R.id.cancel)
    TextView mCancel;

    private OnClickListener mListener;

    public InsertLinkDialog(@NonNull Context context, OnClickListener listener) {
        super(context);
        mListener = listener;
        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_insert_link_dialog, null);
        ButterKnife.bind(this, rootView);
        setTitle("插入链接");
        setView(rootView);
        mConfirm.setOnClickListener(this);
        mCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm:
                String titleStr = mTitle.getText().toString().trim();
                String linkStr = mLink.getText().toString().trim();

                if (TextUtils.isEmpty(titleStr)) {
                    mTitleHint.setError("不能为空");
                    return;
                }
                if (TextUtils.isEmpty(linkStr)) {
                    mLinkHint.setError("不能为空");
                    return;
                }

                if (mTitleHint.isErrorEnabled())
                    mTitleHint.setErrorEnabled(false);
                if (mLinkHint.isErrorEnabled())
                    mLinkHint.setErrorEnabled(false);

                if (mListener != null) {
                    mListener.confirm(titleStr, linkStr);
                }
                dismiss();
                break;
            case R.id.cancel:
                if (mListener!=null){
                    mListener.cancel();
                }
                dismiss();
                break;
        }
    }


    public interface OnClickListener {
        void confirm(String title, String url);

        void cancel();
    }
}
