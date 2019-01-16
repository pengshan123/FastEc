package com.pengshan.latte.ecc.main.personal.profile;

import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.pengshan.latte.ecc.R;
import com.pengshan.latte.ecc.main.personal.setting.NameDelegate;
import com.pengshan.latte_core.date.DateDIalogUtil;
import com.pengshan.latte_core.delegates.LatteDelegate;
import com.pengshan.latte_core.net.RestClient;
import com.pengshan.latte_core.net.callback.ISuccess;
import com.pengshan.latte_core.ui.recycle.MultipleFields;
import com.pengshan.latte_core.ui.recycle.MultipleItemEntity;
import com.pengshan.latte_core.util.callback.CallbackManager;
import com.pengshan.latte_core.util.callback.CallbackType;
import com.pengshan.latte_core.util.callback.IGlobCallback;

import retrofit2.http.DELETE;

public class UserProfileOnClickListener extends SimpleClickListener {

    private final LatteDelegate DELEGATE;

    private String[] mGenders=new String[]{"男","女","保密"};



    public UserProfileOnClickListener(LatteDelegate delegate){
        this.DELEGATE=delegate;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, final View view, int position) {
        MultipleItemEntity entity= (MultipleItemEntity) adapter.getData().get(position);
        int id=entity.getField(MultipleFields.ID);
        switch (id){
            case 1:
                CallbackManager.getInstance()
                        .addCallback(CallbackType.ON_CROP, new IGlobCallback<Uri>() {
                            @Override
                            public void executeCallback(Uri args) {
                                RestClient.builder()
                                        .url("")
                                        .loader(DELEGATE.getContext())
                                        .file(args.getPath())
                                        .success(new ISuccess() {
                                            @Override
                                            public void onSuccess(String response) {
                                                final String path=JSON.parseObject(response).getJSONObject("result")
                                                        .getString("path");

                                                RestClient.builder()
                                                        .url("user_profile.php")
                                                        .params("avator",path)
                                                        .loader(DELEGATE.getContext())
                                                        .success(new ISuccess() {
                                                            @Override
                                                            public void onSuccess(String response) {

                                                            }
                                                        })
                                                        .build()
                                                        .post();
                                            }
                                        })
                                        .build()
                                        .upload();

                            }
                        });
                DELEGATE.startCameraWithCheck();
                break;
            case 2:
                DELEGATE.getSupportDelegate().start(new NameDelegate());
                break;
            case 3:
                getGenderDialog(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TextView textView=view.findViewById(R.id.tv_arrow_value);
                        textView.setText(mGenders[which]);
                        dialog.cancel();
                    }
                });
                break;
            case 4:
                final DateDIalogUtil dateDIalogUtil=new DateDIalogUtil();
                dateDIalogUtil.setDateListener(new DateDIalogUtil.IDateListener() {
                    @Override
                    public void onDateChange(String date) {
                        final TextView textView=view.findViewById(R.id.tv_arrow_value);
                        textView.setText(date);
                    }
                });

                dateDIalogUtil.showDialog(DELEGATE.getContext());
                break;
            default:
                break;
        }
    }

    private void getGenderDialog(DialogInterface.OnClickListener listener){
        final AlertDialog.Builder builder=new AlertDialog.Builder(DELEGATE.getContext());
        builder.setSingleChoiceItems(mGenders,0,listener);
        builder.show();
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
