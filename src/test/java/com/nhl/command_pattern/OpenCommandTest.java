package com.nhl.command_pattern;

import com.nhl.Slide;
import com.nhl.observer_pattern.Presentation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.awt.*;
import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OpenCommandTest
{

    private Presentation presentation;
    private Frame parent;
    private OpenCommand openCommand;

    @BeforeEach
    void setUp()
    {
        presentation = mock(Presentation.class);
        parent = mock(Frame.class);
        openCommand = new OpenCommand(presentation, parent);
    }

    @Test
    void testStoreCurrentState_savesTitleAndSlides()
    {
        Slide slide1 = new Slide();
        Slide slide2 = new Slide();

        when(presentation.getTitle()).thenReturn("Original Title");
        when(presentation.getSize()).thenReturn(2);
        when(presentation.getSlide(0)).thenReturn(slide1);
        when(presentation.getSlide(1)).thenReturn(slide2);

        openCommand.storeCurrentState();
        boolean undoResult = openCommand.undo();

        assertTrue(undoResult);

        verify(presentation).clear();
        verify(presentation).setTitle("Original Title");
        verify(presentation).append(slide1);
        verify(presentation).append(slide2);
    }

    @Test
    void testUndo_returnsFalse_whenNoStateStored()
    {
        assertFalse(openCommand.undo());
    }

    @Test
    void testLoadPresentationFromXML_parsesSlidesAndItems() throws Exception
    {
        File file = new File("/Users/esnu/Desktop/Programming/Java/Software quality/software-quality/dump.xml");

        openCommand.loadPresentationFromXML(file);

        verify(presentation).clear();
        verify(presentation, atLeastOnce()).append(any(Slide.class));
        verify(parent, atLeastOnce()).repaint();
    }
}
