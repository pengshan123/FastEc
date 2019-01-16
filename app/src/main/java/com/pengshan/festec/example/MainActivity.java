package com.pengshan.festec.example;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

import com.pengshan.latte.ecc.launcher.ILauncherListener;
import com.pengshan.latte.ecc.launcher.LauncherDelegate;
import com.pengshan.latte.ecc.launcher.OnLauncherFinishTag;
import com.pengshan.latte.ecc.main.EcBottomDelegate;
import com.pengshan.latte.ecc.sign.ISignListener;
import com.pengshan.latte.ecc.sign.SignInDelegate;
import com.pengshan.latte_core.activites.ProxyActivity;
import com.pengshan.latte_core.app.Latte;
import com.pengshan.latte_core.delegates.LatteDelegate;
import com.pengshan.latte_core.store.MapManager;
import com.pengshan.latte_core.ui.niorgai.StatusBarCompat;
import com.saic.vehiclecontrol.service.SearchVehicleService;

import cn.jpush.android.api.JPushInterface;

public class MainActivity extends ProxyActivity implements
        ISignListener,
        ILauncherListener {
    private Intent mIntent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }
        Latte.getConfigurator().withActivity(this);
        MapManager.getInstance().getStore().put(MapManager.KEY,this);
        StatusBarCompat.translucentStatusBar(this,true);

        mIntent=new Intent(this,SearchVehicleService.class);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(mIntent);
    }

    @Override
    public LatteDelegate setRootDelegate() {
        return new LauncherDelegate();
    }

    @Override
    public void onSignInSuccess() {
        Toast.makeText(this,"登录成功",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSignUpSuccess() {
        Toast.makeText(this,"注册成功",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag) {
        switch (tag){
            case SIGNED:
                Toast.makeText(this,"启动结束,用户已经登录了",Toast.LENGTH_LONG).show();
                startWithPop(new EcBottomDelegate());
                break;
            case NOT_SIGNED:
                Toast.makeText(this,"启动结束,用户没有登录",Toast.LENGTH_LONG).show();
                startWithPop(new SignInDelegate());
                break;
            default:
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }
}








































































































































