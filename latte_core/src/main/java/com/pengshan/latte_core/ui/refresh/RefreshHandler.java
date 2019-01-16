package com.pengshan.latte_core.ui.refresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.pengshan.latte_core.app.Latte;
import com.pengshan.latte_core.net.RestClient;
import com.pengshan.latte_core.net.callback.ISuccess;
import com.pengshan.latte_core.ui.recycle.DataConverter;
import com.pengshan.latte_core.ui.recycle.MultipleRecyclerAdapter;

public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener {
    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final PagingBean BEAN;
    private final RecyclerView RECYCLERVIEW;
    private MultipleRecyclerAdapter mAdapter;
    private final DataConverter CONVERTER;


    private RefreshHandler(SwipeRefreshLayout refresh_layout,RecyclerView recyclerView,DataConverter converter,PagingBean bean) {
        REFRESH_LAYOUT = refresh_layout;
        REFRESH_LAYOUT.setOnRefreshListener(this);
        this.RECYCLERVIEW=recyclerView;
        this.CONVERTER=converter;
        this.BEAN=bean;

    }

    public static RefreshHandler create(SwipeRefreshLayout refresh_layout,RecyclerView recyclerView,DataConverter converter){
        return new RefreshHandler(refresh_layout,recyclerView,converter,new PagingBean());
    }

    private void refresh(){
        REFRESH_LAYOUT.setRefreshing(true);
        Latte.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                REFRESH_LAYOUT.setRefreshing(false);
            }
        },2000);
    }

    public void firstPage(String url){
        BEAN.setDelayed(1000);
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject object=JSON.parseObject(response);
                        BEAN.setTotal(object.getInteger("total"))
                                .setPageSize(object.getInteger("page_size"));

                        mAdapter=MultipleRecyclerAdapter.create(CONVERTER.setJsonData(response));
                        mAdapter.setOnLoadMoreListener(RefreshHandler.this,RECYCLERVIEW);
                        RECYCLERVIEW.setAdapter(mAdapter);
                        BEAN.addIndex();
                    }
                })
                .build()
                .get();
    }


    @Override
    public void onRefresh() {
        refresh();
    }

    @Override
    public void onLoadMoreRequested() {

    }
}
