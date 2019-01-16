package com.pengshan.latte_core.delegates;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.pengshan.latte_core.camera.CameraImageBean;
import com.pengshan.latte_core.camera.LatteCamera;
import com.pengshan.latte_core.camera.RequestCodes;
import com.pengshan.latte_core.util.callback.CallbackManager;
import com.pengshan.latte_core.util.callback.CallbackType;
import com.pengshan.latte_core.util.callback.IGlobCallback;
import com.yalantis.ucrop.UCrop;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public abstract class PermissionCheckerDelegate extends BaseDelegate{

    @NeedsPermission(Manifest.permission.CAMERA)
    void startCamera(){
        LatteCamera.start(this);
    }

    public void startCameraWithCheck(){
        PermissionCheckerDelegatePermissionsDispatcher.startCameraWithPermissionCheck(this);
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    void onCameraDenied(){
        Toast.makeText(getContext(),"不允许拍照",Toast.LENGTH_LONG).show();
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    void onCameraRationale(PermissionRequest request){
        showRationaleDialog(request);
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void onCameraNever(){
        Toast.makeText(getContext(),"永久拒绝权限",Toast.LENGTH_LONG).show();
    }

    private void showRationaleDialog(final PermissionRequest request){
        if (getContext() != null) {
            new AlertDialog.Builder(getContext())
                    .setPositiveButton("同意使用", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            request.proceed();
                        }
                    })
                    .setNegativeButton("拒绝使用", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            request.cancel();
                        }
                    })
                    .setCancelable(false)
                    .setMessage("权限管理")
                    .show();
             }

    }

    @SuppressLint("NeedOnRequestPermissionsResult")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionCheckerDelegatePermissionsDispatcher.onRequestPermissionsResult(this,requestCode,grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK){
            switch (requestCode){
                case RequestCodes.TAKE_PHOTO:
                    final Uri resultUri=CameraImageBean.getInstance().getPath();
                    UCrop.of(resultUri,resultUri)
                            .withMaxResultSize(400,400)
                            .start(getContext(),this);
                    break;
                case RequestCodes.PICK_PHOTO:
                    if (data!=null){
                        final Uri pickPath=data.getData();
                        final String pickCrop=LatteCamera.createCropFile().getPath();
                        UCrop.of(pickPath,Uri.parse(pickCrop))
                                .withMaxResultSize(400,400)
                                .start(getContext(),this);
                    }
                    break;

                case RequestCodes.CROP_PHOTO:
                    final Uri cropUri=UCrop.getOutput(data);
                    final IGlobCallback<Uri> callback=CallbackManager.getInstance()
                            .getCallback(CallbackType.ON_CROP);

                    if (callback!=null){
                        callback.executeCallback(cropUri);
                    }
                    break;

                case RequestCodes.SCAN:
                    break;

                case RequestCodes.CROP_ERROR:
                    Toast.makeText(getContext(),"剪裁出错",Toast.LENGTH_LONG).show();
                default:
                    break;
            }
        }
    }
}














