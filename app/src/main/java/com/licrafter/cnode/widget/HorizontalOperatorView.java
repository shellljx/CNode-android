package com.licrafter.cnode.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import com.licrafter.cnode.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lijx on 2017/4/10.
 */

public class HorizontalOperatorView extends HorizontalScrollView implements View.OnClickListener {

    @BindView(R.id.font_blod)
    ImageView mFontBlod;
    @BindView(R.id.font_italic)
    ImageView mFontItalic;
    @BindView(R.id.quote)
    ImageView mOpQuote;
    @BindView(R.id.list_ul)
    ImageView mOpUl;
    @BindView(R.id.list_ol)
    ImageView mOpOl;
    @BindView(R.id.op_link)
    ImageView mOpLink;
    @BindView(R.id.op_image)
    ImageView mOpImage;
    @BindView(R.id.op_code)
    ImageView mOpCode;
    @BindView(R.id.op_preview)
    ImageView mOpPreview;

    private OperationListener mListener;

    public HorizontalOperatorView(Context context) {
        this(context, null);
    }

    public HorizontalOperatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalOperatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_operator, this, true);
        ButterKnife.bind(this, rootView);

        mFontBlod.setOnClickListener(this);
        mFontItalic.setOnClickListener(this);
        mOpQuote.setOnClickListener(this);
        mOpUl.setOnClickListener(this);
        mOpOl.setOnClickListener(this);
        mOpLink.setOnClickListener(this);
        mOpImage.setOnClickListener(this);
        mOpCode.setOnClickListener(this);
        mOpPreview.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.operation(v);
        }
    }

    public void setOperationListener(OperationListener listener) {
        mListener = listener;
    }

    public interface OperationListener {
        public void operation(View view);
    }
}
