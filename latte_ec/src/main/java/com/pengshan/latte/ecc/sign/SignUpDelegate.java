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

import butterknife.BindView;
import butterknife.OnClick;


public class SignUpDelegate extends LatteDelegate {
    @BindView(R2.id.edit_sign_up_name)
    TextInputEditText mName=null;

    @BindView(R2.id.edit_sign_up_email)
    TextInputEditText mEmail=null;

    @BindView(R2.id.edit_sign_up_phone)
    TextInputEditText mPhone=null;

    @BindView(R2.id.edit_sign_up_password)
    TextInputEditText mPassword=null;

    @BindView(R2.id.edit_sign_up_re_password)
    TextInputEditText mRePassword=null;

    @OnClick(R2.id.btn_sign_up)
    void onClickSignUp(){
        if (checkForm()){
            RestClient.builder()
                    .url("index")
                    .params("","")
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            Log.e("reponseString",response);
                            SignHandler.onSignUp(response,mISignListener);
                        }
                    })
                    .build()
                    .post();
            Toast.makeText(getContext(),"验证通过",Toast.LENGTH_LONG).show();
        }
    }

    private ISignListener mISignListener;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener){
            mISignListener= (ISignListener) activity;
        }
    }

    @OnClick(R2.id.tv_link_sign_in)
    void onClickLink(){
        start(new SignInDelegate());
    }

    private boolean checkForm(){
        final String name=mName.getText().toString();
        final String email=mEmail.getText().toString();
        final String phone=mPhone.getText().toString();
        final String password=mPassword.getText().toString();
        final String repassword=mRePassword.getText().toString();

        boolean isPass=true;

        if (name.isEmpty()){
            mName.setError("请输入姓名");
            isPass=false;
        }else {
            mName.setError(null);
        }

        if (email.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mEmail.setError("错误的邮箱格式");
            isPass=false;
        }else {
            mEmail.setError(null);
        }

        if (phone.isEmpty()||phone.length()!=11){
            mPhone.setError("手机号码错误");
            isPass=false;
        }else {
            mPhone.setError(null);
        }

        if (password.isEmpty()||password.length()<6){
            mPassword.setError("请输入6位数密码");
            isPass=false;
        }else {
            mPassword.setError(null);
        }

        if (repassword.isEmpty()||repassword.length()<6||!(repassword.equals(password))){
            mRePassword.setError("密码验证错误");
            isPass=false;
        }else {
            mRePassword.setError(null);
        }

        return isPass;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}














