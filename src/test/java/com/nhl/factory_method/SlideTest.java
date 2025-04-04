package com.nhl.factory_method;

import com.nhl.Slide;
import com.nhl.SlideItem;
import com.nhl.XMLAccessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

class SlideTest
{
    private Slide slide;

    @BeforeEach
    void setUp()
    {
        slide = mock(Slide.class);
    }

    @Test
    void slideItemAction_shouldNotAppendSlideItem_whenTypeIsValidButImageDoesNotExist()
    {
        try (MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class))
        {
            SlideItem item = SlideItemFactory.createSlideItem(new XMLAccessor().getImage(), "Test", 1);

            mockedJOptionPane.verify(() ->
                    JOptionPane.showMessageDialog(null, "Image was not found", "Image error", JOptionPane.ERROR_MESSAGE)
            );

            assertNull(item);
        }
    }

    @Test
    void slideItemAction_shouldAppendSlideItem_whenTypeIsValidAndImageDoesNotExist()
    {
        SlideItem item = SlideItemFactory.createSlideItem(new XMLAccessor().getImage(), "default-image.jpg", 1);

        assertNotNull(item);
        slide.append(item);

        verify(slide, times(1)).append(item);
    }

    @Test
    void slideItemAction_shouldNotAppendSlideItem_whenTypeIsInvalid()
    {
        SlideItem item = SlideItemFactory.createSlideItem("qwerty", "Test", 1);

        assertNull(item);

        verify(slide, never()).append(any(SlideItem.class));
    }
}
