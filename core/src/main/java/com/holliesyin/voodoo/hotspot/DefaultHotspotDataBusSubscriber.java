package com.holliesyin.voodoo.hotspot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by @author Hollies Yin on 2018-05-28.
 */
public class DefaultHotspotDataBusSubscriber implements HotspotDataBusSubscriber {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultHotspotDataBusSubscriber.class);
    private List<Filter> filters;
    private List<Event> events;
    private List<Handler> handlers;

    public DefaultHotspotDataBusSubscriber() {
    }

    public DefaultHotspotDataBusSubscriber(String id, Class eventType){
        addFilter(new EventTypeFilter(eventType));
        addFilter(new IdFilter(id));
    }

    @Override
    public void addFilter(Filter filter) {
        LOG.info("add filter,filter:{}",filter);
        if (filters == null) {
            filters = new ArrayList<>();
        }
        filters.add(filter);
    }

    @Override
    public void acceptEvent(Event event) {
        if (events == null) {
            events = new ArrayList<>();
        }
        for (Filter filter : filters) {
            if (!filter.filter(event)) {
                return;
            }
        }
        LOG.info("accept event,event:{}",event);
        events.add(event);
    }

    @Override
    public void addHandler(Handler handler) {
        LOG.info("add handler,handler:{}",handler);
        if (handlers == null) {
            handlers = new ArrayList<>();
        }
        handlers.add(handler);
    }

    @Override
    public void subscribe(HotspotDataBusPublisher publisher) {
        LOG.info("subscribe publisher.");
        publisher.subscribe(this);
    }

    @Override
    public void unSubscribe(HotspotDataBusPublisher publisher) {
        LOG.info("unSubscribe publisher.");
        publisher.unSubscribe(this);
    }

    @Override
    public void commit() {
        LOG.info("commit subscriber.");
        if(events==null||events.isEmpty()){
            return;
        }
        handlers.forEach(handler -> handler.handle(events));
        if (events != null) {
            events.clear();
        }
    }

    @Override
    public void cancel() {
        if (events != null) {
            events.clear();
        }
    }
}