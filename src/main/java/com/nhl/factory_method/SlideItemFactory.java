package com.nhl.factory_method;

import com.nhl.SlideItem;
import com.nhl.XMLAccessor;

import java.util.HashMap;
import java.util.Map;

public abstract class SlideItemFactory
{
    private static final Map<String, SlideItemFactory> records = new HashMap<>();
    private static XMLAccessor accessor = new XMLAccessor();

    private static void initializeRecords()
    {
        if (records.isEmpty())
        {
            records.put(accessor.getText(), new TextItemFactory());
            records.put(accessor.getImage(), new BitmapItemFactory());
        }
    }

    public abstract SlideItem initializeItem(String text, int level);

    public static SlideItem createSlideItem(String type, String text, int level)
    {
        initializeRecords();
        SlideItemFactory factory = records.get(type);

        if (factory != null)
        {
            return factory.initializeItem(text, level);
        }

        return null;
    }
}
