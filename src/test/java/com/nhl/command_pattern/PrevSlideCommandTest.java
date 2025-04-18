package com.nhl.command_pattern;

import com.nhl.observer_pattern.Presentation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PrevSlideCommandTest
{
    private PrevSlideCommand command;

    @Mock
    private Presentation mockPresentation;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
        command = new PrevSlideCommand(mockPresentation);
    }

    @Test
    void execute_whenCalled_shouldMoveToPreviousSlide()
    {
        when(mockPresentation.getSlideNumber()).thenReturn(1);

        boolean result = command.execute();

        assertTrue(result);
        verify(mockPresentation).prevSlide();
    }

    @Test
    void undo_whenCalled_shouldMoveToNextSlide()
    {
        when(mockPresentation.getSlideNumber()).thenReturn(0);

        command.execute();
        boolean result = command.undo();

        assertTrue(result);
        verify(mockPresentation).nextSlide();
    }

    @Test
    void getDescription_whenCalled_shouldReturnCorrectDescription()
    {
        assertEquals("Previous Slide", command.getDescription());
    }

    @Test
    void execute_whenAtFirstSlide_shouldStillExecute()
    {
        when(mockPresentation.getSlideNumber()).thenReturn(0);

        boolean result = command.execute();

        assertTrue(result);
        verify(mockPresentation).prevSlide();
    }

    @Test
    void getPresentation_whenCalled_shouldReturnCorrectPresentation()
    {
        assertEquals(mockPresentation, command.getPresentation());
    }

    @Test
    void setPresentation_whenCalled_shouldUpdatePresentation()
    {
        Presentation newPresentation = mock(Presentation.class);

        command.setPresentation(newPresentation);

        assertEquals(newPresentation, command.getPresentation());
    }
} 