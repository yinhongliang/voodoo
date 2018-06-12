package com.holliesyin.voodoo.hotspot;

/**
 * Created by @author Hollies Yin on 2018-05-29.
 */
public interface HotspotDataBusPublisher {
    /**
     * 发布事件
     */
    void publish(Event event);
    /**
     * 注册订阅者
     */
    void subscribe(HotspotDataBusSubscriber subscriber);

    /**
     * 注销订阅者
     */
    void unSubscribe(HotspotDataBusSubscriber subscriber);
}