package com.pengshan.latte_core.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import java.util.HashMap;

public final class Latte {
    private static Handler HANDLER;
    public static Configurator init(Context context){
        getConfigurations().put(ConfigType.APPLICATION_CONTEXT.name(),context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }

    public static <T> T getConfiguration(Object key) {
        return getConfigurator().getConfiguration((Enum<ConfigType>) key);
    }

    public static HashMap<String,Object> getConfigurations(){
        return Configurator.getInstance().getLatteConfigs();
    }

    public static Application getApplicationContext() {
        return getConfiguration(ConfigType.APPLICATION_CONTEXT);
    }

    public static Context getApplication(){
        return (Context) getConfigurations().get(ConfigType.APPLICATION_CONTEXT.name());
    }

    public static Handler getHandler(){
        HANDLER=new Handler();
        return HANDLER;
    }


}
