package com.nhl.command_pattern;

import com.nhl.Slide;
import com.nhl.observer_pattern.Presentation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddNewSlideCommandTest
{

    private AddNewSlideCommand command;

    @Mock
    private Presentation mockPresentation;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
        command = new AddNewSlideCommand(mockPresentation);
    }

    @Test
    void execute_whenCalled_shouldAddNewSlide()
    {
        when(mockPresentation.getSize()).thenReturn(0);

        boolean result = command.execute();

        assertTrue(result);
        verify(mockPresentation).append(any(Slide.class));
    }

    @Test
    void undo_whenCalled_shouldRemoveAddedSlide()
    {
        when(mockPresentation.getSize()).thenReturn(1);
        when(mockPresentation.removeSlide(anyInt())).thenReturn(true);

        command.execute();
        boolean result = command.undo();

        assertTrue(result);
        verify(mockPresentation).removeSlide(0);
    }

    @Test
    void getDescription_whenCalled_shouldReturnCorrectDescription()
    {
        assertEquals("Add New Slide", command.getDescription());
    }

    @Test
    void undo_whenRemoveFails_shouldReturnFalse()
    {
        when(mockPresentation.getSize()).thenReturn(1);
        when(mockPresentation.removeSlide(anyInt())).thenReturn(false);

        command.execute();
        boolean result = command.undo();

        assertFalse(result);
    }
} 