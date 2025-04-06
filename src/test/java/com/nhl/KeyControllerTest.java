package com.nhl;

import com.nhl.command_pattern.ExitCommand;
import com.nhl.command_pattern.NextSlideCommand;
import com.nhl.command_pattern.PrevSlideCommand;
import com.nhl.observer_pattern.Presentation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.event.KeyEvent;

import static org.mockito.Mockito.*;

class KeyControllerTest
{
    private KeyController keyController;

    @Mock
    private Presentation mockPresentation;

    @Mock
    private KeyEvent mockKeyEvent;

    @Mock
    private NextSlideCommand mockNextSlideCommand;

    @Mock
    private PrevSlideCommand mockPrevSlideCommand;

    @Mock
    private ExitCommand mockExitCommand;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
        keyController = new KeyController(mockPresentation);

        keyController.setNextSlideCommand(mockNextSlideCommand);
        keyController.setPrevSlideCommand(mockPrevSlideCommand);
        keyController.setExitCommand(mockExitCommand);
    }

    @Test
    void keyPressed_whenPageDownPressed_shouldExecuteNextSlideCommand()
    {
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_PAGE_DOWN);

        keyController.keyPressed(mockKeyEvent);

        verify(mockNextSlideCommand).execute();
    }

    @Test
    void keyPressed_whenPageUpPressed_shouldExecutePrevSlideCommand()
    {
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_PAGE_UP);

        keyController.keyPressed(mockKeyEvent);

        verify(mockPrevSlideCommand).execute();
    }

    @Test
    void keyPressed_whenDownArrowPressed_shouldExecuteNextSlideCommand()
    {
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_DOWN);

        keyController.keyPressed(mockKeyEvent);

        verify(mockNextSlideCommand).execute();
    }

    @Test
    void keyPressed_whenUpArrowPressed_shouldExecutePrevSlideCommand()
    {
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_UP);

        keyController.keyPressed(mockKeyEvent);

        verify(mockPrevSlideCommand).execute();
    }

    @Test
    void keyPressed_whenEnterPressed_shouldExecuteNextSlideCommand()
    {
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_ENTER);

        keyController.keyPressed(mockKeyEvent);

        verify(mockNextSlideCommand).execute();
    }

    @Test
    void keyPressed_whenPlusPressed_shouldExecuteNextSlideCommand()
    {
        when(mockKeyEvent.getKeyCode()).thenReturn((int) '+');

        keyController.keyPressed(mockKeyEvent);

        verify(mockNextSlideCommand).execute();
    }

    @Test
    void keyPressed_whenMinusPressed_shouldExecutePrevSlideCommand()
    {
        when(mockKeyEvent.getKeyCode()).thenReturn((int) '-');

        keyController.keyPressed(mockKeyEvent);

        verify(mockPrevSlideCommand).execute();
    }

    @Test
    void keyPressed_whenQPressed_shouldExecuteExitCommand()
    {
        when(mockKeyEvent.getKeyCode()).thenReturn((int) 'q');

        keyController.keyPressed(mockKeyEvent);

        verify(mockExitCommand).execute();
    }

    @Test
    void keyPressed_whenCapitalQPressed_shouldExecuteExitCommand()
    {
        when(mockKeyEvent.getKeyCode()).thenReturn((int) 'Q');

        keyController.keyPressed(mockKeyEvent);

        verify(mockExitCommand).execute();
    }

    @Test
    void keyPressed_whenOtherKeyPressed_shouldNotExecuteAnyCommand()
    {
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_A);

        keyController.keyPressed(mockKeyEvent);

        verify(mockNextSlideCommand, never()).execute();
        verify(mockPrevSlideCommand, never()).execute();
        verify(mockExitCommand, never()).execute();
    }

    @Test
    void keyReleased_whenCalled_shouldNotExecuteAnyCommand()
    {
        keyController.keyReleased(mockKeyEvent);

        verify(mockNextSlideCommand, never()).execute();
        verify(mockPrevSlideCommand, never()).execute();
        verify(mockExitCommand, never()).execute();
    }

    @Test
    void keyTyped_whenCalled_shouldNotExecuteAnyCommand()
    {
        keyController.keyTyped(mockKeyEvent);

        verify(mockNextSlideCommand, never()).execute();
        verify(mockPrevSlideCommand, never()).execute();
        verify(mockExitCommand, never()).execute();
    }
} 