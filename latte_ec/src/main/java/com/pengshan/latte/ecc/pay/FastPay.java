package com.pengshan.latte.ecc.pay;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pengshan.latte.ecc.R;
import com.pengshan.latte_core.delegates.LatteDelegate;
import com.pengshan.latte_core.net.RestClient;
import com.pengshan.latte_core.net.callback.ISuccess;
import com.pengshan.latte_core.wechat.LatteWeChat;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;

public class FastPay implements View.OnClickListener {
    private IAIPayResultListener mIaiPayResultListener;
    private Activity mActivity;

    private AlertDialog mDialog;
    private int mOrderID=-1;


    private FastPay(LatteDelegate delegate){
        this.mActivity=delegate.getProxyActivity();
        this.mDialog=new AlertDialog.Builder(delegate.getContext()).create();

    }

    public static FastPay create(LatteDelegate delegate){
        return new FastPay(delegate);
    }

    public void beginPayDialog() {
        mDialog.show();
        final Window window = mDialog.getWindow();
        if (window != null){
            window.setContentView(R.layout.dialog_pay_panel);
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.anim_panel_up_from_bottom);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            final WindowManager.LayoutParams params=window.getAttributes();

            params.width=WindowManager.LayoutParams.MATCH_PARENT;
            params.flags=WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(params);

            window.findViewById(R.id.btn_dialog_pay_alipay).setOnClickListener(this);
            window.findViewById(R.id.btn_dialog_pay_wechat).setOnClickListener(this);
            window.findViewById(R.id.btn_dialog_pay_cancel).setOnClickListener(this);

        }

    }

    public final void alpay(int orderId){
        final String singUrl="";
        RestClient.builder()
                .url(singUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final String paySign=JSON.parseObject(response).getString("result");
                        final PayAsyncTask payAsyncTask=new PayAsyncTask(mActivity,mIaiPayResultListener);
                        payAsyncTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,paySign);


                    }
                })
                .build()
                .post();
    }

    public final void weChatPay(int mOrderID){
        final String weChatPrePayUrl="";

        final IWXAPI iwxapi=LatteWeChat.getInstance().getWXAPI();
        iwxapi.registerApp("appid");
        RestClient.builder()
                .url(weChatPrePayUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject result=JSON.parseObject(response).getJSONObject("result");
                        final String prepayId=result.getString("prepayid");
                        final String partnerId = result.getString("partnerid");
                        final String packageValue = result.getString("package");
                        final String timestamp = result.getString("timestamp");
                        final String nonceStr = result.getString("noncestr");
                        final String paySign = result.getString("sign");

                        final PayReq payReq = new PayReq();
                        payReq.appId = "appid";
                        payReq.prepayId = prepayId;
                        payReq.partnerId = partnerId;
                        payReq.packageValue = packageValue;
                        payReq.timeStamp = timestamp;
                        payReq.nonceStr = nonceStr;
                        payReq.sign = paySign;

                        iwxapi.sendReq(payReq);
                    }
                })
                .build()
                .post();
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (id==R.id.btn_dialog_pay_alipay){
            alpay(mOrderID);
            mDialog.cancel();
        }else if (id==R.id.btn_dialog_pay_wechat){
            weChatPay(mOrderID);
            mDialog.cancel();
        }else if (id==R.id.btn_dialog_pay_cancel){
            mDialog.cancel();
        }
    }

    public FastPay setPayResultListener(IAIPayResultListener listener) {
        this.mIaiPayResultListener = listener;
        return this;
    }

    public FastPay setOrderId(int orderId) {
        this.mOrderID=orderId;
        return this;
    }
}













