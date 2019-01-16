package com.pengshan.latte.ecc.main.personal.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pengshan.latte_core.ui.recycle.DataConverter;
import com.pengshan.latte_core.ui.recycle.MultipleFields;
import com.pengshan.latte_core.ui.recycle.MultipleItemEntity;

import java.util.ArrayList;

public class OrderListDataConverter extends DataConverter {

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final JSONArray array=JSON.parseObject(getJsonData()).getJSONArray("data");
        final int size=array.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data=array.getJSONObject(i);
            final String thumb=data.getString("thumb");
            final String title=data.getString("title");
            final int id=data.getInteger("id");
            final double price=data.getDouble("price");
            final String time=data.getString("time");

            final MultipleItemEntity entity=MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE,OrderListItemType.ITEM_ORDER_LIST)
                    .setField(MultipleFields.ID,id)
                    .setField(MultipleFields.IMAGE_URL,thumb)
                    .setField(MultipleFields.TITLE,title)
                    .setField(OrderItemFields.TIME,time)
                    .setField(OrderItemFields.PRICE,price)
                    .build();

            ENTITLES.add(entity);
        }
        return ENTITLES;
    }
}
























