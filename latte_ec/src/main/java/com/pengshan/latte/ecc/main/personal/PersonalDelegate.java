package com.pengshan.latte.ecc.main.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pengshan.latte.ecc.R;
import com.pengshan.latte.ecc.R2;
import com.pengshan.latte.ecc.main.personal.list.ListItemType;
import com.pengshan.latte.ecc.main.personal.list.ListAdapter;
import com.pengshan.latte.ecc.main.personal.order.OrderListDelegate;
import com.pengshan.latte.ecc.main.personal.profile.UserProfileDelegate;
import com.pengshan.latte.ecc.main.personal.setting.PersonalClickListener;
import com.pengshan.latte_core.delegates.bottom.BottomItemDelegate;
import com.pengshan.latte_core.ui.recycle.MultipleFields;
import com.pengshan.latte_core.ui.recycle.MultipleItemEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PersonalDelegate extends BottomItemDelegate {

    @BindView(R2.id.rv_personal_setting)
    RecyclerView mRvSettings;

    public static final String ORDER_TYPE="ORDER_TYPE";

    private Bundle mArgs;

    @OnClick(R2.id.tv_all_order)
    void onClickAllOrder(){
        mArgs.putString(ORDER_TYPE,"all");
        startOrderListByType();
    }

    @OnClick(R2.id.img_user_avatar)
    void onClickAvatar(){
        getParentDelegate().getSupportDelegate().start(new UserProfileDelegate());
    }

    private void startOrderListByType(){
        final OrderListDelegate delegate=new OrderListDelegate();
        delegate.setArguments(mArgs);
        getParentDelegate().getSupportDelegate().start(delegate);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArgs=new Bundle();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_personal;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        MultipleItemEntity address=MultipleItemEntity.builder()
                .setField(MultipleFields.ITEM_TYPE,ListItemType.ITEM_NORMAL)
                .setField(MultipleFields.ID,1)
                .setField(MultipleFields.TEXT,"收货地址")
                .build();

        MultipleItemEntity system=MultipleItemEntity.builder()
                .setField(MultipleFields.ITEM_TYPE,ListItemType.ITEM_NORMAL)
                .setField(MultipleFields.TEXT,"系统设置")
                .setField(MultipleFields.ID,2)
                .build();


        final List<MultipleItemEntity> data=new ArrayList<>();

        data.add(address);
        data.add(system);

        final LinearLayoutManager manager=new LinearLayoutManager(getContext());
        mRvSettings.setLayoutManager(manager);
        ListAdapter adapter=new ListAdapter(data);
        mRvSettings.setAdapter(adapter);
        mRvSettings.addOnItemTouchListener(new PersonalClickListener(this));
    }
}















