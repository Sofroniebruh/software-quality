package com.nhl.factory_method;

import com.nhl.SlideItem;
import com.nhl.Style;
import com.nhl.TextItem;

import javax.swing.*;

public class TextItemFactory extends SlideItemFactory
{
    @Override
    public SlideItem initializeItem(String text, int level)
    {
        return new TextItem(level, text);
    }
}
