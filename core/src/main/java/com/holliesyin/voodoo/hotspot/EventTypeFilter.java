package com.holliesyin.voodoo.hotspot;

/**
 * Created by @author Hollies Yin on 2018-05-29.
 */
public class EventTypeFilter implements Filter {
    private Class eventType;

    public EventTypeFilter(Class eventType) {
        this.eventType = eventType;
    }

    @Override
    public boolean filter(Event event) {
        return event.getClass() == eventType;
    }
}