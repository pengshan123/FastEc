package com.pengshan.latte.ecc.main.sort;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.pengshan.latte.ecc.R;
import com.pengshan.latte.ecc.main.sort.content.ContentDelegate;
import com.pengshan.latte.ecc.main.sort.list.VerticalListDelegate;
import com.pengshan.latte_core.delegates.bottom.BottomItemDelegate;

public class SortDelegate extends BottomItemDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_sort;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        final VerticalListDelegate listDelegate=new VerticalListDelegate();
        loadRootFragment(R.id.vertical_list_container,listDelegate);
        getParentDelegate().loadRootFragment(R.id.sort_content_container,ContentDelegate.newInstance(1));

    }
}













