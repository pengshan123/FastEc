package com.pengshan.latte.ecc.pay;

import android.app.Activity;
import android.os.AsyncTask;

import com.alipay.sdk.app.PayTask;

public class PayAsyncTask extends AsyncTask<String,Void,String> {

    private final Activity ACTIVITY;
    private final IAIPayResultListener LISTENER;

    private static final String AL_PAY_STATUS_SUCCESS="9000";
    private static final String AL_PAY_STATUS_PALYING="8000";
    private static final String AL_PAY_STATUS_FAIL="4000";
    private static final String AL_PAY_STATUS_CANCEL="6001";
    private static final String AL_PAY_STATUS_CONNECT_ERROR="6002";


    public PayAsyncTask(Activity activity, IAIPayResultListener listener) {
        ACTIVITY = activity;
        LISTENER = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        final String alPay=params[0];
        final PayTask payTask=new PayTask(ACTIVITY);

        return payTask.pay(alPay,true);

    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        final PayResult payResult=new PayResult(result);
        final String resultInfo=payResult.getResult();
        final String resultStatus=payResult.getResultStatus();
        switch (resultStatus){
            case AL_PAY_STATUS_SUCCESS:
                if (LISTENER!=null){
                    LISTENER.onPaySuccess();
                }
                break;
            case AL_PAY_STATUS_PALYING:
                if (LISTENER!=null){
                    LISTENER.onPaying();
                }
                break;
            case AL_PAY_STATUS_FAIL:

                if (LISTENER!=null){
                    LISTENER.onPayFail();
                }
                break;
            case AL_PAY_STATUS_CANCEL:

                if (LISTENER!=null){
                    LISTENER.onPayCancel();
                }
                break;
            case AL_PAY_STATUS_CONNECT_ERROR:

                if (LISTENER!=null){
                    LISTENER.onPayConnnectError();
                }
                break;
            default:
                break;
        }

    }
}














