package com.nhl.command_pattern;

import com.nhl.Slide;
import com.nhl.observer_pattern.Presentation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.MockedStatic;

import javax.swing.JOptionPane;
import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NewCommandTest
{
    private NewCommand command;

    @Mock
    private Presentation mockPresentation;

    @Mock
    private Frame mockFrame;

    @Mock
    private FileDialog mockFileDialog;

    @Mock
    private File mockFile;

    @Mock
    private URI mockURI;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
        command = new NewCommand(mockPresentation, mockFrame);
    }

    @Test
    void execute_whenCreatingBlankPresentation_shouldClearAndSetNewPresentation()
    {
        try (MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class))
        {
            mockedJOptionPane.when(() ->
                    JOptionPane.showOptionDialog(
                            eq(mockFrame),
                            eq("Do you want to create a new blank presentation or open an existing one?"),
                            eq("New Presentation"),
                            eq(JOptionPane.YES_NO_OPTION),
                            eq(JOptionPane.QUESTION_MESSAGE),
                            isNull(),
                            eq(new String[] {"Blank", "Open File"}),
                            eq("Blank")
                    )
            ).thenReturn(0);

            boolean result = command.execute();

            assertTrue(result);
            verify(mockPresentation).clear();
            verify(mockPresentation).setTitle("New Presentation");
            verify(mockPresentation).append(any(Slide.class));
            verify(mockFrame).repaint();
        }
    }

    @Test
    void execute_whenUserCancels_shouldReturnFalse()
    {
        try (MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class))
        {
            mockedJOptionPane.when(() ->
                    JOptionPane.showOptionDialog(
                            any(), any(), any(), anyInt(), anyInt(), any(), any(), any()
                    )
            ).thenReturn(-1);

            boolean result = command.execute();

            assertFalse(result);
            verify(mockPresentation, never()).clear();
        }
    }

    @Test
    void undo_whenPreviousStateExists_shouldRestorePreviousState()
    {
        List<Slide> previousSlides = new ArrayList<>();
        previousSlides.add(new Slide());
        previousSlides.add(new Slide());
        String previousTitle = "Previous Title";

        try
        {
            java.lang.reflect.Field slidesField = NewCommand.class.getDeclaredField("previousSlides");
            slidesField.setAccessible(true);
            slidesField.set(command, previousSlides);

            java.lang.reflect.Field titleField = NewCommand.class.getDeclaredField("previousTitle");
            titleField.setAccessible(true);
            titleField.set(command, previousTitle);
        }
        catch (Exception e)
        {
            fail("Failed to set up test: " + e.getMessage());
        }

        boolean result = command.undo();

        assertTrue(result);
        verify(mockPresentation).clear();
        verify(mockPresentation).setTitle(previousTitle);
        verify(mockPresentation, times(2)).append(any(Slide.class));
        verify(mockFrame).repaint();
    }

    @Test
    void undo_whenNoPreviousState_shouldReturnFalse()
    {
        boolean result = command.undo();

        assertFalse(result);
        verify(mockPresentation, never()).clear();
    }

    @Test
    void getDescription_whenCalled_shouldReturnCorrectDescription()
    {
        assertEquals("New Presentation", command.getDescription());
    }
} 