package com.pengshan.latte_core.camera;

import android.net.Uri;

public final class CameraImageBean {
    private Uri mPath;

    private static final CameraImageBean INSTANCE=new CameraImageBean();

    public static CameraImageBean getInstance(){
        return INSTANCE;
    }

    public Uri getPath() {
        return mPath;
    }

    public void setPath(Uri mPath) {
        this.mPath = mPath;
    }
}
