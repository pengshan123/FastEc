package com.pengshan.latte_core.wechat.templates;

import com.pengshan.latte_core.activites.ProxyActivity;
import com.pengshan.latte_core.delegates.LatteDelegate;
import com.pengshan.latte_core.wechat.BaseWXEntryActivity;
import com.pengshan.latte_core.wechat.LatteWeChat;

public class WXEntryTemplate extends BaseWXEntryActivity {

    @Override
    protected void onResume() {
        super.onResume();
        finish();
        overridePendingTransition(0,0);
    }

    @Override
    protected void onSignInSucess(String userInfo) {
        LatteWeChat.getInstance().getSignInCallback().onSignInSuccess(userInfo);
    }
}
