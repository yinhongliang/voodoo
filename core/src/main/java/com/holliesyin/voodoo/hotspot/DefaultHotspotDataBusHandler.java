package com.holliesyin.voodoo.hotspot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by @author Hollies Yin on 2018-06-12.
 */
public abstract class DefaultHotspotDataBusHandler implements Handler{

    protected final Logger LOG = LoggerFactory.getLogger(getClass());

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