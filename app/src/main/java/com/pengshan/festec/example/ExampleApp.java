package com.pengshan.festec.example;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.pengshan.latte.ecc.database.DatabaseManager;
import com.pengshan.latte_core.app.Latte;
import com.pengshan.latte_core.delegates.web.event.EventMannager;
import com.pengshan.latte_core.delegates.web.event.TestEvent;
import com.pengshan.latte_core.util.callback.CallbackManager;
import com.pengshan.latte_core.util.callback.CallbackType;
import com.pengshan.latte_core.util.callback.IGlobCallback;

import cn.jpush.android.api.JPushInterface;


public class ExampleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Latte.init(this)
                .withIcon(new FontAwesomeModule())
                .withWeChatAppId("")
                .withWeChatAppSecret("")
                .configure();
        initStetho();
        DatabaseManager.getInstance().init(this);
        EventMannager.getInstance().addEvent("test",new TestEvent());
        Utils.init(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        CallbackManager.getInstance()
                .addCallback(CallbackType.TAG_OPEN_PUSH, new IGlobCallback() {
                    @Override
                    public void executeCallback(Object args) {
                        if (JPushInterface.isPushStopped(Latte.getApplicationContext())){
                            JPushInterface.setDebugMode(true);
                            JPushInterface.init(getApplicationContext());
                        }
                    }
                })
                .addCallback(CallbackType.TAG_OPEN_PUSH, new IGlobCallback() {
                    @Override
                    public void executeCallback(Object args) {
                        if (!JPushInterface.isPushStopped(getApplicationContext())){
                            JPushInterface.stopPush(getApplicationContext());

                        }
                    }
                });
    }



    private void initStetho(){
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build()
        );
    }
}
