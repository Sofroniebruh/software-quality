package com.nhl.command_pattern;

import com.nhl.observer_pattern.Presentation;
import com.nhl.observer_pattern.SystemOperations;

public class ExitCommand implements Command
{
    private Presentation presentation;

    public ExitCommand(Presentation presentation)
    {
        this.presentation = presentation;
    }

    public Presentation getPresentation()
    {
        return presentation;
    }

    public void setPresentation(Presentation presentation)
    {
        this.presentation = presentation;
    }

    @Override
    public boolean execute()
    {
        SystemOperations.exit(0);

        return true;
    }

    @Override
    public boolean undo()
    {
        return false;
    }

    @Override
    public String getDescription()
    {
        return "Exit Application";
    }
}
