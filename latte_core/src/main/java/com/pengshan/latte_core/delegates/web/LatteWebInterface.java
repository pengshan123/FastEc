package com.pengshan.latte_core.delegates.web;

import android.util.Log;
import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.pengshan.latte_core.delegates.web.event.Event;
import com.pengshan.latte_core.delegates.web.event.EventMannager;

public class LatteWebInterface {
    private final WebDelegate DELEGATE;


    private LatteWebInterface(WebDelegate delegate) {
        DELEGATE = delegate;
    }

    static LatteWebInterface create(WebDelegate delegate){
        return new LatteWebInterface(delegate);
    }

    @JavascriptInterface
    public String event(String params){
        final String action=JSON.parseObject(params).getString("action");

        final Event event=EventMannager.getInstance().createEvent(action);
        if (event!=null){
            event.setDelegate(DELEGATE);
            event.setContext(DELEGATE.getContext());
            event.setUrl(DELEGATE.getUrl());
            return event.execute(params);
        }

        return null;
    }
}





















