package com.nhl.factory_method;

import com.nhl.BitmapItem;
import com.nhl.SlideItem;
import com.nhl.TextItem;
import com.nhl.XMLAccessor;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class SlideItemFactoryTest
{
    @Test
    void createSlideItem_whenTypeIsText_shouldReturnTextItem()
    {
        SlideItem item = SlideItemFactory.createSlideItem(new XMLAccessor().getText(), "Test", 1);
        assertNotNull(item);
        assertInstanceOf(TextItem.class, item);
    }

    @Test
    void createSlideItem_whenTypeIsImageAndImageDoesNotExist_shouldReturnNullAndShowDialog()
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
    void createSlideItem_whenTypeIsImageAndImageExists_shouldReturnBitmapItem()
    {
        SlideItem item = SlideItemFactory.createSlideItem(new XMLAccessor().getImage(), "default-image.jpg", 1);
        assertNotNull(item);
        assertInstanceOf(BitmapItem.class, item);
    }

    @Test
    void createSlideItem_whenTypeIsUnknown_shouldReturnNull()
    {
        SlideItem item = SlideItemFactory.createSlideItem("qwerty", "Test", 1);
        assertNull(item);
    }
}