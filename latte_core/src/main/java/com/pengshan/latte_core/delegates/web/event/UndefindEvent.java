package com.pengshan.latte_core.delegates.web.event;

import android.util.Log;
import android.widget.Toast;

public class UndefindEvent extends Event{
    @Override
    public String execute(String params) {
        Log.e("pengshan","==--");
        return null;
    }
}
