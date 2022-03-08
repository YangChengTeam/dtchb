package com.yc.wxchb.beans.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.lq.lianjibusiness.base_libary.ui.base.SimpleActivity;
import com.yc.wxchb.R;
import com.yc.wxchb.widget.CustomWebView;


import butterknife.BindView;
import butterknife.OnClick;

public class WebViewActivity extends SimpleActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.customWebView)
    CustomWebView webView;
    private String url;
    private String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initEventAndData() {
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        if (!TextUtils.isEmpty(title)) {
            toolbarTitle.setText(title);
            toolbarTitle.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(this.url)){
            initWebView();
        }
    }
    public static void startWebViewJump(Context context, String url, String titile) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", titile);
        context.startActivity(intent);
    }

    private void initWebView() {
        WebSettings webSetting = webView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setUseWideViewPort(false);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDefaultTextEncodingName("UTF-8");
        showWaiteDialog();
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                webView.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                closeWaiteDialog();
                view.clearHistory();
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress) {
                super.onProgressChanged(view, progress);
                if (progress>70){
                    closeWaiteDialog();
                }
            }
        });
        webView.loadUrl(url);
    }
    @OnClick({R.id.toolbar_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
        }
    }

    @Override
    public void onDestroy() {
        try {
            if (webView != null) {
                webView.stopLoading();
                webView.removeAllViewsInLayout();
                webView.removeAllViews();
                webView.setWebViewClient(null);
                webView.setWebChromeClient(null);
                webView.destroy();
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        super.onDestroy();
    }
}