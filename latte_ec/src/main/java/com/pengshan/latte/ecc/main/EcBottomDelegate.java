package com.pengshan.latte.ecc.main;

import android.graphics.Color;

import com.pengshan.latte.ecc.main.cart.ShopCartDelegate;
import com.pengshan.latte.ecc.main.discover.DiscoverDelegate;
import com.pengshan.latte.ecc.main.index.IndexDelegate;
import com.pengshan.latte.ecc.main.personal.PersonalDelegate;
import com.pengshan.latte.ecc.main.sort.SortDelegate;
import com.pengshan.latte_core.delegates.bottom.BaseBottomDelegate;
import com.pengshan.latte_core.delegates.bottom.BottomItemDelegate;
import com.pengshan.latte_core.delegates.bottom.BottomTabBean;
import com.pengshan.latte_core.delegates.bottom.ItemBuilder;

import java.util.LinkedHashMap;

public class EcBottomDelegate extends BaseBottomDelegate {
    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottomTabBean,BottomItemDelegate> items=new LinkedHashMap<>();
        items.put(new BottomTabBean("{fa-home}","主页"),new IndexDelegate());
        items.put(new BottomTabBean("{fa-sort}","分类"),new SortDelegate());
        items.put(new BottomTabBean("{fa-compass}","发现"),new DiscoverDelegate());
        items.put(new BottomTabBean("{fa-shopping-cart}","购物车"),new ShopCartDelegate());
        items.put(new BottomTabBean("{fa-user}","我的"),new PersonalDelegate());
        return builder.addItems(items).build();
    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }

    @Override
    public int setClickedColor() {
        return Color.parseColor("#ffff8800");
    }
}
