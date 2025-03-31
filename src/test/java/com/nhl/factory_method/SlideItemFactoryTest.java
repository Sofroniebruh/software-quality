package com.nhl.factory_method;

import com.nhl.BitmapItem;
import com.nhl.SlideItem;
import com.nhl.TextItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SlideItemFactoryTest {

    @Test
    void createSlideItem_shouldReturnTextItem_whenTypeIsText() {
        SlideItem item = SlideItemFactory.createSlideItem("text");
        assertNotNull(item);
        assertInstanceOf(TextItem.class, item);
    }

    @Test
    void createSlideItem_shouldReturnBitmapItem_whenTypeIsImage() {
        SlideItem item = SlideItemFactory.createSlideItem("image");
        assertNotNull(item);
        assertInstanceOf(BitmapItem.class, item);
    }

    @Test
    void createSlideItem_shouldReturnNull_whenTypeIsUnknown() {
        SlideItem item = SlideItemFactory.createSlideItem("unknown");
        assertNull(item);
    }
}