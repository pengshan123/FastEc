package com.pengshan.latte.ecc.pay;

public interface IAIPayResultListener {
    void onPaySuccess();

    void onPaying();

    void onPayFail();

    void onPayCancel();

    void onPayConnnectError();
}
