package com.nhl.observer_pattern;

import com.nhl.Slide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PresentationTest
{
    private Presentation presentation;

    @Mock
    private IUIInteraction mockUIInteraction;

    @Mock
    private IErrorHandler mockErrorHandler;

    @Mock
    private PresentationSubject mockSubject;

    @Mock
    private PresentationObserver mockObserver;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
        presentation = new Presentation(mockUIInteraction);
    }

    @Test
    void constructor_whenCalledWithUIInteraction_shouldInitializeEmptyPresentation()
    {
        assertNotNull(presentation);
        assertEquals(0, presentation.getSize());
        assertEquals(0, presentation.getSlideNumber());
    }

    @Test
    void getTitle_whenTitleIsSet_shouldReturnCorrectTitle()
    {
        presentation.setTitle("Test Title");

        assertEquals("Test Title", presentation.getTitle());
    }

    @Test
    void setTitle_whenCalled_shouldUpdateTitle()
    {
        presentation.setTitle("New Title");

        assertEquals("New Title", presentation.getTitle());
    }

    @Test
    void getSize_whenNoSlidesAdded_shouldReturnZero()
    {
        assertEquals(0, presentation.getSize());

        Slide slide = new Slide();
        presentation.append(slide);

        assertEquals(1, presentation.getSize());
    }

    @Test
    void getSlide_whenValidIndexProvided_shouldReturnCorrectSlide()
    {
        Slide slide = new Slide();
        slide.setTitle("Test Slide");
        presentation.append(slide);

        Slide retrievedSlide = presentation.getSlide(0);

        assertNotNull(retrievedSlide);
        assertEquals("Test Slide", retrievedSlide.getTitle());

        assertNull(presentation.getSlide(-1));
        assertNull(presentation.getSlide(1));
    }

    @Test
    void getCurrentSlide_whenNoSlidesAdded_shouldReturnNull()
    {
        assertNull(presentation.getCurrentSlide());

        Slide slide = new Slide();

        slide.setTitle("Test Slide");
        presentation.append(slide);

        Slide currentSlide = presentation.getCurrentSlide();

        assertNotNull(currentSlide);
        assertEquals("Test Slide", currentSlide.getTitle());
    }

    @Test
    void append_whenCalled_shouldAddSlideAndSetCurrentSlide()
    {
        Slide slide = new Slide();
        slide.setTitle("Test Slide");

        presentation.append(slide);

        assertEquals(1, presentation.getSize());
        assertEquals(0, presentation.getSlideNumber());
    }

    @Test
    void clear_whenCalled_shouldRemoveAllSlides()
    {
        Slide slide1 = new Slide();
        Slide slide2 = new Slide();

        presentation.append(slide1);
        presentation.append(slide2);
        presentation.setSlideNumber(1);
        presentation.clear();

        assertEquals(0, presentation.getSize());
        assertEquals(0, presentation.getSlideNumber());
    }

    @Test
    void removeSlide_whenValidIndexProvided_shouldRemoveSlide()
    {
        Slide slide1 = new Slide();
        Slide slide2 = new Slide();

        presentation.append(slide1);
        presentation.append(slide2);

        assertTrue(presentation.removeSlide(0));
        assertEquals(1, presentation.getSize());
        assertFalse(presentation.removeSlide(-1));
        assertFalse(presentation.removeSlide(1));
    }

    @Test
    void setSlideNumber_whenValidNumberProvided_shouldUpdateCurrentSlide()
    {
        presentation.setSlideNumber(0);
        assertEquals(0, presentation.getSlideNumber());

        Slide slide = new Slide();

        presentation.append(slide);
        presentation.setSlideNumber(0);
        assertEquals(0, presentation.getSlideNumber());

        presentation.setSlideNumber(-1);
        assertEquals(0, presentation.getSlideNumber());

        presentation.setSlideNumber(1);
        assertEquals(0, presentation.getSlideNumber());
    }

    @Test
    void nextSlide_whenCalled_shouldMoveToNextSlide()
    {
        presentation.nextSlide();
        assertEquals(0, presentation.getSlideNumber());

        Slide slide = new Slide();

        presentation.append(slide);
        presentation.nextSlide();
        assertEquals(0, presentation.getSlideNumber());

        Slide slide2 = new Slide();

        presentation.append(slide2);
        presentation.nextSlide();
        assertEquals(1, presentation.getSlideNumber());
    }

    @Test
    void prevSlide_whenCalled_shouldMoveToPreviousSlide()
    {
        presentation.prevSlide();
        assertEquals(0, presentation.getSlideNumber());

        Slide slide = new Slide();

        presentation.append(slide);
        presentation.prevSlide();
        assertEquals(0, presentation.getSlideNumber());

        Slide slide2 = new Slide();

        presentation.append(slide2);
        presentation.setSlideNumber(1);
        presentation.prevSlide();
        assertEquals(0, presentation.getSlideNumber());
    }

    @Test
    void registerAndUnregisterObserver_whenCalled_shouldUpdateObserversList()
    {
        presentation.registerObserver(mockObserver);
        List<PresentationObserver> observers = presentation.getObservers();
        assertTrue(observers.contains(mockObserver));

        presentation.unregisterObserver(mockObserver);
        observers = presentation.getObservers();
        assertFalse(observers.contains(mockObserver));
    }
} 