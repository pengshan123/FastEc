package com.pengshan.latte.ecc.sign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pengshan.latte.ecc.database.DatabaseManager;
import com.pengshan.latte.ecc.database.UserProfile;
import com.pengshan.latte_core.app.AccountManager;

public class SignHandler {
    public static void onSignIn(String response,ISignListener signListener){
        final JSONObject profileJson=JSON.parseObject(response).getJSONObject("data");
        long userId=profileJson.getLong("userId");
        String name=profileJson.getString("name");
        String avatar=profileJson.getString("avatar");
        String gender=profileJson.getString("gender");
        String address=profileJson.getString("address");

        final UserProfile profile=new UserProfile(userId,name,avatar,gender,address);

        DatabaseManager.getInstance().getDao().insert(profile);

        AccountManager.setSignState(true);
        signListener.onSignInSuccess();
    }

    public static void onSignUp(String response,ISignListener signListener){
        final JSONObject profileJson=JSON.parseObject(response).getJSONObject("data");
        long userId=profileJson.getLong("userId");
        String name=profileJson.getString("name");
        String avatar=profileJson.getString("avatar");
        String gender=profileJson.getString("gender");
        String address=profileJson.getString("address");

        final UserProfile profile=new UserProfile(userId,name,avatar,gender,address);
        DatabaseManager.getInstance().getDao().insert(profile);

        AccountManager.setSignState(true);
        signListener.onSignUpSuccess();
    }
}
