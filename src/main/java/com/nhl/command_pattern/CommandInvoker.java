package com.nhl.command_pattern;

import java.util.Stack;

public class CommandInvoker
{
    private Stack<Command> commandHistory;
    private Stack<Command> redoStack;

    public CommandInvoker()
    {
        this.commandHistory = new Stack<>();
        this.redoStack = new Stack<>();
    }

    public void executeCommand(Command command)
    {
        command.execute();
        commandHistory.push(command);
        redoStack.clear();
    }

    public void undo()
    {
        if (!commandHistory.isEmpty())
        {
            Command command = commandHistory.pop();
            command.undo();
            redoStack.push(command);
        }
    }

    public void redo()
    {
        if (!redoStack.isEmpty())
        {
            Command command = redoStack.pop();
            command.execute();
            commandHistory.push(command);
        }
    }

    public boolean canUndo()
    {
        return !commandHistory.isEmpty();
    }

    public boolean canRedo()
    {
        return !redoStack.isEmpty();
    }
} 