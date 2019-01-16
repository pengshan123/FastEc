package com.pengshan.latte_core.net.callback;

import android.os.Handler;

import com.pengshan.latte_core.ui.LatteLoader;
import com.pengshan.latte_core.ui.LoaderStyle;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestCallbacks implements Callback<String> {
    private final IRequest REQUEST;
    private final ISuccess SUCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final LoaderStyle LOADER_STYLE;
    private final Handler HANDLER=new Handler();

    public RequestCallbacks(IRequest request,
                            ISuccess sucess,
                            IFailure failure,
                            IError error,
                            LoaderStyle loaderStyle) {
        REQUEST = request;
        SUCESS = sucess;
        FAILURE = failure;
        ERROR = error;
        LOADER_STYLE=loaderStyle;
    }


    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()){
            if (call.isExecuted()){
                if (SUCESS!=null){
                    SUCESS.onSuccess(response.body());
                }
            }
        }else {
            if (ERROR!=null){
                ERROR.onError(response.code(),response.message());
            }
        }

        stopLoading();
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if (FAILURE!=null){
            FAILURE.onFailure();
        }

        if (REQUEST!=null){
            REQUEST.onRequestEnd();
        }

        stopLoading();
    }

    private void stopLoading(){
        if (LOADER_STYLE!=null){
            HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LatteLoader.stopLoading();
                }
            },1000);
        }
    }
}













