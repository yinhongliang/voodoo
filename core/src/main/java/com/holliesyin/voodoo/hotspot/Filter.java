package com.holliesyin.voodoo.hotspot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by @author Hollies Yin on 2018-05-28.
 */
public interface Filter {
    Logger LOG = LoggerFactory.getLogger(Filter.class);
    boolean filter(Event event);
}