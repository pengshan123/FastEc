package com.pengshan.latte_core.util.callback;

import java.util.WeakHashMap;

public class CallbackManager {
    private static final WeakHashMap<Object,IGlobCallback> CALLBACKS=new WeakHashMap<>();

    private static class Holder{
        private static final CallbackManager INSTANCE=new CallbackManager();
    }

    public static CallbackManager getInstance(){
        return Holder.INSTANCE;
    }

    public CallbackManager addCallback(Object tag,IGlobCallback callback){
        CALLBACKS.put(tag,callback);
        return this;
    }

    public IGlobCallback getCallback(Object tag){
        return CALLBACKS.get(tag);
    }
}













