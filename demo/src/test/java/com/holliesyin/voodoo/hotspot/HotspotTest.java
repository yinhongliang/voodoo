package com.holliesyin.voodoo.hotspot;

/**
 * Created by @author Hollies Yin on 2018/6/9.
 */
public class HotspotTest {
    public static void main(String[] args) {
        HotspotCase hotspotCase0 = new HotspotCase("0");
        hotspotCase0.publishEvents();

//        HotspotCase hotspotCase1 = new HotspotCase("1");
//        hotspotCase1.publishEvents();
//        hotspotCase1.commit();

        hotspotCase0.commit();
    }
}