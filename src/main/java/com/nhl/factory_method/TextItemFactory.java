package com.nhl.factory_method;

import com.nhl.SlideItem;
import com.nhl.TextItem;

public class TextItemFactory extends SlideItemFactory
{
    @Override
    public SlideItem initializeItem(String text, int level)
    {
        return new TextItem(level, text);
    }
}
