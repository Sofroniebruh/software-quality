package com.nhl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SlideTest
{

    private Slide slide;

    @BeforeEach
    void setUp()
    {
        slide = new Slide();
    }

    @Test
    void constructor_whenCalled_shouldInitializeEmptySlide()
    {
        assertNotNull(slide);
        assertEquals(null, slide.getTitle());
        assertTrue(slide.getSlideItems().isEmpty());
    }

    @Test
    void setTitle_whenCalled_shouldUpdateTitle()
    {
        String title = "Test Title";

        slide.setTitle(title);
        assertEquals(title, slide.getTitle());
    }

    @Test
    void appendItem_whenCalled_shouldAddTextItem()
    {
        String itemText = "Test Item";

        slide.append(1, itemText);
        assertEquals(1, slide.getSlideItems().size());

        SlideItem addedItem = slide.getSlideItem(0);

        assertNotNull(addedItem);
        assertTrue(addedItem instanceof TextItem);
        assertEquals(itemText, ((TextItem) addedItem).getText());
    }

    @Test
    void appendMultipleItems_whenCalled_shouldAddAllItems()
    {
        String[] items = {"Item 1", "Item 2", "Item 3"};

        for (String item : items)
        {
            slide.append(1, item);
        }

        assertEquals(items.length, slide.getSlideItems().size());

        for (int i = 0; i < items.length; i++)
        {
            SlideItem slideItem = slide.getSlideItem(i);

            assertNotNull(slideItem);
            assertTrue(slideItem instanceof TextItem);
            assertEquals(items[i], ((TextItem) slideItem).getText());
        }
    }

    @Test
    void clearItems_whenCalled_shouldRemoveAllItems()
    {
        slide.append(1, "Item 1");
        slide.append(1, "Item 2");
        slide.getSlideItems().clear();

        assertTrue(slide.getSlideItems().isEmpty());
    }

    @Test
    void getItems_whenCalled_shouldReturnAllItems()
    {
        String[] items = {"Item 1", "Item 2", "Item 3"};

        for (String item : items)
        {
            slide.append(1, item);
        }

        Vector<SlideItem> slideItems = slide.getSlideItems();

        assertNotNull(slideItems);
        assertEquals(items.length, slideItems.size());

        for (int i = 0; i < items.length; i++)
        {
            SlideItem slideItem = slideItems.get(i);

            assertNotNull(slideItem);
            assertInstanceOf(TextItem.class, slideItem);
            assertEquals(items[i], ((TextItem) slideItem).getText());
        }
    }

    @Test
    void removeItem_whenValidIndexProvided_shouldRemoveItem()
    {
        slide.append(1, "Item 1");
        slide.append(1, "Item 2");
        slide.append(1, "Item 3");

        assertTrue(slide.removeItem(1));
        assertEquals(2, slide.getSlideItems().size());

        assertEquals("Item 1", ((TextItem) slide.getSlideItem(0)).getText());
        assertEquals("Item 3", ((TextItem) slide.getSlideItem(1)).getText());
    }

    @Test
    void removeItem_whenInvalidIndexProvided_shouldReturnFalse()
    {
        assertFalse(slide.removeItem(-1));
        assertFalse(slide.removeItem(0));

        slide.append(1, "Item 1");
        assertFalse(slide.removeItem(1));
    }
} 