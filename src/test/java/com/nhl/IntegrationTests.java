package com.nhl;

import com.nhl.observer_pattern.Presentation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class IntegrationTests
{
    private Presentation presentation;
    private KeyController keyController;

    @BeforeEach
    public void setUp()
    {
        System.setProperty("java.awt.headless", "true");

        presentation = new Presentation();
        Slide slide1 = new Slide();
        slide1.setTitle("Slide 1");
        Slide slide2 = new Slide();
        slide2.setTitle("Slide 2");
        presentation.append(slide1);
        presentation.append(slide2);
        presentation.setSlideNumber(0);
        keyController = new KeyController(presentation);
    }

    @Test
    public void keyController_whenEnterPressed_shouldMoveToNextSlide()
    {
        java.awt.Button mockButton = mock(java.awt.Button.class);

        KeyEvent enterEvent = new KeyEvent(mockButton, KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, '\n');
        keyController.keyPressed(enterEvent);
        assertEquals(1, presentation.getSlideNumber());
    }

    @Test
    public void keyController_whenUpArrowPressed_shouldMoveToPreviousSlide()
    {
        java.awt.Button mockButton = mock(java.awt.Button.class);

        presentation.setSlideNumber(1);
        KeyEvent upEvent = new KeyEvent(mockButton, KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(), 0, KeyEvent.VK_UP, ' ');
        keyController.keyPressed(upEvent);
        assertEquals(0, presentation.getSlideNumber());
    }
}
