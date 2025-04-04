package com.nhl.factory_method;

import com.nhl.Slide;
import com.nhl.SlideItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SlideIntegrationTest
{
    @Test
    void slideItemAction_shouldWorkCorrectly()
    {
        Slide slide = new Slide();
        SlideItem slideItem = SlideItemFactory.createSlideItem(XMLAccessor.TEXT);

        assertNotNull(slideItem);
        slide.append(slideItem);

        assertEquals(1, slide.getSlideItems().size());
    }
}
