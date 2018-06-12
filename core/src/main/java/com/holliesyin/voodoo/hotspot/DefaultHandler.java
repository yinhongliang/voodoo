package com.holliesyin.voodoo.hotspot;

import java.util.List;

/**
 * Created by @author Hollies Yin on 2018-06-12.
 */
public abstract class DefaultHandler implements Handler{

    @Override
    public abstract void initContext();

    @Override
    public void handle(List<Event> events) {
        initContext();
        doHandle(events);
        postHandle();
    }

    @Override
    public abstract void doHandle(List<Event> events);

    @Override
    public abstract void postHandle();
}