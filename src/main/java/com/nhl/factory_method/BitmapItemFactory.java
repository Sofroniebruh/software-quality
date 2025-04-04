package com.nhl.factory_method;

import com.nhl.BitmapItem;
import com.nhl.SlideItem;

public class BitmapItemFactory extends SlideItemFactory
{
    @Override
    public SlideItem initializeItem(String text, int level)
    {
        BitmapItem bitmapItem =  new BitmapItem(level, text);

        if (bitmapItem.getBufferedImage() == null)
        {
            return null;
        }

        return bitmapItem;
    }
}
