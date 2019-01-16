package com.pengshan.latte_core.net;

import android.util.Log;

import com.pengshan.latte_core.R;
import com.pengshan.latte_core.app.ConfigType;
import com.pengshan.latte_core.app.Configurator;
import com.pengshan.latte_core.app.Latte;
import com.pengshan.latte_core.net.interceptors.DebugInterceptor;

import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RestCreator {
    public static RestService getRestService(){
        return RestServiceHolder.REST_SERVICE;
    }

    private static final class ParamsHolder{
        public static final WeakHashMap<String,Object> PARAMS=new WeakHashMap<>();
    }


    public static WeakHashMap<String,Object> getParams(){
        return ParamsHolder.PARAMS;
    }

    private static final class RetrofitHolder{
        private static final String BASE_URL= (String) Configurator.getInstance().getLatteConfigs().get(ConfigType.API_HOST);
        private static final Retrofit RETROFIT_CLIENT=new Retrofit.Builder()
                .baseUrl("http://127.0.0.1/")
                .client(OKhttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    private static ArrayList<Interceptor> getInterceptorArrayList(){
        ArrayList<Interceptor> interceptors= new ArrayList<>();
        interceptors.add(new DebugInterceptor("shop_cart",R.raw.shop_cart_data));
        return interceptors;
    }

    private static final class OKhttpHolder{
        private static final int TIME_OUT=60;
        private static final OkHttpClient.Builder BUILDER=new OkHttpClient.Builder();
        private static final ArrayList<Interceptor> INTERCEPTORS= getInterceptorArrayList();

        private static OkHttpClient.Builder addInterceptor(){
            if (INTERCEPTORS!=null&&!INTERCEPTORS.isEmpty()){
                for (Interceptor interceptor:INTERCEPTORS) {
                    BUILDER.addInterceptor(interceptor);
                }
            }

            return BUILDER;
        }

        private static final OkHttpClient OK_HTTP_CLIENT=addInterceptor()
                .connectTimeout(TIME_OUT,TimeUnit.SECONDS)
                .build();
    }

    private static final class RestServiceHolder{
        private static final RestService REST_SERVICE=
                RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);

    }
}












