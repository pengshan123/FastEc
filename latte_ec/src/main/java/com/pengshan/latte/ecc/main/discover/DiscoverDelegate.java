package com.pengshan.latte.ecc.main.discover;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.pengshan.latte.ecc.R;
import com.pengshan.latte_core.delegates.bottom.BottomItemDelegate;
import com.pengshan.latte_core.delegates.web.WebDelegateImpl;

public class DiscoverDelegate extends BottomItemDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_discover;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        final WebDelegateImpl delegate=WebDelegateImpl.create("index.html");
        delegate.setTopDelegate(this.getParentDelegate());
        loadRootFragment(R.id.web_discover_container,delegate);
    }
}





















































































