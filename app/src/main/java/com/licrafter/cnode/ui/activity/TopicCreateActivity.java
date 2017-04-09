package com.licrafter.cnode.ui.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.bilibili.boxing.Boxing;
import com.bilibili.boxing.model.config.BoxingConfig;
import com.bilibili.boxing.model.entity.BaseMedia;
import com.bilibili.boxing.model.entity.impl.ImageMedia;
import com.bilibili.boxing.utils.ImageCompressor;
import com.bilibili.boxing_impl.ui.BoxingActivity;
import com.licrafter.cnode.R;
import com.licrafter.cnode.base.BaseActivity;
import com.licrafter.cnode.ui.fragment.MdEditFragment;
import com.licrafter.cnode.ui.fragment.MdPreviewFragment;
import com.licrafter.cnode.utils.FragmentUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * author: shell
 * date 2017/4/3 下午9:56
 **/
public class TopicCreateActivity extends BaseActivity implements View.OnClickListener {

    private static final int REQUEST_CODE = 1024;
    private static final int COMPRESS_REQUEST_CODE = 2048;

    @BindView(R.id.inputLayout)
    ScrollView mInputLayout;
    @BindView(R.id.space)
    View mSpace;
    @BindView(R.id.spinner)
    Spinner mSpinner;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

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

    private MdEditFragment mEditFragment;
    private MdPreviewFragment mPreviewFragment;
    private boolean mIsPreview;

    @Override
    public int getContentView() {
        return R.layout.activity_topic_create;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.menu_send_post, menu);
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
    public void initView(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.title_topic_create);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (savedInstanceState == null) {
            mEditFragment = new MdEditFragment();
            mPreviewFragment = new MdPreviewFragment();
            FragmentUtils.addMultiple(getSupportFragmentManager(), R.id.mdContent, 0, mEditFragment, mPreviewFragment);
        } else {
            mEditFragment = FragmentUtils.findFragment(getSupportFragmentManager(), MdEditFragment.class);
        }
    }

    @Override
    public void setListeners() {
        mFontBlod.setOnClickListener(this);
        mFontItalic.setOnClickListener(this);
        mOpQuote.setOnClickListener(this);
        mOpUl.setOnClickListener(this);
        mOpOl.setOnClickListener(this);
        mOpLink.setOnClickListener(this);
        mOpImage.setOnClickListener(this);
        mOpCode.setOnClickListener(this);
        mOpPreview.setOnClickListener(this);
        mInputLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                mSpace.setLayoutParams(new LinearLayout.LayoutParams(1, Math.abs(rect.bottom - getWindowManager().getDefaultDisplay().getHeight())));
            }
        });
    }

    @Override
    public void bind() {

    }

    @Override
    public void unBind() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.font_blod:
            case R.id.font_italic:
            case R.id.quote:
            case R.id.list_ul:
            case R.id.list_ol:
            case R.id.op_code:
                mEditFragment.getPerformEditable().onClick(v);
                break;
            case R.id.op_link:
                insertLink();
                break;
            case R.id.op_image:
                BoxingConfig singleImgConfig = new BoxingConfig(BoxingConfig.Mode.SINGLE_IMG);
                singleImgConfig.needCamera();
                Boxing.of(singleImgConfig).withIntent(this, BoxingActivity.class).start(this, COMPRESS_REQUEST_CODE);
                break;
            case R.id.op_preview:
                if (mEditFragment != null && mPreviewFragment != null) {
                    if (!mIsPreview) {
                        FragmentUtils.showHideFragment(getSupportFragmentManager(), mPreviewFragment, mEditFragment, true, true);
                        mPreviewFragment.refresh(mEditFragment.getTitle(), mEditFragment.getContent());
                    } else {
                        getSupportFragmentManager().popBackStack();
                    }
                    mIsPreview = !mIsPreview;
                }
                break;
        }
    }

    private void insertLink() {
        View rootView = LayoutInflater.from(this).inflate(R.layout.layout_insert_link_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("插入链接")
                .setView(rootView).create();

        final TextInputLayout titleHint = (TextInputLayout) rootView.findViewById(R.id.inputNameHint);
        final TextInputLayout linkHint = (TextInputLayout) rootView.findViewById(R.id.inputHint);
        final EditText title = (EditText) rootView.findViewById(R.id.name);
        final EditText link = (EditText) rootView.findViewById(R.id.text);

        rootView.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleStr = title.getText().toString().trim();
                String linkStr = link.getText().toString().trim();

                if (TextUtils.isEmpty(titleStr)) {
                    titleHint.setError("不能为空");
                    return;
                }
                if (TextUtils.isEmpty(linkStr)) {
                    linkHint.setError("不能为空");
                    return;
                }

                if (titleHint.isErrorEnabled())
                    titleHint.setErrorEnabled(false);
                if (linkHint.isErrorEnabled())
                    linkHint.setErrorEnabled(false);

                mEditFragment.getPerformEditable().perform(R.id.op_link, titleStr, linkStr);
                dialog.dismiss();
            }
        });

        rootView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            final ArrayList<BaseMedia> medias = Boxing.getResult(data);
            if (requestCode == REQUEST_CODE) {
            } else if (requestCode == COMPRESS_REQUEST_CODE) {
                android.util.Log.d("ljx", "path = " + medias.get(0).getPath());
            }
        }
    }
}
