package com.licrafter.cnode.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.licrafter.cnode.R;
import com.licrafter.cnode.base.BaseActivity;
import com.licrafter.cnode.cache.UserCache;
import com.licrafter.cnode.model.LoginResultModel;
import com.licrafter.cnode.mvp.presenter.LoginPresenter;
import com.licrafter.cnode.mvp.view.MvpView;
import com.licrafter.cnode.utils.LogUtils;

import butterknife.BindView;

/**
 * author: shell
 * date 2017/3/10 下午10:07
 **/
public class LoginActivity extends BaseActivity implements View.OnClickListener, MvpView {

    private static String TAG = LoginActivity.class.getName();

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.input_token)
    TextInputEditText mTokenInputView;
    @BindView(R.id.btn_scan)
    Button mScanBtn;
    @BindView(R.id.btn_login)
    Button mLoginBtn;
    @BindView(R.id.tv_tip)
    TextView mTipView;

    private AlertDialog loginDialog;
    private LoginPresenter mPresenter = new LoginPresenter();

    @Override
    public int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mToolbar.setTitle(getString(R.string.title_login));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        AlertDialog.Builder loginBuilder = new AlertDialog.Builder(this);
        loginBuilder.setMessage("正在登录...");
        loginDialog = loginBuilder.create();
    }

    @Override
    public void setListeners() {
        mTipView.setOnClickListener(this);
        mLoginBtn.setOnClickListener(this);
        mScanBtn.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_tip:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(getString(R.string.token_message));
                builder.create().show();
                break;
            case R.id.btn_scan:
                new IntentIntegrator(this).setCaptureActivity(QRActivity.class).initiateScan();
                break;
            case R.id.btn_login:
                if (!TextUtils.isEmpty(mTokenInputView.getText().toString())) {
                    mPresenter.login(mTokenInputView.getText().toString());
                    loginDialog.show();
                } else {
                    Toast.makeText(this, "登录 Token 不能为空!", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "取消扫码", Toast.LENGTH_LONG).show();
            } else {
                LogUtils.info(TAG, result.getContents());
                mTokenInputView.setText(result.getContents());
                Toast.makeText(this, "扫码成功,点击登录!", Toast.LENGTH_LONG).show();
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onFailed(Throwable e) {
        loginDialog.dismiss();
        Toast.makeText(this, "登录失败，请重试!", Toast.LENGTH_SHORT).show();
    }

    public void notifyLoginSuccess(LoginResultModel model) {
        UserCache.cache(model.getId(), model.getLoginname(), model.getAvatar_url(), mTokenInputView.getText().toString());
        Toast.makeText(this, "登录成功!", Toast.LENGTH_SHORT).show();
        loginDialog.dismiss();
        setResult(RESULT_OK);
        finish();
    }
}
