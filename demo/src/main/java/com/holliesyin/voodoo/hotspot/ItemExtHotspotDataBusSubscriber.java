package com.holliesyin.voodoo.hotspot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by @author Hollies Yin on 2018-05-29.
 */
public class ItemExtHotspotDataBusSubscriber extends DefaultHotspotDataBusSubscriber {

    private static final Logger LOG = LoggerFactory.getLogger(ItemExtHotspotDataBusSubscriber.class);

    public ItemExtHotspotDataBusSubscriber(ItemResource itemResource, String id, Class eventType) {
        super(id, eventType);
        addHandler(new AddItemExtPropertyHandler(itemResource, id,true));
        LOG.info("create subscriber success.id:{}", id);
    }
}