package com.nhl.factory_method;

import com.nhl.BitmapItem;
import com.nhl.SlideItem;

public class BitmapItemFactory extends SlideItemFactory {

    @Override
    public SlideItem initializeItem() {
        return new BitmapItem();
    }
}
