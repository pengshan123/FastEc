package com.pengshan.latte_core.ui.recycle;

import android.support.annotation.ColorInt;

import com.choices.divider.DividerItemDecoration;

public class BaseDescoration extends DividerItemDecoration {

    private BaseDescoration(@ColorInt int color, int size) {
        setDividerLookup(new DividerLookupImpl(color,size));
    }

    public static BaseDescoration create(@ColorInt int color, int size){
        return new BaseDescoration(color,size);
    }


}
