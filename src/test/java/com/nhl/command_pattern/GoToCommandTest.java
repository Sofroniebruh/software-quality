package com.nhl.command_pattern;

import com.nhl.observer_pattern.Presentation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.MockedStatic;

import javax.swing.JOptionPane;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GoToCommandTest
{
    private GoToCommand command;

    @Mock
    private Presentation mockPresentation;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
        command = new GoToCommand(mockPresentation);
    }

    @Test
    void execute_whenValidPageNumberProvided_shouldNavigateToPage()
    {
        when(mockPresentation.getSlideNumber()).thenReturn(0);

        try (MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class))
        {
            mockedJOptionPane.when(() -> JOptionPane.showInputDialog(GoToCommand.PAGENR)).thenReturn("2");

            boolean result = command.execute();

            assertTrue(result);
            verify(mockPresentation).setSlideNumber(1);
        }
    }

    @Test
    void undo_whenCalled_shouldReturnToPreviousPage()
    {
        when(mockPresentation.getSlideNumber()).thenReturn(0);

        try (MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class))
        {
            mockedJOptionPane.when(() -> JOptionPane.showInputDialog(GoToCommand.PAGENR)).thenReturn("2");

            command.execute();
            boolean result = command.undo();

            assertTrue(result);
            verify(mockPresentation).setSlideNumber(0);
        }
    }

    @Test
    void getDescription_whenCalled_shouldReturnCorrectDescription()
    {
        assertEquals("Go to Slide", command.getDescription());
    }

    @Test
    void execute_whenInvalidInputProvided_shouldNotNavigateAndReturnFalse()
    {
        when(mockPresentation.getSlideNumber()).thenReturn(0);

        try (MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class))
        {
            mockedJOptionPane.when(() -> JOptionPane.showInputDialog(GoToCommand.PAGENR)).thenReturn("invalid");
            mockedJOptionPane.when(() -> JOptionPane.showConfirmDialog(any(), anyString(), anyString(), anyInt()))
                    .thenReturn(JOptionPane.NO_OPTION);

            boolean result = command.execute();

            assertFalse(result);
            verify(mockPresentation, never()).setSlideNumber(anyInt());
        }
    }

    @Test
    void execute_whenInvalidInputProvidedAndRetrySelected_shouldNavigateToSpecifiedSlide()
    {
        when(mockPresentation.getSlideNumber()).thenReturn(0);

        try (MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class))
        {
            mockedJOptionPane.when(() -> JOptionPane.showInputDialog(GoToCommand.PAGENR))
                    .thenReturn("invalid")
                    .thenReturn("2");
            mockedJOptionPane.when(() -> JOptionPane.showConfirmDialog(any(), anyString(), anyString(), anyInt()))
                    .thenReturn(JOptionPane.YES_OPTION);

            boolean result = command.execute();

            assertTrue(result);
            verify(mockPresentation).setSlideNumber(1);
        }
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