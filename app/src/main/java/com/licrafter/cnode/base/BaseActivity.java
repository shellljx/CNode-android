package com.licrafter.cnode.base;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.licrafter.cnode.utils.StatusBarUtils;

import butterknife.ButterKnife;

/**
 * author: shell
 * date 2017/2/22 下午4:52
 **/
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        StatusBarUtils.initStatusBar(this);
        ButterKnife.bind(this);
        initView();
        setListeners();
        bind();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBind();
    }

    /**
     * 返回content layout id
     *
     * @return
     */
    public abstract int getContentView();

    public abstract void initView();

    public abstract void setListeners();

    public abstract void bind();

    public abstract void unBind();
}
