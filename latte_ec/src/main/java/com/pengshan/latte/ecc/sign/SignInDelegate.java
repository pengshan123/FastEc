package com.pengshan.latte.ecc.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.pengshan.latte.ecc.R;
import com.pengshan.latte.ecc.R2;
import com.pengshan.latte_core.delegates.LatteDelegate;
import com.pengshan.latte_core.net.RestClient;
import com.pengshan.latte_core.net.callback.ISuccess;
import com.pengshan.latte_core.wechat.LatteWeChat;
import com.pengshan.latte_core.wechat.callbacks.IWeChatSignInCallback;

import butterknife.BindView;
import butterknife.OnClick;

public class SignInDelegate extends LatteDelegate {
    @BindView(R2.id.edit_sign_in_email)
    TextInputEditText mEmail;

    @BindView(R2.id.edit_sign_in_password)
    TextInputEditText mPassword;

    @OnClick(R2.id.btn_sign_in)
    void onClickSignIn(){
        if (checkForm()){
            RestClient.builder()
                    .url("index")
                    .params("","")
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            Log.e("reponseString",response);
                            SignHandler.onSignIn(response,mISignListener);
                        }
                    })
                    .build()
                    .post();
            Toast.makeText(getContext(),"验证通过",Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R2.id.icon_sign_in_wechat)
    void onClickWeChat(){
        LatteWeChat.getInstance().onSignSuccess(new IWeChatSignInCallback() {
            @Override
            public void onSignInSuccess(String userInfo) {

            }
        }).signIn();
    }

    @OnClick(R2.id.tv_link_sign_up)
    void onClickLink(){
        start(new SignUpDelegate());
    }

    private ISignListener mISignListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener){
            mISignListener= (ISignListener) activity;
        }
    }

    private boolean checkForm(){
        final String email=mEmail.getText().toString();
        final String password=mPassword.getText().toString();
        boolean isPass=true;

        if (email.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mEmail.setError("错误的邮箱格式");
            isPass=false;
        }else {
            mEmail.setError(null);
        }

        if (password.isEmpty()||password.length()<6){
            mPassword.setError("请输入6位数密码");
            isPass=false;
        }else {
            mPassword.setError(null);
        }

        return isPass;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}





















