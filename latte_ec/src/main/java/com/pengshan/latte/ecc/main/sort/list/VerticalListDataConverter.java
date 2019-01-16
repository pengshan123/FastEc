package com.pengshan.latte.ecc.main.sort.list;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pengshan.latte_core.ui.recycle.DataConverter;
import com.pengshan.latte_core.ui.recycle.ItemType;
import com.pengshan.latte_core.ui.recycle.MultipleFields;
import com.pengshan.latte_core.ui.recycle.MultipleItemEntity;

import java.util.ArrayList;

public final class VerticalListDataConverter extends DataConverter {

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final ArrayList<MultipleItemEntity> datalist=new ArrayList<>();
        final JSONArray dataArray=JSON
                .parseObject(getJsonData())
                .getJSONObject("data")
                .getJSONArray("list");
        final int size=dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data=dataArray.getJSONObject(i);
            final int id=data.getInteger("id");
            final String name=data.getString("name");

            final MultipleItemEntity entity=MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE,ItemType.VERTICAL_MENU_LIST)
                    .setField(MultipleFields.ID,id)
                    .setField(MultipleFields.TEXT,name)
                    .setField(MultipleFields.TAG,false)
                    .build();

            datalist.add(entity);
            datalist.get(0).setField(MultipleFields.TAG,true);
        }
        return datalist;
    }
}
















