package com.holliesyin.voodoo.hotspot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by @author Hollies Yin on 2018-05-29.
 */
public class HotspotDataBusPublisherFactory {
    private final static Logger LOG = LoggerFactory.getLogger(HotspotDataBusPublisherFactory.class);

    private static ConcurrentHashMap<String, HotspotDataBusPublisher> publishers = new ConcurrentHashMap<>();

    /**
     * 以Publisher类型划分，同类型的Publisher则公用数据BUS
     */
    public static <T extends HotspotDataBusPublisher> T getInstance(String busName) {
        HotspotDataBusPublisher publisher = publishers.get(busName);

        if (publisher == null) {
            synchronized (LockFactory.getDataBusLock(busName)) {
                try {
                    publisher = new DefaultHotspotDataBusPublisher(busName);
                    publishers.put(busName, publisher);
                    LOG.info("init publisher success,busName:{}",busName);
                } catch (Exception e) {
                    LOG.error("init publisher fail,busName:{}", busName, e);
                }
            }
        }
        return (T) publisher;
    }
}