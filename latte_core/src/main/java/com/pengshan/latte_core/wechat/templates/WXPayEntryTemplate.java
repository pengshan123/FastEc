package com.pengshan.latte_core.wechat.templates;
import com.tencent.mm.opensdk.modelbase.BaseReq;

public class WXPayEntryTemplate extends BaseWXPayEntryActivity {

    @Override
    protected void onPaySuccess() {
        finish();
        overridePendingTransition(0,0);
    }

    @Override
    protected void onPayFail() {
        finish();
        overridePendingTransition(0,0);
    }

    @Override
    protected void onPayCancel() {
        finish();
        overridePendingTransition(0,0);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        finish();
        overridePendingTransition(0,0);
    }
}
