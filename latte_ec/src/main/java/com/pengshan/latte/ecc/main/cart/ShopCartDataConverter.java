package com.pengshan.latte.ecc.main.cart;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pengshan.latte_core.ui.recycle.DataConverter;
import com.pengshan.latte_core.ui.recycle.MultipleFields;
import com.pengshan.latte_core.ui.recycle.MultipleItemEntity;

import java.util.ArrayList;

public class ShopCartDataConverter extends DataConverter {

    @Override
    public ArrayList<MultipleItemEntity> convert() {

        final ArrayList<MultipleItemEntity> dataList=new ArrayList<>();

        final JSONArray dataArray=JSON.parseObject(getJsonData()).getJSONArray("data");

        final int size=dataArray.size();

        for (int i = 0; i <size ; i++) {
            final JSONObject data=dataArray.getJSONObject(i);
            final String thumb=data.getString("thumb");
            final String desc=data.getString("desc");
            final String title=data.getString("title");
            final int id=data.getInteger("id");
            final double count=data.getInteger("count");
            final double price=data.getDouble("price");

            final MultipleItemEntity entity=MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE,6)
                    .setField(MultipleFields.ID,id)
                    .setField(MultipleFields.IMAGE_URL,thumb)
                    .setField(ShopCartFields.TITLE,title)
                    .setField(ShopCartFields.DESC,desc)
                    .setField(ShopCartFields.COUNT,count)
                    .setField(ShopCartFields.PRICE,price)
                    .setField(ShopCartFields.IS_SELECTED,false)
                    .setField(ShopCartFields.POSITION,i)
                    .build();

            dataList.add(entity);

        }

        return dataList;
    }
}


















