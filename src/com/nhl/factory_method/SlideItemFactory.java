package com.nhl.factory_method;

import com.nhl.SlideItem;

import java.util.HashMap;
import java.util.Map;

public abstract class SlideItemFactory {
    private static final Map<String, SlideItemFactory> records = new HashMap<>();

    private static void initializeRecords() {
        if (records.isEmpty()) {
            records.put(XMLAccessor.TEXT, new TextItemFactory());
            records.put(XMLAccessor.IMAGE, new BitmapItemFactory());
        }
    }

    public abstract SlideItem initializeItem();

    public static SlideItem createSlideItem(String type) {
        initializeRecords();
        SlideItemFactory factory = records.get(type);

        if (factory != null) {
            return factory.initializeItem();
        }

        return null;
    }
}
