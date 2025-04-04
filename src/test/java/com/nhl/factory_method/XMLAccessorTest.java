package com.nhl.factory_method;

import com.nhl.Slide;
import com.nhl.XMLAccessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class XMLAccessorTest
{
    private Slide slide;
    private XMLAccessor xmlAccessor;

    @BeforeEach
    void setup()
    {
        this.slide = new Slide();
        this.xmlAccessor = new XMLAccessor();
    }

    @Test
    void slideItemAction_enteredCorrectTEXTTypeText_shouldAddTextItem()
    {
        this.xmlAccessor.slideItemAction(this.slide, new XMLAccessor().getText(), "Test", 1);

        assertFalse(this.slide.getSlideItems().isEmpty());
    }

    @Test
    void slideItemAction_enteredCorrectIMAGEType_shouldAddBitMapItem()
    {
        this.xmlAccessor.slideItemAction(this.slide, new XMLAccessor().getImage(), "Test", 1);

        assertFalse(this.slide.getSlideItems().isEmpty());
    }

    @Test
    void slideItemAction_enteredIncorrectTypeText_shouldNotAddItem()
    {
        this.xmlAccessor.slideItemAction(this.slide, "qwerty", "Test", 1);

        assertTrue(this.slide.getSlideItems().isEmpty());
    }
}