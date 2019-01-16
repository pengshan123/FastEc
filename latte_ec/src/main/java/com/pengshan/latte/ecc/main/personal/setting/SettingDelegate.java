package com.pengshan.latte.ecc.main.personal.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pengshan.latte.ecc.R;
import com.pengshan.latte.ecc.R2;
import com.pengshan.latte.ecc.main.personal.list.ListAdapter;
import com.pengshan.latte.ecc.main.personal.list.ListItemType;
import com.pengshan.latte_core.delegates.LatteDelegate;
import com.pengshan.latte_core.ui.recycle.MultipleFields;
import com.pengshan.latte_core.ui.recycle.MultipleItemEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SettingDelegate extends LatteDelegate {

    @BindView(R2.id.rv_settings)
    RecyclerView mRecyclerView;

    @Override
    public Object setLayout() {
        return R.layout.delegate_setting;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        MultipleItemEntity push=MultipleItemEntity.builder()
                .setField(MultipleFields.ITEM_TYPE,ListItemType.ITEM_NORMAL)
                .setField(MultipleFields.ID,1)
                .setField(MultipleFields.TEXT,"消息推送")
                .build();

        MultipleItemEntity about=MultipleItemEntity.builder()
                .setField(MultipleFields.ITEM_TYPE,ListItemType.ITEM_NORMAL)
                .setField(MultipleFields.ID,1)
                .setField(MultipleFields.TEXT,"关于")
                .build();

        final List<MultipleItemEntity> data=new ArrayList<>();
        data.add(push);
        data.add(about);

        //设置RecyclerView
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        final ListAdapter adapter = new ListAdapter(data);
        mRecyclerView.setAdapter(adapter);
        //mRecyclerView.addOnItemTouchListener(new SettingsClickListener(this));

    }
}




















