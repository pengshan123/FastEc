package com.pengshan.latte.ecc.main.personal.setting;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.pengshan.latte.ecc.main.personal.address.AddressDelegate;
import com.pengshan.latte_core.delegates.LatteDelegate;
import com.pengshan.latte_core.ui.recycle.MultipleFields;
import com.pengshan.latte_core.ui.recycle.MultipleItemEntity;

public class PersonalClickListener extends SimpleClickListener {

    private final LatteDelegate DELEGATE;

    public PersonalClickListener(LatteDelegate delegate) {
        DELEGATE = delegate;
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        MultipleItemEntity entity= (MultipleItemEntity) baseQuickAdapter.getData().get(position);
        int id=entity.getField(MultipleFields.ID);
        switch (id){
            case 1:
                DELEGATE.getParentDelegate().getSupportDelegate().start(new AddressDelegate());

                break;
            case 2:
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
