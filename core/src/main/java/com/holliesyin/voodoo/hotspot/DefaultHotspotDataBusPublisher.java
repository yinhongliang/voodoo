package com.holliesyin.voodoo.hotspot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by @author Hollies Yin on 2018-05-29.
 */
public class DefaultHotspotDataBusPublisher implements HotspotDataBusPublisher {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultHotspotDataBusPublisher.class);
    private String busName;
    private List<HotspotDataBusSubscriber> clients;

    public DefaultHotspotDataBusPublisher(String busName) {
        this.busName = busName;
    }

    @Override
    public void publish(Event event) {
        LOG.info("publish event,event:{},clients count:{}", event, clients == null ? 0 : clients.size());
        clients.forEach(client -> client.acceptEvent(event));
    }

    @Override
    public void subscribe(HotspotDataBusSubscriber subscriber) {
        synchronized (LockFactory.getSubAndUnSubLock()) {
            if (clients == null) {
                clients = new ArrayList<>();
            }
            clients.add(subscriber);
        }
    }

    @Override
    public void unSubscribe(HotspotDataBusSubscriber subscriber) {
        synchronized (LockFactory.getSubAndUnSubLock()) {
            if (clients == null) {
                clients = new ArrayList<>();
            }
            clients.remove(subscriber);
        }
    }
}