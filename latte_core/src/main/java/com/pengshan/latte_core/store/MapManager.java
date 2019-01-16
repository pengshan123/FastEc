package com.pengshan.latte_core.store;

import java.util.HashMap;
import java.util.Map;

public class MapManager {
    private Map<String,Object> STOREMAP=new HashMap<>();

    public static final String KEY="key";

    private static final class Holder{
        private static final MapManager INSTANCE=new MapManager();
    }

    private MapManager(){

    }

    public static MapManager getInstance(){
        return Holder.INSTANCE;
    }


    public Map<String,Object> getStore(){
        return STOREMAP;
    }


}













































