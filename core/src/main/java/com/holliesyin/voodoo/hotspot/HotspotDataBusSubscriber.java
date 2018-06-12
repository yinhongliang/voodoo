package com.holliesyin.voodoo.hotspot;

/**
 * Created by @author Hollies Yin on 2018-05-29.
 */
public interface HotspotDataBusSubscriber {
    void addFilter(Filter filter);
    void acceptEvent(Event event);
    void addHandler(Handler handler);
    void subscribe(HotspotDataBusPublisher publisher);
    void unSubscribe(HotspotDataBusPublisher publisher);
    void commit();
    void cancel();
}