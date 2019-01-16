package com.pengshan.latte.ecc.main.personal.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.pengshan.latte.ecc.R;
import com.pengshan.latte_core.delegates.LatteDelegate;

public class NameDelegate extends LatteDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_name;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
