package com.nhl.factory_method;

import com.nhl.Slide;
import com.nhl.SlideItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

class SlideTest {
    private Slide slide;

    @BeforeEach
    void setUp() {
        slide = mock(Slide.class);
    }

    @Test
    void slideItemAction_shouldAppendSlideItem_whenTypeIsValid() {
        SlideItem item = SlideItemFactory.createSlideItem(XMLAccessor.IMAGE);

        assertNotNull(item);
        slide.append(item);

        verify(slide, times(1)).append(item);
    }

    @Test
    void slideItemAction_shouldNotAppendSlideItem_whenTypeIsInvalid() {
        SlideItem item = SlideItemFactory.createSlideItem("qwerty");

        assertNull(item);

        verify(slide, never()).append(any(SlideItem.class));
    }
}
