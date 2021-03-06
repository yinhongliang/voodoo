package com.holliesyin.voodoo.hotspot;

/**
 * Created by @author Hollies Yin on 2018/6/9.
 */
public class HotspotCase {
    private ItemResource itemResource = new ItemResource();
    private String id;
    private HotspotDataBusPublisher publisher = HotspotDataBusPublisherFactory.getInstance("ADD_ITEM_EXT_BUS");
    private HotspotDataBusSubscriber subscriber = null;

    public HotspotCase(String id) {
        this.id = id;
        subscriber = new ItemExtHotspotDataBusSubscriber(itemResource, id, AddItemExtPropertyEvent.class);
        subscriber.subscribe(publisher);
    }

    public void publishEvents() {
        doSomethingTimeConsuming();
        publisher.publish(new AddItemExtPropertyEvent(id, "key1", "value1"));
        doSomethingTimeConsuming();
        publisher.publish(new AddItemExtPropertyEvent(id, "key2", "value2"));
        doSomethingTimeConsuming();
        publisher.publish(new AddItemExtPropertyEvent(id, "key1", "value3"));
    }

    public void commit() {
        subscriber.commit();
    }

    private void doSomethingTimeConsuming() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
    }
}