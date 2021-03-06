package com.holliesyin.voodoo.hotspot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by @author Hollies Yin on 2018/6/9.
 */
public class ItemResource {
    private final static Logger LOG = LoggerFactory.getLogger(ItemResource.class);

    public Item findById(String id) {
        LOG.info("find item in db,id:{}",id);
        return new Item(id,"");
    }

    public boolean updateItemExt(Item item) {
        LOG.info("update item ext success,item:{}",item);
        return true;
    }

    public boolean updateItemExt(Item item,String occLock){
        LOG.info("update item ext with occLock success,item:{},occLock:{}",item,occLock);
        return true;
    }
}