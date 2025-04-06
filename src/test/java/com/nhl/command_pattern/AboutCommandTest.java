package com.nhl.command_pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.MockedStatic;

import javax.swing.JOptionPane;
import java.awt.Frame;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AboutCommandTest
{
    private AboutCommand command;

    @Mock
    private Frame mockFrame;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
        command = new AboutCommand(mockFrame);
    }

    @Test
    void execute_whenCalled_shouldShowAboutDialog()
    {
        try (MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class))
        {
            boolean result = command.execute();

            assertTrue(result);
            mockedJOptionPane.verify(() ->
                    JOptionPane.showMessageDialog(
                            eq(mockFrame),
                            contains("JabberPoint is a primitive slide-show program"),
                            eq("About JabberPoint"),
                            eq(JOptionPane.INFORMATION_MESSAGE)
                    )
            );
        }
    }

    @Test
    void undo_whenCalled_shouldReturnFalse()
    {
        assertFalse(command.undo());
    }

    @Test
    void getDescription_whenCalled_shouldReturnCorrectDescription()
    {
        assertEquals("About JabberPoint", command.getDescription());
    }
} 