package com.pengshan.latte_core.delegates.web.client;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.pengshan.latte_core.delegates.web.WebDelegate;
import com.pengshan.latte_core.delegates.web.route.Router;

public class WebViewClientImpl extends WebViewClient {

    private final WebDelegate DELEGATE;


    public WebViewClientImpl(WebDelegate delegate) {
        DELEGATE = delegate;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return super.shouldOverrideUrlLoading(view, request);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return Router.getInstance().handleWebUrl(DELEGATE,url);
    }

}














