package com.licrafter.cnode.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * author: shell
 * date 2017/3/2 上午11:36
 **/
public class CNodeWebView extends WebView {

    public CNodeWebView(Context context) {
        this(context, null);
    }

    public CNodeWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CNodeWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWebView();
    }

    @SuppressLint("JavascriptInterface")
    private void initWebView() {
        this.setWebViewClient(webViewClient);
        WebSettings webSettings = getSettings();
        webSettings.setDisplayZoomControls(false);
        webSettings.setSupportZoom(false);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setLoadsImagesAutomatically(true);
        addJavascriptInterface(new JavaScriptObject(getContext()), "cnode");
    }

    public void loadHtml(String data) {
        StringBuilder sb = new StringBuilder();
        data = sb.append("<html>")
                .append("<head>")
                .append("<link rel=\"stylesheet\" href=\"")
                .append("file:///android_asset/css/github.css")
                .append("\" />")
                .append("</head>")
                .append("<body class=\"markdown-body\">")
                .append(data)
                .append("<span id='tooltip'></span>")
                .append("<script type='text/javascript' src='file:///android_asset/js/jquery-3.1.1.min.js'></script>")
                .append("<script type='text/javascript' src='file:///android_asset/js/markdownview.js'></script>")
                .append("<script type=\"text/x-mathjax-config\"> MathJax.Hub.Config({showProcessingMessages: false, showMathMenu: false, tex2jax: {inlineMath: [['$','$']]}});</script>")
                .append("<script type=\"text/javascript\" src=\"https://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS_CHTML\"></script>")
                .append("</body>")
                .append("</html>").toString();

        loadDataWithBaseURL("",
                data,
                "text/html",
                "UTF-8",
                "");
    }

    private WebViewClient webViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            if (url.contains(".jpg")||url.contains(".png")||url.contains(".gif")||url.contains(".jpeg")) {
//                if (url.contains("file://")){
//                    url = url.substring(6,url.length());
//                }
//            } else if (url.contains("www.v2ex.com")) {
//                view.loadUrl(url);
//            } else {
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                getContext().startActivity(intent);
//            }
            view.loadUrl(url);
            return true;
        }

    };

    public class JavaScriptObject {
        public Context context;

        public JavaScriptObject(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public void showImageDetail(String url) {
            Toast.makeText(context, url, Toast.LENGTH_SHORT).show();
        }
    }
}
