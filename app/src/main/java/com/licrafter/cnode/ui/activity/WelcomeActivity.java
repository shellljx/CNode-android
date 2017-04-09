package com.licrafter.cnode.ui.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.licrafter.cnode.R;
import com.licrafter.cnode.base.BaseActivity;

/**
 * author: shell
 * date 2017/3/15 下午11:18
 **/
public class WelcomeActivity extends BaseActivity {
    @Override
    public int getContentView() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        hideSystemUI();
        findViewById(R.id.content).postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
            }
        }, 1000);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
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
