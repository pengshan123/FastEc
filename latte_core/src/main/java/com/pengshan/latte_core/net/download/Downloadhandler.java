package com.pengshan.latte_core.net.download;

import android.os.AsyncTask;

import com.pengshan.latte_core.net.RestCreator;
import com.pengshan.latte_core.net.RestService;
import com.pengshan.latte_core.net.callback.IError;
import com.pengshan.latte_core.net.callback.IFailure;
import com.pengshan.latte_core.net.callback.IRequest;
import com.pengshan.latte_core.net.callback.ISuccess;
import com.pengshan.latte_core.util.file.FileUtil;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;

public class Downloadhandler {
    private final String URL;
    private final static Map<String,Object> PARAMS=RestCreator.getParams();
    private final IRequest REQUEST;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final ISuccess SUCESS;
    private final IFailure FAILURE;
    private final IError ERROR;

    public Downloadhandler(String url,
                           IRequest request,
                           String download_dir,
                           String extension,
                           String name,
                           ISuccess sucess,
                           IFailure failure,
                           IError error) {
        URL = url;
        REQUEST = request;
        DOWNLOAD_DIR = download_dir;
        EXTENSION = extension;
        NAME = name;
        SUCESS = sucess;
        FAILURE = failure;
        ERROR = error;
    }

    public final void handleDownload(){
        if (REQUEST!=null){
            REQUEST.onRequestStart();
        }

        RestCreator.getRestService().download(URL,PARAMS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            final ResponseBody responseBody=response.body();
                            final SaveFileTask task=new SaveFileTask(REQUEST,SUCESS);
                            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,DOWNLOAD_DIR,EXTENSION,responseBody,NAME);
                            if (task.isCancelled()){
                                if (REQUEST!=null){
                                    REQUEST.onRequestEnd();
                                }
                            }
                        }else {
                            if (ERROR!=null){
                                ERROR.onError(response.code(),response.message());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if (FAILURE!=null){
                            FAILURE.onFailure();
                        }
                    }
                });
    }
}

















