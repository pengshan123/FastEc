package com.pengshan.latte_core.ui;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.pengshan.latte_core.R;
import com.pengshan.latte_core.util.DimenUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

public class LatteLoader {
    private static final int LOADER_SIZE_SACLE=8;
    private static final int LOADER_OFFEST_SCALE=10;

    private static final ArrayList<AppCompatDialog> LOADERS=new ArrayList<>();
    private static final String DEFAULTLOADER=LoaderStyle.BallClipRotateIndicator.name();

    public static void showLoading(Context context,Enum<LoaderStyle> type){
        showLoading(context,type.name());
    }
    public static void showLoading(Context context,String type){
        final AppCompatDialog dialog=new AppCompatDialog(context,R.style.dialog);
        final AVLoadingIndicatorView avLoadingIndicatorView=LoaderCreator.create(type,context);
        dialog.setContentView(avLoadingIndicatorView);
        int devicewidth=DimenUtil.getScreenWidth();
        int debiceHeight=DimenUtil.getScreenHeight();

        final Window dialogWidth=dialog.getWindow();
        if (dialogWidth!=null){  
            WindowManager.LayoutParams lp=dialogWidth.getAttributes();
            lp.width=devicewidth/LOADER_SIZE_SACLE;
            lp.height=debiceHeight/LOADER_SIZE_SACLE;
            lp.height=lp.height+debiceHeight/LOADER_OFFEST_SCALE;
            lp.gravity=Gravity.CENTER;

        }
        LOADERS.add(dialog);
        dialog.show();
    }
    public static void showLoading(Context context){
        showLoading(context,DEFAULTLOADER);
    }

    public static void stopLoading(){
        for (AppCompatDialog dialog:LOADERS) {
            if (dialog!=null){
                if (dialog.isShowing()){
                    dialog.cancel();
                    dialog.dismiss();
                }
            }
        }
    }

}




















