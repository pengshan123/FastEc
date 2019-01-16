package com.pengshan.latte.ecc.main.cart;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ViewStubCompat;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.joanzapata.iconify.widget.IconTextView;
import com.pengshan.latte.ecc.R;
import com.pengshan.latte.ecc.R2;
import com.pengshan.latte.ecc.pay.FastPay;
import com.pengshan.latte.ecc.pay.IAIPayResultListener;
import com.pengshan.latte.ecc.pay.PayAsyncTask;
import com.pengshan.latte_core.delegates.bottom.BottomItemDelegate;
import com.pengshan.latte_core.net.RestClient;
import com.pengshan.latte_core.net.callback.ISuccess;
import com.pengshan.latte_core.ui.recycle.MultipleItemEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class ShopCartDelegate extends BottomItemDelegate implements ISuccess ,IcartItemListener{
    private int mCurrentCount=0;
    private int mTotalCount=0;
    private double mTotalPrice=0.00;



    @BindView(R2.id.iv_shop_cart)
    RecyclerView mRecyclerView=null;

    @BindView(R2.id.icon_shop_cart_select_all)
    IconTextView mIconSelectAll=null;

    @BindView(R2.id.stub)
    ViewStub mStubNoItem=null;

    @BindView(R2.id.tv_shop_cart_total_price)
    TextView mTvTotalPrice=null;


    @OnClick(R2.id.tv_shop_cart_pay)
    void onClickPay(){
        FastPay.create(this).beginPayDialog();
    }

    private void createOrder(){
        final String orderUrl="";
        final WeakHashMap<String,Object> orderParams=new WeakHashMap<>();
        orderParams.put("userid","");
        orderParams.put("amount",0.01);
        orderParams.put("comment","测试支付");
        orderParams.put("type",1);
        orderParams.put("ordertype",0);
        orderParams.put("isanonymous",true);
        orderParams.put("followeduser",0);
        RestClient.builder()
                .url(orderUrl)
                .loader(getContext())
                .params(orderParams)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final int orderId=JSON.parseObject(response).getInteger("result");
                        FastPay.create(ShopCartDelegate.this)
                                .setPayResultListener(new IAIPayResultListener() {
                                    @Override
                                    public void onPaySuccess() {

                                    }

                                    @Override
                                    public void onPaying() {

                                    }

                                    @Override
                                    public void onPayFail() {

                                    }

                                    @Override
                                    public void onPayCancel() {

                                    }

                                    @Override
                                    public void onPayConnnectError() {

                                    }
                                })
                                .setOrderId(orderId)
                                .beginPayDialog();
                    }

                })
                .build()
                .post();
    }

    @OnClick(R2.id.tv_top_shop_cart_remove_selected)
    void onClickRemoveSelectedItem(){
        final List<MultipleItemEntity> data=adapter.getData();
        List<MultipleItemEntity> deleteEntities=new ArrayList<>();
        for (MultipleItemEntity entity:data) {
            final boolean isSelected=entity.getField(ShopCartFields.IS_SELECTED);
            if (isSelected){
                deleteEntities.add(entity);
            }
        }
        for (MultipleItemEntity entity:deleteEntities) {
            int removePosition;
            final int entityPosition=entity.getField(ShopCartFields.POSITION);
            if (entityPosition>mCurrentCount-1){
                removePosition=entityPosition-(mTotalCount-mCurrentCount);
            }else {
                removePosition=entityPosition;
            }

            if (removePosition<=adapter.getItemCount()){
                adapter.remove(removePosition);
                mCurrentCount=adapter.getItemCount();
                adapter.notifyItemRangeChanged(removePosition,adapter.getItemCount());
            }
        }
        checkItemCount();
    }

    @OnClick(R2.id.tv_top_shop_cart_clear)
    void onClickClear(){
        adapter.getData().clear();
        adapter.notifyDataSetChanged();
        checkItemCount();
    }

    private void checkItemCount(){
        final int count=adapter.getItemCount();
        if (count==0){
            final View stubView=mStubNoItem.inflate();
            final TextView tvToBuy=stubView.findViewById(R.id.tv_stub_to_buy);
            tvToBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),"您该购物了",Toast.LENGTH_LONG).show();
                }
            });

            mRecyclerView.setVisibility(View.GONE);
        }else {
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R2.id.icon_shop_cart_select_all)
    void onCLickSelectedAll(){
        final int tag= (int) mIconSelectAll.getTag();
        if (tag==0){
            mIconSelectAll.setTextColor(ContextCompat.getColor(getContext(),R.color.app_main));
            mIconSelectAll.setTag(1);
            adapter.setIsSelectedAll(true);
            adapter.notifyItemRangeChanged(0,adapter.getItemCount());
        }else {
            mIconSelectAll.setTextColor(Color.GRAY);
            mIconSelectAll.setTag(0);
            adapter.setIsSelectedAll(false);
            adapter.notifyItemRangeChanged(0,adapter.getItemCount());
        }
    }

    private ShopCartAdapter adapter;

    @Override
    public Object setLayout() {
        return R.layout.delegate_shop_cart;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mIconSelectAll.setTag(0);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder()
                .url("shop_cart")
                .loader(getContext())
                .success(this)
                .build()
                .get();
                
    }

    @Override
    public void onSuccess(String response) {
        final List<MultipleItemEntity> data=
                new ShopCartDataConverter().setJsonData(response).convert();
        adapter=new ShopCartAdapter(data);
        adapter.setIcartItemListener(this);
        final LinearLayoutManager manager=new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
        mTotalPrice=adapter.getTotalPrice();
        checkItemCount();
    }

    @Override
    public void onItemClick(double itemTotalPrice) {
        final double price=adapter.getTotalPrice();
        mTvTotalPrice.setText(String.valueOf(price));

    }


}
















