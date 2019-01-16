package com.pengshan.latte_core.delegates.web.event;

import android.support.annotation.NonNull;

import java.util.HashMap;

public class EventMannager {

    private static final HashMap<String,Event> EVENTS=new HashMap<>();

    private EventMannager(){

    }

    private static final class Holder{
        private static final EventMannager INSTANCE=new EventMannager();
    }

    public static EventMannager getInstance(){
        return Holder.INSTANCE;
    }

    public EventMannager addEvent(@NonNull String name, @NonNull Event event){
        EVENTS.put(name,event);
        return this;
    }


    public Event createEvent(@NonNull String action){
        final Event event=EVENTS.get(action);
        if (event==null){
            return new UndefindEvent();
        }

        return event;
    }
}











