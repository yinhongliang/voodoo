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

    private static final int LOCK_SIZE = 16;

    private static Object[] LOCK_HOLDER = new Object[LOCK_SIZE];

    static {
        for (int i = 0; i < LOCK_SIZE; i++) {
            LOCK_HOLDER[i] = i;
        }
    }

    /**
     * 以Publisher类型划分，同类型的Publisher则公用数据BUS
     */
    public static <T extends HotspotDataBusPublisher> T getInstance(String busName) {
        HotspotDataBusPublisher publisher = publishers.get(busName);

        if (publisher == null) {
            synchronized (getLock(busName)) {
                try {
                    publisher = new DefaultHotspotDataBusPublisher();
                    publishers.put(busName, publisher);
                    LOG.info("init publisher success,publisher type:{}", publisher);
                } catch (Exception e) {
                    LOG.error("init publisher fail,publisher type:{}", publisher, e);
                }
            }
        }
        return (T) publisher;
    }

    private static Object getLock(String busName) {
        return LOCK_HOLDER[Math.abs(busName.hashCode()) % LOCK_SIZE];
    }
}