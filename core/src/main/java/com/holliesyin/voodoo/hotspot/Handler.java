package com.holliesyin.voodoo.hotspot;

import java.util.List;

/**
 * Created by @author Hollies Yin on 2018-05-28.
 */
public interface Handler {
    void initContext();
    void handle(List<Event> events);
    void doHandle(List<Event> events);
    void postHandle();
}