package com.pengshan.latte_core.delegates.web;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.pengshan.latte_core.delegates.LatteDelegate;
import com.pengshan.latte_core.delegates.web.route.RouteKeys;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public abstract class WebDelegate extends LatteDelegate implements IwebViewInitializer{

    private WebView mWebView=null;

    private final ReferenceQueue<WebView> WEB_VIEW_QUENE=new ReferenceQueue<>();

    private String mUrl=null;

    private boolean mIsWebViewAbailable=false;

    private LatteDelegate mTopDelegate=null;



    public WebDelegate() {

    }

    public abstract IwebViewInitializer setInitializer();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args=getArguments();
        mUrl=args.getString(RouteKeys.URL.name());
        initWebView();
    }

    @SuppressLint("JavascriptInterface")
    private void initWebView(){
        if (mWebView!=null){
            mWebView.removeAllViews();
            mWebView.destroy();
        }else {
            final IwebViewInitializer initializer=setInitializer();
            if (initializer!=null){
                final WeakReference<WebView> webViewWeakReference=
                        new WeakReference<WebView>(new WebView(getContext()),WEB_VIEW_QUENE);
                mWebView=webViewWeakReference.get();
                mWebView=initializer.initWebView(mWebView);
                mWebView.setWebViewClient(initializer.initWebViewClient());
                mWebView.setWebChromeClient(initializer.initWebChromeClient());
                mWebView.addJavascriptInterface(LatteWebInterface.create(this),"latte");
                mIsWebViewAbailable=true;
            }else {
                throw new NullPointerException("Initializer is Null");
            }
        }
    }

    public void setTopDelegate(LatteDelegate delegate){
        mTopDelegate=delegate;
    }

    public LatteDelegate getTopDelegate(){
        if (mTopDelegate==null){
            mTopDelegate=this;
        }
        return mTopDelegate;
    }

    public WebView getWebView(){
        if (mWebView==null){
            throw new NullPointerException("WEBVIEW IS NULL");
        }

        return mIsWebViewAbailable?mWebView:null;
    }

    public String getUrl(){
        if (mUrl==null){
            throw new NullPointerException("WebView IS NULL");
        }
        return mUrl;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mWebView!=null){
            mWebView.onPause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mWebView!=null){
            mWebView.onResume();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsWebViewAbailable=false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWebView!=null){
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView=null;
        }
    }
}
