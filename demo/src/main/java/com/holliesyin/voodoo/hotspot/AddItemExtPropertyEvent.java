package com.holliesyin.voodoo.hotspot;

/**
 * Created by @author Hollies Yin on 2018-05-28.
 */
public class AddItemExtPropertyEvent implements Event {
    private String id;
    private String key;
    private String value;

    public AddItemExtPropertyEvent(String id,String key, String value) {
        this.id = id;
        this.key = key;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "AddItemExtPropertyEvent{" +
                "id='" + id + '\'' +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}