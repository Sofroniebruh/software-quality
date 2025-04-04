package com.nhl.factory_method;

import com.nhl.Slide;
import com.nhl.XMLAccessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

class XMLAccessorTest
{
    private Slide slide;
    private XMLAccessor xmlAccessor;

    @BeforeEach
    void setup()
    {
        System.setProperty("java.awt.headless", "true");
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
    void slideItemAction_enteredCorrectIMAGETypeAndCorrectImageName_shouldAddBitMapItemAndNoDialog()
    {
        this.xmlAccessor.slideItemAction(this.slide, new XMLAccessor().getImage(), "default-image.jpg", 1);

        assertFalse(this.slide.getSlideItems().isEmpty());
    }

    @Test
    void slideItemAction_enteredCorrectIMAGETypeAndIncorrectImageName_shouldShowDialogAndNotAddItem() {
        try (MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class)) {
            this.xmlAccessor.slideItemAction(slide, new XMLAccessor().getImage(), "qwerty", 1);

            mockedJOptionPane.verify(() ->
                    JOptionPane.showMessageDialog(null, "Image was not found", "Image error", JOptionPane.ERROR_MESSAGE)
            );

            assertTrue(slide.getSlideItems().isEmpty());
        }
    }

    @Test
    void slideItemAction_enteredIncorrectTypeText_shouldNotAddItemAndShowDialog()
    {
        this.xmlAccessor.slideItemAction(this.slide, "qwerty", "default-image.jpg", 1);

        assertTrue(this.slide.getSlideItems().isEmpty());
    }
}