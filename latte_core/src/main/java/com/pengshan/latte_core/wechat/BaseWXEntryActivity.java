package com.pengshan.latte_core.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pengshan.latte_core.net.RestClient;
import com.pengshan.latte_core.net.callback.IError;
import com.pengshan.latte_core.net.callback.IFailure;
import com.pengshan.latte_core.net.callback.ISuccess;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

public abstract class BaseWXEntryActivity extends BaseWXActivity{

    protected abstract void onSignInSucess(String userInfo);


    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        final String code=((SendAuth.Resp)baseResp).code;
        final StringBuilder authUrl=new StringBuilder();
        authUrl
                .append("")
                .append(LatteWeChat.APP_ID)
                .append("&secret=")
                .append(LatteWeChat.APP_SECRET)
                .append("&code=")
                .append(code)
                .append("");

        getAuth(authUrl.toString());

    }

    private void getAuth(String authUrl){
        RestClient
                .builder()
                .url(authUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject authObj=JSON.parseObject(response);
                        final String accessToken=authObj.getString("access_token");
                        final String openId=authObj.getString("openid");

                        final StringBuilder userInfoUrl=new StringBuilder();
                        userInfoUrl
                                .append("")
                                .append(accessToken)
                                .append("&openid")
                                .append(openId)
                                .append("&lang")
                                .append("zn_CN");

                        getUserInfo(userInfoUrl.toString());
                    }
                })
                .build()
                .get();

    }

    private void getUserInfo(String userInfoUrl){
        RestClient
                .builder()
                .url(userInfoUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        onSignInSucess(response);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {

                    }
                })
                .build()
                .get();
    }
}













