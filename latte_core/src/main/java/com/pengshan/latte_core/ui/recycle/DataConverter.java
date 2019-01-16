package com.pengshan.latte_core.ui.recycle;

import java.util.ArrayList;

public abstract class DataConverter {
    protected final ArrayList<MultipleItemEntity> ENTITLES=new ArrayList<>();

    private String mJsonData=null;

    public abstract ArrayList<MultipleItemEntity> convert();

    public DataConverter setJsonData(String jsonData){
        this.mJsonData=jsonData;
        return this;
    }

    protected String getJsonData(){
        if (mJsonData==null||mJsonData.isEmpty()){
            throw new NullPointerException("DATA IS NULL");
        }

        return mJsonData;
    }

}












