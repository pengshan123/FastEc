package com.pengshan.latte.ecc.main.personal.order;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.pengshan.latte.ecc.R;
import com.pengshan.latte_core.ui.recycle.MultipleFields;
import com.pengshan.latte_core.ui.recycle.MultipleItemEntity;
import com.pengshan.latte_core.ui.recycle.MultipleRecyclerAdapter;
import com.pengshan.latte_core.ui.recycle.MultipleViewHolder;

import java.util.List;

public class OrderListAdapter extends MultipleRecyclerAdapter {

    private RequestOptions OPTIONS=new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate();

    public OrderListAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(OrderListItemType.ITEM_ORDER_LIST,R.layout.item_order_list);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()){
            case OrderListItemType.ITEM_ORDER_LIST:
                final AppCompatImageView imageView=holder.getView(R.id.image_order_list);
                final AppCompatTextView title=holder.getView(R.id.tv_order_list_title);
                final AppCompatTextView price=holder.getView(R.id.tv_order_list_price);
                final AppCompatTextView time=holder.getView(R.id.tv_order_list_time);

                final String titleVal=entity.getField(MultipleFields.TITLE);
                final String timeval=entity.getField(OrderItemFields.TIME);
                final String priceVal=entity.getField(OrderItemFields.PRICE);
                final String imageUrl=entity.getField(MultipleFields.IMAGE_URL);

                title.setText(titleVal);
                price.setText("价格: "+priceVal);
                time.setText("时间： "+timeval);

                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(OPTIONS)
                        .into(imageView);

                break;
            default:
                break;
        }
    }
}


















