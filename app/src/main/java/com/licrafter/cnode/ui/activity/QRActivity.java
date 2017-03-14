package com.licrafter.cnode.ui.activity;

import android.os.Bundle;
import android.view.KeyEvent;

import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.licrafter.cnode.R;
import com.licrafter.cnode.base.BaseActivity;

import butterknife.BindView;

/**
 * author: shell
 * date 2017/3/13 上午11:26
 **/
public class QRActivity extends BaseActivity {

    @BindView(R.id.qr_view)
    DecoratedBarcodeView barcodeScannerView;

    private CaptureManager capture;


    @Override
    public int getContentView() {
        return R.layout.activity_qr;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        capture = new CaptureManager(this, barcodeScannerView);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode();
    }

    @Override
    public void setListeners() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

    @Override
    public void bind() {

    }

    @Override
    public void unBind() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }
}
