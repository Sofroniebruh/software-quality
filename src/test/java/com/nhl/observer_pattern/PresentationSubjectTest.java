package com.nhl.observer_pattern;

import com.nhl.Slide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PresentationSubjectTest
{

    private PresentationSubject subject;

    @Mock
    private PresentationObserver mockObserver;

    @Mock
    private Presentation mockPresentation;

    @Mock
    private Slide mockSlide;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
        subject = new PresentationSubjectImpl();
    }

    @Test
    void registerObserver_whenCalled_shouldAddObserverToList()
    {
        subject.registerObserver(mockObserver);

        List<PresentationObserver> observers = subject.getObservers();

        assertTrue(observers.contains(mockObserver));
    }

    @Test
    void registerObserver_whenObserverAlreadyRegistered_shouldNotAddDuplicate()
    {
        subject.registerObserver(mockObserver);
        subject.registerObserver(mockObserver);

        List<PresentationObserver> observers = subject.getObservers();

        assertEquals(1, observers.size());
        assertTrue(observers.contains(mockObserver));
    }

    @Test
    void unregisterObserver_whenCalled_shouldRemoveObserverFromList()
    {
        subject.registerObserver(mockObserver);
        subject.unregisterObserver(mockObserver);

        List<PresentationObserver> observers = subject.getObservers();

        assertFalse(observers.contains(mockObserver));
    }

    @Test
    void notifyObservers_whenCalled_shouldNotifyAllObservers()
    {
        subject.registerObserver(mockObserver);
        subject.notifyObservers(mockPresentation, mockSlide);

        List<PresentationObserver> observers = subject.getObservers();

        assertTrue(observers.contains(mockObserver));
    }

    @Test
    void getObservers_whenCalled_shouldReturnListOfObservers()
    {
        subject.registerObserver(mockObserver);

        List<PresentationObserver> observers = subject.getObservers();

        assertNotNull(observers);
        assertEquals(1, observers.size());
        assertTrue(observers.contains(mockObserver));
    }
} 