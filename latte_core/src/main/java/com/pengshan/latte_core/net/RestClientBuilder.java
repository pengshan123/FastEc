package com.pengshan.latte_core.net;

import android.content.Context;

import com.pengshan.latte_core.net.callback.IError;
import com.pengshan.latte_core.net.callback.IFailure;
import com.pengshan.latte_core.net.callback.IRequest;
import com.pengshan.latte_core.net.callback.ISuccess;
import com.pengshan.latte_core.ui.LoaderStyle;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RestClientBuilder {
    private String mUrl;
    private static final Map<String,Object> PARAMS=RestCreator.getParams();
    private IRequest mRequest;
    private String downloadDir;
    private String extension;
    private String name;
    private ISuccess mSuccess;
    private IFailure mFailure;
    private IError mError;
    private RequestBody mBody;
    private Context mContext;
    private LoaderStyle mLoaderStyle;
    private File file;

    RestClientBuilder(){

    }

    public final RestClientBuilder url(String url){
        this.mUrl=url;
        return this;
    }

    public final RestClientBuilder params(Map<String,Object> params){
        PARAMS.putAll(params);
        return this;
    }


    public final RestClientBuilder params(String key,Object value){
        PARAMS.put(key,value);
        return this;
    }

    public final RestClientBuilder file(File file){
        this.file=file;
        return this;
    }

    public final RestClientBuilder file(String file){
        this.file=new File(file);
        return this;
    }

    public final RestClientBuilder dir(String dir){
        this.downloadDir=dir;
        return this;
    }

    public final RestClientBuilder extension(String extension){
        this.extension=extension;
        return this;
    }

    public final RestClientBuilder name(String name){
        this.name=name;
        return this;
    }

    public final RestClientBuilder raw(String raw){
        this.mBody=RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),raw);
        return this;
    }


    public final RestClientBuilder success(ISuccess success){
        this.mSuccess=success;
        return this;
    }

    public final RestClientBuilder failure(IFailure failure){
        this.mFailure=failure;
        return this;
    }

    public final RestClientBuilder error(IError error){
        this.mError=error;
        return this;
    }

    public final RestClientBuilder reauest(IRequest request){
        this.mRequest=request;
        return this;
    }

    public final RestClientBuilder loader(Context context,LoaderStyle style){
        this.mContext=context;
        this.mLoaderStyle=style;
        return this;
    }

    public final RestClientBuilder loader(Context context){
        this.mContext=context;
        this.mLoaderStyle=LoaderStyle.BallClipRotateIndicator;
        return this;
    }


    public final RestClient build(){
        return new RestClient(mUrl,PARAMS,downloadDir,extension,name,mRequest,mSuccess,mFailure,mError,mBody,file,mContext,mLoaderStyle);
    }
}

















