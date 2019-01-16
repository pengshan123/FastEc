package com.pengshan.latte_core.net;

import android.content.Context;

import com.pengshan.latte_core.net.callback.IError;
import com.pengshan.latte_core.net.callback.IFailure;
import com.pengshan.latte_core.net.callback.IRequest;
import com.pengshan.latte_core.net.callback.ISuccess;
import com.pengshan.latte_core.net.callback.RequestCallbacks;
import com.pengshan.latte_core.net.download.Downloadhandler;
import com.pengshan.latte_core.ui.LatteLoader;
import com.pengshan.latte_core.ui.LoaderStyle;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Multipart;

public class RestClient {
    private final String URL;
    private final static Map<String,Object> PARAMS=RestCreator.getParams();
    private final IRequest REQUEST;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final ISuccess SUCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody BODY;
    private final LoaderStyle LOADER_STYLE;
    private final File FILE;
    private final Context CONTEXT;


    public RestClient(String url,
                      Map<String, Object> params,
                      String downloadDir,
                      String extension,
                      String name,
                      IRequest request,
                      ISuccess sucess,
                      IFailure failure,
                      IError error,
                      RequestBody body,
                      File file,
                      Context context,
                      LoaderStyle loaderStyle) {
        URL = url;
        PARAMS.putAll(params);
        REQUEST = request;
        SUCESS = sucess;
        FAILURE = failure;
        ERROR = error;
        BODY = body;
        FILE=file;
        this.CONTEXT=context;
        this.LOADER_STYLE=loaderStyle;
        DOWNLOAD_DIR=downloadDir;
        EXTENSION=extension;
        NAME=name;
    }
    public static RestClientBuilder builder(){
        return new RestClientBuilder();
    }

    private void request(HttpMethod method){
        final RestService service=RestCreator.getRestService();
        Call<String> call=null;
        if (REQUEST!=null){
            REQUEST.onRequestStart();
        }

        if (LOADER_STYLE!=null){
            LatteLoader.showLoading(CONTEXT,LOADER_STYLE);
        }

        switch (method){
            case POST:
                call=service.post(URL,PARAMS);
                break;
            case PUT:
                call=service.put(URL,PARAMS);
                break;
            case GET:
                call=service.get(URL,PARAMS);
                break;
            case POST_RAW:
                call=service.postRaw(URL,BODY);
                break;
            case PUT_RAW:
                call=service.putRaw(URL,BODY);
                break;
            case UPLOAD:
                final RequestBody requestBody=
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()),FILE);
                final MultipartBody.Part body=
                        MultipartBody.Part.createFormData("file",FILE.getName(),requestBody);
                call=service.upload(URL,body);

                break;
            case DELETE:
                call=service.delete(URL,PARAMS);
                break;
            default:
                break;
        }

        if (call!=null){
            call.enqueue(getRequestCallback());
        }
    }
    private Callback<String> getRequestCallback(){
        return new RequestCallbacks(
                REQUEST,
                SUCESS,
                FAILURE,
                ERROR,
                LOADER_STYLE
        );
    }

    public final void get(){
        request(HttpMethod.GET);
    }

    public final void post(){
        if (BODY==null){
            request(HttpMethod.POST);
        }else {
            if (!PARAMS.isEmpty()){
                throw new RuntimeException("params must be null");
            }
            request(HttpMethod.POST_RAW);
        }

    }

    public final void put(){
        if (BODY==null){
            request(HttpMethod.PUT);
        }else {
            if (!PARAMS.isEmpty()){
                throw new RuntimeException("params must be null");
            }
            request(HttpMethod.PUT_RAW);
        }
    }

    public final void delete(){
        request(HttpMethod.DELETE);
    }

    public final void upload(){
        request(HttpMethod.UPLOAD);
    }

    public final void download(){
        new Downloadhandler(URL,REQUEST,DOWNLOAD_DIR,EXTENSION,NAME,SUCESS,FAILURE,ERROR).handleDownload();
    }

}















