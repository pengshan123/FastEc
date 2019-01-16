package com.pengshan.latte_core.delegates.web.event;

import android.widget.Toast;

public class TestEvent extends Event{
    @Override
    public String execute(String params) {
        Toast.makeText(getContext(),params,Toast.LENGTH_LONG).show();
        return null;
    }
}
