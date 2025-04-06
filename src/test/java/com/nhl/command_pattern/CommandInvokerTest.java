package com.nhl.command_pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommandInvokerTest
{

    private CommandInvoker commandInvoker;

    @Mock
    private Command mockCommand;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
        commandInvoker = new CommandInvoker();
    }

    @Test
    void executeCommand_whenCommandExecutesSuccessfully_shouldUpdateUndoRedoState()
    {
        when(mockCommand.execute()).thenReturn(true);

        commandInvoker.executeCommand(mockCommand);

        verify(mockCommand).execute();
        assertTrue(commandInvoker.canUndo());
        assertFalse(commandInvoker.canRedo());
    }

    @Test
    void undo_whenCommandWasExecuted_shouldRevertCommandAndUpdateUndoRedoState()
    {
        when(mockCommand.execute()).thenReturn(true);
        when(mockCommand.undo()).thenReturn(true);

        commandInvoker.executeCommand(mockCommand);
        commandInvoker.undo();

        verify(mockCommand).undo();
        assertFalse(commandInvoker.canUndo());
        assertTrue(commandInvoker.canRedo());
    }

    @Test
    void redo_whenCommandWasUndone_shouldReExecuteCommandAndUpdateUndoRedoState()
    {
        when(mockCommand.execute()).thenReturn(true);
        when(mockCommand.undo()).thenReturn(true);

        commandInvoker.executeCommand(mockCommand);
        commandInvoker.undo();
        commandInvoker.redo();

        verify(mockCommand, times(2)).execute();
        assertTrue(commandInvoker.canUndo());
        assertFalse(commandInvoker.canRedo());
    }

    @Test
    void canUndo_whenNoCommandExecuted_shouldReturnFalse()
    {
        assertFalse(commandInvoker.canUndo());

        when(mockCommand.execute()).thenReturn(true);
        commandInvoker.executeCommand(mockCommand);

        assertTrue(commandInvoker.canUndo());
    }

    @Test
    void canRedo_whenNoCommandUndone_shouldReturnFalse()
    {
        assertFalse(commandInvoker.canRedo());

        when(mockCommand.execute()).thenReturn(true);
        when(mockCommand.undo()).thenReturn(true);

        commandInvoker.executeCommand(mockCommand);
        commandInvoker.undo();

        assertTrue(commandInvoker.canRedo());
    }

    @Test
    void getDescription_whenCalled_shouldReturnCommandDescription()
    {
        when(mockCommand.getDescription()).thenReturn("Test Command");
        assertEquals("Test Command", mockCommand.getDescription());
    }

    @Test
    void multipleCommands_whenExecutingAndUndoingMultipleCommands_shouldMaintainCorrectUndoRedoState()
    {
        Command mockCommand1 = mock(Command.class);
        Command mockCommand2 = mock(Command.class);

        when(mockCommand1.execute()).thenReturn(true);
        when(mockCommand1.undo()).thenReturn(true);
        when(mockCommand2.execute()).thenReturn(true);
        when(mockCommand2.undo()).thenReturn(true);

        commandInvoker.executeCommand(mockCommand1);
        commandInvoker.executeCommand(mockCommand2);

        assertTrue(commandInvoker.canUndo());
        assertFalse(commandInvoker.canRedo());

        commandInvoker.undo();

        assertTrue(commandInvoker.canUndo());
        assertTrue(commandInvoker.canRedo());

        commandInvoker.undo();

        assertFalse(commandInvoker.canUndo());
        assertTrue(commandInvoker.canRedo());

        commandInvoker.redo();

        assertTrue(commandInvoker.canUndo());
        assertTrue(commandInvoker.canRedo());

        commandInvoker.redo();

        assertTrue(commandInvoker.canUndo());
        assertFalse(commandInvoker.canRedo());
    }
} 