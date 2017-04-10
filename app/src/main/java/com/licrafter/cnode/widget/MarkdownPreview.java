package com.licrafter.cnode.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by lijx on 2017/4/5.
 */

public class MarkdownPreview extends NestedScrollView {

    public WebView mWebView;
    private Context mContext;
    private OnLoadingFinishListener mLoadingFinishListener;

    public MarkdownPreview(Context context) {
        this(context, null);
    }

    public MarkdownPreview(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MarkdownPreview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @SuppressLint({"AddJavascriptInterface", "SetJavaScriptEnabled"})
    private void init(Context context) {
        if (!isInEditMode()) {
            this.mContext = context;
            if (Build.VERSION.SDK_INT >= 21) {
                WebView.enableSlowWholeDocumentDraw();
            }
            this.mWebView = new WebView(this.mContext);
            this.mWebView.getSettings().setDomStorageEnabled(true);
            this.mWebView.getSettings().setJavaScriptEnabled(true);
            this.mWebView.setVerticalScrollBarEnabled(false);
            this.mWebView.setHorizontalScrollBarEnabled(false);
            this.mWebView.addJavascriptInterface(new JavaScriptInterface(this), "handler");
            this.mWebView.setWebViewClient(new MdWebViewClient());
            this.mWebView.loadUrl("file:///android_asset/html/markdown.html");
            addView(this.mWebView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }
    }

    public final void parseMarkdown(String str, boolean z) {
        this.mWebView.loadUrl("javascript:parseMarkdown(\"" + str.replace("\n", "\\n").replace("\"", "\\\"").replace("'", "\\'") + "\", " + z + ")");
    }


    public void setOnLoadingFinishListener(OnLoadingFinishListener loadingFinishListener) {
        this.mLoadingFinishListener = loadingFinishListener;
    }

    public interface ContentListener {
    }

    public interface OnLoadingFinishListener {
        void onLoadingFinish();
    }

    final class JavaScriptInterface {
        final MarkdownPreview a;

        private JavaScriptInterface(MarkdownPreview markdownPreviewView) {
            this.a = markdownPreviewView;
        }

        @JavascriptInterface
        public void none() {

        }

        @JavascriptInterface
        public void onHTMLGenerated(String html) {

        }
    }


    final class MdWebViewClient extends WebViewClient {


        public final void onPageFinished(WebView webView, String str) {
            if (mLoadingFinishListener != null) {
                mLoadingFinishListener.onLoadingFinish();
            }
        }

        public final void onReceivedError(WebView webView, int i, String str, String str2) {
            new StringBuilder("onReceivedError :errorCode:").append(i).append("description:").append(str).append("failingUrl").append(str2);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO: 2017/4/10
            return true;
        }
    }
}
