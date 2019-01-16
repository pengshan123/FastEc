package com.pengshan.latte.ecc.main.personal.list;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.pengshan.latte.ecc.R;
import com.pengshan.latte_core.ui.recycle.MultipleFields;
import com.pengshan.latte_core.ui.recycle.MultipleItemEntity;
import com.pengshan.latte_core.ui.recycle.MultipleRecyclerAdapter;
import com.pengshan.latte_core.ui.recycle.MultipleViewHolder;

import java.util.List;

public class ListAdapter extends MultipleRecyclerAdapter {
    private static final RequestOptions OPTIONS=new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate();


    public ListAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(ListItemType.ITEM_NORMAL,R.layout.arrow_item_layout);
        addItemType(ListItemType.ITEM_AVATAR,R.layout.arrow_item_avatar);
    }

    @Override
    protected void convert(final MultipleViewHolder holder, final MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()){
            case ListItemType.ITEM_NORMAL:
                final String text=entity.getField(MultipleFields.TEXT);
                final String value=entity.getField(MultipleFields.VALUE);
                holder.setText(R.id.tv_arrow_text,text);
                holder.setText(R.id.tv_arrow_value,value);
                break;
            case ListItemType.ITEM_AVATAR:
                Glide.with(mContext)
                        .load("")
                        .apply(OPTIONS)
                        .into((ImageView) holder.getView(R.id.img_arrow_avatar));
                break;

            default:
                break;
        }
    }

}




























