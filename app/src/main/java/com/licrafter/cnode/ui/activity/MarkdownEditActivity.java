package com.licrafter.cnode.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bilibili.boxing.Boxing;
import com.bilibili.boxing.model.config.BoxingConfig;
import com.bilibili.boxing.model.entity.BaseMedia;
import com.bilibili.boxing.model.entity.impl.ImageMedia;
import com.licrafter.cnode.R;
import com.licrafter.cnode.base.BaseActivity;
import com.licrafter.cnode.model.PostTopicResultModel;
import com.licrafter.cnode.model.ReplyResultModel;
import com.licrafter.cnode.model.entity.TAB;
import com.licrafter.cnode.mvp.presenter.MarkdownEditPresenter;
import com.licrafter.cnode.mvp.view.MvpView;
import com.licrafter.cnode.ui.fragment.MdEditFragment;
import com.licrafter.cnode.ui.fragment.MdPreviewFragment;
import com.licrafter.cnode.utils.FragmentUtils;
import com.licrafter.cnode.widget.HorizontalOperatorView;
import com.licrafter.cnode.widget.dialog.InsertLinkDialog;
import com.licrafter.cnode.widget.dialog.UploadingDialog;

import java.util.ArrayList;

import boxing_impl.ui.BoxingActivity;
import butterknife.BindView;

/**
 * author: shell
 * date 2017/4/3 下午9:56
 **/
public class MarkdownEditActivity extends BaseActivity implements HorizontalOperatorView.OperationListener, MvpView {

    private static String[] PHOTO_PERMISSIONS = {Manifest.permission.READ_PHONE_STATE};
    public static final int NEW_TOPIC = 0x001;
    public static final int NEW_REPLY = 0x002;

    private static final int COMPRESS_REQUEST_CODE = 2048;
    private static final int REQUEST_PERMISSIONS = 2333;

    @BindView(R.id.inputLayout)
    ScrollView mInputLayout;
    @BindView(R.id.space)
    View mSpace;
    @BindView(R.id.layout_spinner)
    View mSpinnerLayout;
    @BindView(R.id.spinner)
    Spinner mSpinner;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.opLayout)
    HorizontalOperatorView mOpLayout;

    private MdEditFragment mEditFragment;
    private MdPreviewFragment mPreviewFragment;
    private AlertDialog mDialog;
    private UploadingDialog mUploadDialog;
    private MarkdownEditPresenter mPresenter = new MarkdownEditPresenter();
    private TAB mTab = TAB.SHARE;
    private boolean mIsPreview;
    private int mType = NEW_TOPIC;
    //对其他评论发表评论需要
    private String mTopicId;
    private String mReplyId;
    private String mAuthor;


    public static void createTopic(BaseActivity activity) {
        Intent intent = new Intent(activity, MarkdownEditActivity.class);
        intent.putExtra("type", NEW_TOPIC);
        activity.startActivity(intent);
    }

    public static void createReply(BaseActivity activity, int reqestCode, String topic_id, String reply_id, String name) {
        Intent intent = new Intent(activity, MarkdownEditActivity.class);
        intent.putExtra("type", NEW_REPLY);
        intent.putExtra("topic_id", topic_id);
        intent.putExtra("reply_id", reply_id);
        intent.putExtra("author", name);
        activity.startActivityForResult(intent, reqestCode);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_markdown_edit;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        if (getIntent() != null) {
            mType = getIntent().getIntExtra("type", NEW_TOPIC);
            mTopicId = getIntent().getStringExtra("topic_id");
            mReplyId = getIntent().getStringExtra("reply_id");
            mAuthor = getIntent().getStringExtra("author");
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(mType == NEW_TOPIC ? R.string.title_topic_create : R.string.title_reply_create);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (savedInstanceState == null) {
            mEditFragment = MdEditFragment.newInstance(mType, mAuthor);
            mPreviewFragment = MdPreviewFragment.newInstance(mType);
            FragmentUtils.addMultiple(getSupportFragmentManager(), R.id.mdContent, 0, mEditFragment, mPreviewFragment);
        } else {
            mEditFragment = FragmentUtils.findFragment(getSupportFragmentManager(), MdEditFragment.class);
        }
        mDialog = new AlertDialog.Builder(this).setMessage("正在发送...").create();
        mUploadDialog = new UploadingDialog(this);
        mSpinnerLayout.setVisibility(mType == NEW_TOPIC ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setListeners() {
        mOpLayout.setOperationListener(this);
        mInputLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                mSpace.setLayoutParams(new LinearLayout.LayoutParams(1, Math.abs(rect.bottom - getWindowManager().getDefaultDisplay().getHeight())));
            }
        });
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String zh = getResources().getStringArray(R.array.categories)[position];
                mTab = TAB.ValueOf(zh);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void bind() {
        mPresenter.attachView(this);
    }

    @Override
    public void unBind() {
        mPresenter.detachView();
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
            case R.id.action_send_post:
                if (mType == NEW_TOPIC) {
                    sendPost();
                } else {
                    sendReply();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void operation(View v) {
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
                checkPermission();
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

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this, PHOTO_PERMISSIONS[0]) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(PHOTO_PERMISSIONS, REQUEST_PERMISSIONS);
        } else {
            startAlbum();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (REQUEST_PERMISSIONS == requestCode && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startAlbum();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            final ArrayList<BaseMedia> medias = Boxing.getResult(data);
            if (requestCode == COMPRESS_REQUEST_CODE) {
                if (medias == null || medias.size() == 0) {
                    return;
                }
                BaseMedia media = medias.get(0);
                if (!(media instanceof ImageMedia)) {
                    return;
                }
                ImageMedia imageMedia = (ImageMedia) media;
                mPresenter.uploadImg(imageMedia.getPath());
                mUploadDialog.show();
            }
        }
    }

    private void sendPost() {
        mDialog.show();
        mEditFragment.getPerformEditable().performInsertTail();
        mPresenter.createPost(mEditFragment.getTitle()
                , mTab.getEnName(), mEditFragment.getContent());
    }

    private void sendReply() {
        mDialog.show();
        mEditFragment.getPerformEditable().performInsertTail();
        mPresenter.createReply(mTopicId, mEditFragment.getContent(), mReplyId);
    }

    private void startAlbum() {
        BoxingConfig singleImgConfig = new BoxingConfig(BoxingConfig.Mode.SINGLE_IMG);
        singleImgConfig.needCamera();
        Boxing.of(singleImgConfig).withIntent(this, BoxingActivity.class).start(this, COMPRESS_REQUEST_CODE);
    }

    private void insertLink() {
        new InsertLinkDialog(this, new InsertLinkDialog.OnClickListener() {
            @Override
            public void confirm(String title, String url) {
                mEditFragment.getPerformEditable().perform(R.id.op_link, title, url);
            }

            @Override
            public void cancel() {

            }
        }).show();
    }

    @Override
    public void onFailed(Throwable e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    public void notifySendPostSuccess(PostTopicResultModel result) {
        Toast.makeText(this, getString(R.string.toast_create_topic_success), Toast.LENGTH_SHORT).show();
        TopicDetailActivity.start(this, result.getTopic_id());
        finish();
    }

    public void notifyReplySuccess(ReplyResultModel model) {
        setResult(RESULT_OK);
        finish();
    }

    public void uploadProgress(Integer integer) {
        mUploadDialog.updateProgress(integer);
    }

    public void notifyUploadSuccess(String url) {
        mEditFragment.getPerformEditable().perform(R.id.op_image, url);
    }

    public void notifyUploadFailed(Throwable e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    public void dismissUploadDialog() {
        mUploadDialog.dismiss();
    }

    public void dismissSendDialog() {
        mDialog.dismiss();
    }
}
