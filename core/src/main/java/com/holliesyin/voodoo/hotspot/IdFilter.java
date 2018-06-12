package com.holliesyin.voodoo.hotspot;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by @author Hollies Yin on 2018-05-29.
 */
public class IdFilter implements Filter {

    private String id;

    public IdFilter(String id) {
        this.id = id;
    }

    @Override
    public boolean filter(Event event) {
        return StringUtils.equals(id,  event.getId());
    }
}