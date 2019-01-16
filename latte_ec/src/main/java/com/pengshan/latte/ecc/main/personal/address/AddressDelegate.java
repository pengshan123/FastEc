package com.pengshan.latte.ecc.main.personal.address;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pengshan.latte.ecc.R;
import com.pengshan.latte.ecc.R2;
import com.pengshan.latte_core.delegates.LatteDelegate;
import com.pengshan.latte_core.net.RestClient;
import com.pengshan.latte_core.net.callback.ISuccess;
import com.pengshan.latte_core.ui.recycle.MultipleItemEntity;

import java.util.List;

import butterknife.BindView;

public class AddressDelegate extends LatteDelegate implements ISuccess {
    @BindView(R2.id.rv_address)
    RecyclerView mRecyclerView=null;



    @Override
    public Object setLayout() {
        return R.layout.detegate_address;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        RestClient.builder()
                .url("address.php")
                .loader(getContext())
                .success(this)
                .build()
                .get();
    }

    @Override
    public void onSuccess(String response) {
        final LinearLayoutManager manager=new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        final List<MultipleItemEntity> data=new AddressDataConverter().setJsonData(response).convert();
        final AddressAdapter addressAdapter=new AddressAdapter(data);
        mRecyclerView.setAdapter(addressAdapter);
    }
}
















