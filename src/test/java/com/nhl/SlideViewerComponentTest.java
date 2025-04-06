package com.nhl;

import com.nhl.observer_pattern.Presentation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SlideViewerComponentTest
{
    private SlideViewerComponent slideViewer;

    @Mock
    private Slide mockSlide;

    @Mock
    private JFrame mockFrame;

    @Mock
    private Presentation mockPresentation;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
        slideViewer = new SlideViewerComponent(mockFrame);
    }

    @Test
    void constructor_whenCalled_shouldCreateValidSlideViewerComponent()
    {
        assertNotNull(slideViewer);
        assertNotNull(slideViewer.getScrollPane());
    }

    @Test
    void getScrollPane_whenCalled_shouldReturnValidJScrollPane()
    {
        JScrollPane scrollPane = slideViewer.getScrollPane();

        assertNotNull(scrollPane);
        assertInstanceOf(JScrollPane.class, scrollPane);
    }

    @Test
    void getPreferredSize_whenCalled_shouldReturnValidDimension()
    {
        Dimension size = slideViewer.getPreferredSize();

        assertNotNull(size);
        assertTrue(size.width > 0);
        assertTrue(size.height > 0);
    }

    @Test
    void update_whenCalledWithPresentationAndSlide_shouldUpdateFrameTitle()
    {
        when(mockSlide.getTitle()).thenReturn("Test Title");
        Vector<SlideItem> items = new Vector<>();

        when(mockSlide.getSlideItems()).thenReturn(items);
        when(mockPresentation.getTitle()).thenReturn("Presentation Title");
        slideViewer.update(mockPresentation, mockSlide);

        verify(mockFrame).setTitle("Presentation Title");
    }

    @Test
    void paintComponent_whenCalled_shouldSetColorAndFillRect()
    {
        Graphics g = mock(Graphics.class);

        slideViewer.paintComponent(g);

        verify(g, atLeastOnce()).setColor(any(Color.class));
        verify(g, atLeastOnce()).fillRect(anyInt(), anyInt(), anyInt(), anyInt());
    }

    @Test
    void paintComponent_whenCalledWithPresentation_shouldDrawSlideContent()
    {
        when(mockSlide.getTitle()).thenReturn("Test Title");

        Vector<SlideItem> items = new Vector<>();

        when(mockSlide.getSlideItems()).thenReturn(items);
        when(mockPresentation.getTitle()).thenReturn("Presentation Title");
        when(mockPresentation.getSlideNumber()).thenReturn(0);
        when(mockPresentation.getSize()).thenReturn(1);
        slideViewer.update(mockPresentation, mockSlide);

        Graphics g = mock(Graphics.class);

        slideViewer.paintComponent(g);

        verify(g, atLeastOnce()).setColor(any(Color.class));
        verify(g, atLeastOnce()).setFont(any(Font.class));
        verify(g, atLeastOnce()).drawString(anyString(), anyInt(), anyInt());
    }
} 