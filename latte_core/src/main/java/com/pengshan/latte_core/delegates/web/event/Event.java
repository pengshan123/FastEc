package com.pengshan.latte_core.delegates.web.event;

import android.content.Context;

import com.pengshan.latte_core.delegates.LatteDelegate;

public abstract class Event implements IEvent{
    private Context mContext=null;

    private String mAction=null;

    private LatteDelegate mDelegate=null;

    private String mUrl=null;

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public String getAction() {
        return mAction;
    }

    public void setAction(String mAction) {
        this.mAction = mAction;
    }

    public LatteDelegate getDelegate() {
        return mDelegate;
    }

    public void setDelegate(LatteDelegate mDelegate) {
        this.mDelegate = mDelegate;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }
}













