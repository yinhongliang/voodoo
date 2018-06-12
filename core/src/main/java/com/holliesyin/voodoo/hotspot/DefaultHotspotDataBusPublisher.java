package com.holliesyin.voodoo.hotspot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by @author Hollies Yin on 2018-05-29.
 */
public class DefaultHotspotDataBusPublisher implements HotspotDataBusPublisher {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultHotspotDataBusPublisher.class);
    private List<HotspotDataBusSubscriber> clients;
    private ReentrantLock lock = new ReentrantLock(true);

    @Override
    public void publish(Event event) {
        LOG.info("publish event,event:{},clients:{}", event, clients == null ? 0 : clients.size());
        clients.forEach(client -> client.acceptEvent(event));
    }

    @Override
    public void subscribe(HotspotDataBusSubscriber subscriber) {
        LOG.info("subscribe subscriber to publisher.");
        lock.lock();
        if (clients == null) {
            clients = new ArrayList<>();
        }
        clients.add(subscriber);
        lock.unlock();
    }

    @Override
    public void unSubscribe(HotspotDataBusSubscriber subscriber) {
        LOG.info("unSubscribe subscriber to publisher.");
        lock.lock();
        if (clients == null) {
            clients = new ArrayList<>();
        }
        clients.remove(subscriber);
        lock.unlock();
    }
}