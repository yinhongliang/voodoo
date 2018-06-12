package com.holliesyin.voodoo.hotspot;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by @author Hollies Yin on 2018-05-28.
 */
public class AddItemExtPropertyHandler extends DefaultHotspotDataBusHandler {

    private ItemResource itemResource;
    private String id;
    private boolean useOCC;

    private Item item;
    private Object occLock;

    public AddItemExtPropertyHandler(ItemResource itemResource, String id, boolean useOCC) {
        super();
        this.itemResource = itemResource;
        this.id = id;
        this.useOCC = useOCC;
    }

    @Override
    public void initContext() {
        item = itemResource.getItem(id);
        occLock = item.getExt();
    }

    @Override
    public void doHandle(List<Event> events) {
        events.forEach(event -> handleEventInItemExt((AddItemExtPropertyEvent) event, item));
    }

    @Override
    public void postHandle() {
        if (!useOCC) {
            itemResource.updateItemExt(item);
            LOG.info("add new ext property success,item:{}", item);
            return;
        }

        while (!itemResource.updateItemExt(item, (String) occLock)) {
        }

        LOG.info("add new ext property with lock success,item:{}", item);

    }

    private void handleEventInItemExt(AddItemExtPropertyEvent event, Item item) {
        LOG.info("try to add ext property,event:{},item:{}", event, item);
        String newExt = item.getExt();
        Gson gson = new Gson();
        Map map = gson.fromJson(newExt, Map.class);
        if (map == null) {
            map = new HashMap<>();
        }
        map.put(event.getKey(), event.getValue());
        newExt = gson.toJson(map);
        item.setExt(newExt);
    }
}