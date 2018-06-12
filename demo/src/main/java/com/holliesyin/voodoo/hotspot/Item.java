package com.holliesyin.voodoo.hotspot;

/**
 * Created by @author Hollies Yin 2018/6/9.
 */
public class Item {
    private String id;
    private String ext;

    public Item() {
    }

    public Item(String id, String ext) {
        this.id = id;
        this.ext = ext;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", ext='" + ext + '\'' +
                '}';
    }
}
