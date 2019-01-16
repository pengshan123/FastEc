package com.pengshan.latte.ecc.main.personal.profile;

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

public class UserProfileDelegate extends LatteDelegate {
    @BindView(R2.id.rv_user_profile)
    RecyclerView mRecyclerView;



    @Override
    public Object setLayout() {
        return R.layout.delegate_user_profile;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        MultipleItemEntity image=MultipleItemEntity.builder()
                .setField(MultipleFields.ITEM_TYPE,ListItemType.ITEM_AVATAR)
                .setField(MultipleFields.ID,1)
                .setField(MultipleFields.IMAGE_URL,"")
                .build();

        MultipleItemEntity name=MultipleItemEntity.builder()
                .setField(MultipleFields.ITEM_TYPE,ListItemType.ITEM_NORMAL)
                .setField(MultipleFields.ID,2)
                .setField(MultipleFields.TEXT,"姓名")
                .setField(MultipleFields.VALUE,"山山")
                .build();

        final List<MultipleItemEntity> data=new ArrayList<>();

        data.add(image);
        data.add(name);

        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        ListAdapter adapter=new ListAdapter(data);
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.addOnItemTouchListener(new UserProfileOnClickListener(this));
    }
}



































