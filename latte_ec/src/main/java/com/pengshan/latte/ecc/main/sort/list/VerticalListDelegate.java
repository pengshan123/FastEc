package com.pengshan.latte.ecc.main.sort.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pengshan.latte.ecc.R;
import com.pengshan.latte.ecc.R2;
import com.pengshan.latte.ecc.main.sort.SortDelegate;
import com.pengshan.latte_core.delegates.LatteDelegate;
import com.pengshan.latte_core.net.RestClient;
import com.pengshan.latte_core.net.callback.ISuccess;
import com.pengshan.latte_core.ui.recycle.MultipleItemEntity;

import java.util.List;

import butterknife.BindView;

public class VerticalListDelegate extends LatteDelegate {
    @BindView(R2.id.rv_vertical_menu_list)
    RecyclerView mRecyclerView;




    private void initRecyclerView(){
        final LinearLayoutManager manager=new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(null);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_vertical_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initRecyclerView();

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder()
                .url("sort_list")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final List<MultipleItemEntity> data=
                                new VerticalListDataConverter().setJsonData(response).convert();
                        final SortDelegate delegate=getParentDelegate();
                        final SortRecyclerAdapter adapter=new SortRecyclerAdapter(data,delegate);
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .build()
                .get();
    }
}












