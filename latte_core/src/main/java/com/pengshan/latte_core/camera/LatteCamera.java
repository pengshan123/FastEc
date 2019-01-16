package com.pengshan.latte_core.camera;

import android.net.Uri;

import com.pengshan.latte_core.delegates.PermissionCheckerDelegate;
import com.pengshan.latte_core.util.file.FileUtil;

public class LatteCamera {

    public static Uri createCropFile(){
        return Uri.parse(
                FileUtil.createFile(
                        "crop_image",
                        FileUtil.getFileNameByTime("IMG","jpg")).getPath());
    }

    public static void start(PermissionCheckerDelegate delegate){
        new CameraHanlder(delegate).beginCameraDialog();

    }


}
