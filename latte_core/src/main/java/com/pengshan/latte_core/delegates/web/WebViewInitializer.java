package com.pengshan.latte_core.delegates.web;

import android.os.Build;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class WebViewInitializer {

    public WebView createWebView(WebView webView){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        webView.setHorizontalScrollBarEnabled(false);

        webView.setVerticalScrollBarEnabled(false);

        webView.setDrawingCacheEnabled(true);

        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });

        final WebSettings settings=webView.getSettings();

        settings.setJavaScriptEnabled(true);

        final String ua=settings.getUserAgentString();

        settings.setUserAgentString(ua+"Latte");

        settings.setBuiltInZoomControls(false);

        settings.setDisplayZoomControls(false);

        settings.setSupportZoom(false);

        settings.setAllowContentAccess(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            settings.setAllowFileAccessFromFileURLs(true);
            settings.setAllowUniversalAccessFromFileURLs(true);
        }

        settings.setAllowContentAccess(true);

        settings.setAppCacheEnabled(true);

        settings.setDomStorageEnabled(true);

        settings.setDatabaseEnabled(true);

        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

        return webView;
    }
}













