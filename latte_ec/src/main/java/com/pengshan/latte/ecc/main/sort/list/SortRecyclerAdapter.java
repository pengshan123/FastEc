package com.pengshan.latte.ecc.main.sort.list;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.TextView;

import com.pengshan.latte.ecc.R;
import com.pengshan.latte.ecc.main.sort.SortDelegate;
import com.pengshan.latte.ecc.main.sort.content.ContentDelegate;
import com.pengshan.latte_core.delegates.LatteDelegate;
import com.pengshan.latte_core.ui.recycle.ItemType;
import com.pengshan.latte_core.ui.recycle.MultipleFields;
import com.pengshan.latte_core.ui.recycle.MultipleItemEntity;
import com.pengshan.latte_core.ui.recycle.MultipleRecyclerAdapter;
import com.pengshan.latte_core.ui.recycle.MultipleViewHolder;

import java.util.List;

public class SortRecyclerAdapter extends MultipleRecyclerAdapter {
    private final SortDelegate DELEGATE;
    private int mPrePosition=0;

    public SortRecyclerAdapter(List<MultipleItemEntity> data, SortDelegate delegate) {
        super(data);
        this.DELEGATE=delegate;
        addItemType(ItemType.VERTICAL_MENU_LIST,R.layout.item_vertical_menu_list);
    }

    @Override
    protected void convert(final MultipleViewHolder holder, final MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()){
            case ItemType.VERTICAL_MENU_LIST:
                final String text=entity.getField(MultipleFields.TEXT);
                final boolean isClicked=entity.getField(MultipleFields.TAG);
                final TextView name=holder.getView(R.id.tv_vertical_item_name);
                name.setText(text);
                final View line=holder.getView(R.id.view_line);
                final View itemView=holder.itemView;

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int currentPosition=holder.getAdapterPosition();
                        if (mPrePosition!=currentPosition){
                            getData().get(mPrePosition).setField(MultipleFields.TAG,false);
                            notifyItemChanged(mPrePosition);

                            entity.setField(MultipleFields.TAG,true);
                            notifyItemChanged(currentPosition);
                            mPrePosition=currentPosition;

                            final int contentId=getData().get(currentPosition).getField(MultipleFields.ID);
                            showContent(contentId);

                        }
                    }
                });

                if (!isClicked){
                    line.setVisibility(View.INVISIBLE);
                    name.setTextColor(ContextCompat.getColor(mContext,R.color.we_chat_black));
                    itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.item_background));
                }else {
                    line.setVisibility(View.VISIBLE);
                    name.setTextColor(ContextCompat.getColor(mContext,R.color.app_main));
                    line.setBackgroundColor(ContextCompat.getColor(mContext,R.color.app_main));
                    itemView.setBackgroundColor(Color.WHITE);
                }
                break;
            default:
                break;
        }
    }

    private void showContent(int contentId){
        final ContentDelegate delegate=ContentDelegate.newInstance(contentId);
        switchContent(delegate);

    }

    private void switchContent(ContentDelegate delegate){
        final LatteDelegate contentDelegate=DELEGATE.findChildFragment(ContentDelegate.class);
        if (contentDelegate!=null){
            contentDelegate.replaceFragment(delegate,false);
        }
    }
}




























