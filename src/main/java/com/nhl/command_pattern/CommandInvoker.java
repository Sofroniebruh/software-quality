package com.nhl.command_pattern;

import java.util.Stack;

public class CommandInvoker
{
    private final Stack<Command> commandHistory;
    private final Stack<Command> redoStack;

    public CommandInvoker()
    {
        this.commandHistory = new Stack<>();
        this.redoStack = new Stack<>();
    }

    public void executeCommand(Command command)
    {
        command.execute();
        this.commandHistory.push(command);
        this.redoStack.clear();
    }

    public void undo()
    {
        if (!this.commandHistory.isEmpty())
        {
            Command command = this.commandHistory.pop();
            command.undo();
            this.redoStack.push(command);
        }
    }

    public void redo()
    {
        if (!this.redoStack.isEmpty())
        {
            Command command = this.redoStack.pop();
            command.execute();
            this.commandHistory.push(command);
        }
    }

    public boolean canUndo()
    {
        return !this.commandHistory.isEmpty();
    }

    public boolean canRedo()
    {
        return !this.redoStack.isEmpty();
    }
} 