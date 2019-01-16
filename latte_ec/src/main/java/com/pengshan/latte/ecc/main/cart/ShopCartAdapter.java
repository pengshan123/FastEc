package com.pengshan.latte.ecc.main.cart;


import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.joanzapata.iconify.widget.IconTextView;
import com.pengshan.latte.ecc.R;
import com.pengshan.latte_core.app.Latte;
import com.pengshan.latte_core.net.RestClient;
import com.pengshan.latte_core.net.callback.ISuccess;
import com.pengshan.latte_core.store.MapManager;
import com.pengshan.latte_core.ui.recycle.MultipleFields;
import com.pengshan.latte_core.ui.recycle.MultipleItemEntity;
import com.pengshan.latte_core.ui.recycle.MultipleRecyclerAdapter;
import com.pengshan.latte_core.ui.recycle.MultipleViewHolder;

import java.util.List;

public class ShopCartAdapter extends MultipleRecyclerAdapter {
    private static final RequestOptions OPTIONS=new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate();

    private boolean mIsSelectedAll=false;

    private IcartItemListener mIcartItemListener;

    private double mToalPrice=0.00;


    public ShopCartAdapter(List<MultipleItemEntity> data) {
        super(data);

        for (MultipleItemEntity entity:data) {
            final double price=entity.getField(ShopCartFields.PRICE);
            final double count=entity.getField(ShopCartFields.COUNT);
            final double total=price*count;
            mToalPrice=mToalPrice+total;
        }
        addItemType(ShopCartItemType.SHOP_CART_ITEM,R.layout.item_shop_cart);
    }

    public void setIsSelectedAll(boolean isSelectedAll){
        this.mIsSelectedAll=isSelectedAll;

    }

    public void setIcartItemListener(IcartItemListener listener){
        this.mIcartItemListener=listener;
    }

    public double getTotalPrice(){
        return mToalPrice;
    }

    @Override
    protected void convert(MultipleViewHolder holder, final MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()){
            case ShopCartItemType.SHOP_CART_ITEM:
                final int id=entity.getField(MultipleFields.ID);
                final String thumb=entity.getField(MultipleFields.IMAGE_URL);
                final String title=entity.getField(ShopCartFields.TITLE);
                final String desc=entity.getField(ShopCartFields.DESC);
                final double count=entity.getField(ShopCartFields.COUNT);
                final double price=entity.getField(ShopCartFields.PRICE);


                final ImageView imgThumb=holder.getView(R.id.image_item_shop_cart);
                final TextView tvTitle=holder.getView(R.id.tv_item_shop_cart_title);
                final TextView tvDesc=holder.getView(R.id.tv_item_shop_cart_desc);
                final TextView tvPrice=holder.getView(R.id.tv_item_shop_cart_price);
                final IconTextView iconMinus=holder.getView(R.id.icon_item_minus);
                final IconTextView iconPlus=holder.getView(R.id.icon_item_plus);
                final TextView tvCount=holder.getView(R.id.tv_item_shop_cart_count);
                final IconTextView iconIsSelected=holder.getView(R.id.icon_item_shop_cart);

                tvTitle.setText(title);
                tvDesc.setText(desc);
                tvPrice.setText(String.valueOf(price));
                tvCount.setText(String.valueOf(count));
                Glide.with(mContext)
                        .load(thumb)
                        .into(imgThumb);

                entity.setField(ShopCartFields.IS_SELECTED,mIsSelectedAll);

                final boolean isSelected=entity.getField(ShopCartFields.IS_SELECTED);

                if (isSelected){
                    iconIsSelected.setTextColor
                            (ContextCompat.getColor((Context) MapManager.getInstance().getStore().get(MapManager.KEY),R.color.app_main));
                }else {
                    iconIsSelected.setTextColor(Color.GRAY);
                }

                iconIsSelected.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final boolean currentSelected=entity.getField(ShopCartFields.IS_SELECTED);
                        if (currentSelected){
                            iconIsSelected.setTextColor(Color.GRAY);
                            entity.setField(ShopCartFields.IS_SELECTED,false);
                        }else {
                            iconIsSelected.setTextColor
                                    (ContextCompat.getColor((Context) MapManager.getInstance().getStore().get(MapManager.KEY),R.color.app_main));
                            entity.setField(ShopCartFields.IS_SELECTED,true);
                        }

                    }
                });

                iconMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final double count=entity.getField(ShopCartFields.COUNT);
                        if (Integer.parseInt(tvCount.getText().toString())>1){
                            RestClient.builder()
                                    .url("")
                                    .loader(mContext)
                                    .params("count",count)
                                    .success(new ISuccess() {
                                        @Override
                                        public void onSuccess(String response) {
                                            int countNum=Integer.parseInt(tvCount.getText().toString());
                                            countNum--;
                                            tvCount.setText(String.valueOf(countNum));

                                            if (mIcartItemListener!=null){
                                                mToalPrice=mToalPrice-price;
                                                final double itemTotal=countNum*price;
                                                mIcartItemListener.onItemClick(itemTotal);
                                            }
                                        }
                                    })
                                    .build()
                                    .post();

                        }
                    }
                });

                iconPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final double count=entity.getField(ShopCartFields.COUNT);
                        RestClient.builder()
                                .url("")
                                .loader(mContext)
                                .params("count",count)
                                .success(new ISuccess() {
                                    @Override
                                    public void onSuccess(String response) {
                                        int countNum=Integer.parseInt(tvCount.getText().toString());
                                        countNum++;
                                        tvCount.setText(String.valueOf(countNum));

                                        if (mIcartItemListener!=null){
                                            mToalPrice=mToalPrice+price;
                                            final double itemTotal=countNum*price;
                                            mIcartItemListener.onItemClick(itemTotal);
                                        }
                                    }
                                })
                                .build()
                                .post();
                    }
                });

                break;
             default:
                 break;
        }

    }
}

























