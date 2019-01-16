package com.pengshan.latte_core.delegates.bottom;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.joanzapata.iconify.widget.IconTextView;
import com.pengshan.latte_core.R;
import com.pengshan.latte_core.R2;
import com.pengshan.latte_core.delegates.LatteDelegate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

public abstract class BaseBottomDelegate extends LatteDelegate implements View.OnClickListener {
    private final ArrayList<BottomItemDelegate> ITEM_DELEGATES=new ArrayList<>();
    private final ArrayList<BottomTabBean> TAB_BEANS=new ArrayList<>();
    private final LinkedHashMap<BottomTabBean,BottomItemDelegate> ITEMS=new LinkedHashMap<>();
    private int mCurrentDelegate=0;
    private int mIndexDelegate=0;
    private int mClickedColor=Color.RED;

    public abstract LinkedHashMap<BottomTabBean,BottomItemDelegate> setItems(ItemBuilder builder);
    public abstract int setIndexDelegate();

    @BindView(R2.id.bottom_bar)
    LinearLayout mBottomBar;
//    @BindView(R2.id.bottom_bar_delegate_container)
//    FrameLayout

    @Override
    public Object setLayout() {
        return R.layout.bottom_layout;
    }

    @ColorInt
    public abstract int setClickedColor();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mIndexDelegate=setIndexDelegate();
        if (setClickedColor()!=0){
            mClickedColor=setClickedColor();
        }

        final ItemBuilder builder=ItemBuilder.builder();
        final LinkedHashMap<BottomTabBean,BottomItemDelegate> items=setItems(builder);
        ITEMS.putAll(items);

        for (Map.Entry<BottomTabBean,BottomItemDelegate> item:ITEMS.entrySet()){
            final BottomTabBean key=item.getKey();
            final BottomItemDelegate value=item.getValue();
            TAB_BEANS.add(key);
            ITEM_DELEGATES.add(value);
        }

    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final int size=ITEMS.size();
        for (int i = 0; i < size; i++) {
            LayoutInflater.from(getContext()).inflate(R.layout.bottom_item_icon_text_layout,mBottomBar);
            final RelativeLayout item= (RelativeLayout) mBottomBar.getChildAt(i);
            item.setTag(i);
            item.setOnClickListener(this);
            final IconTextView itemIcon= (IconTextView) item.getChildAt(0);
            final AppCompatTextView itemTitle= (AppCompatTextView) item.getChildAt(1);
            final BottomTabBean bean=TAB_BEANS.get(i);
            itemIcon.setText(bean.getIcon());
            itemTitle.setText(bean.getTitle());
            if (i==mIndexDelegate){
                itemIcon.setTextColor(mClickedColor);
                itemTitle.setTextColor(mClickedColor);
            }
        }

        final SupportFragment[] delegateArray=ITEM_DELEGATES.toArray(new SupportFragment[size]);
        loadMultipleRootFragment(R.id.bottom_bar_delegate_container,mIndexDelegate,delegateArray);
    }

    private void resetColor(){
        final int count=mBottomBar.getChildCount();
        for (int i = 0; i <count ; i++) {
            final RelativeLayout item= (RelativeLayout) mBottomBar.getChildAt(i);
            final IconTextView itemIcon= (IconTextView) item.getChildAt(0);
            itemIcon.setTextColor(Color.GRAY);
            final AppCompatTextView itemTitle= (AppCompatTextView) item.getChildAt(1);
            itemTitle.setTextColor(Color.GRAY);
        }
    }

    @Override
    public void onClick(View v) {
        final int tag= (int) v.getTag();
        resetColor();
        final RelativeLayout item= (RelativeLayout) v;
        final IconTextView itemIcon= (IconTextView) item.getChildAt(0);
        itemIcon.setTextColor(mClickedColor);
        final AppCompatTextView itemTitle= (AppCompatTextView) item.getChildAt(1);
        itemTitle.setTextColor(mClickedColor);
        showHideFragment(ITEM_DELEGATES.get(tag),ITEM_DELEGATES.get(mCurrentDelegate));
        mCurrentDelegate=tag;
    }
}













