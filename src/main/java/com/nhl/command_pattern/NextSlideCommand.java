package com.nhl.command_pattern;

import com.nhl.observer_pattern.Presentation;

public class NextSlideCommand implements Command
{
    private Presentation presentation;

    public NextSlideCommand(Presentation presentation)
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
        presentation.nextSlide();

        return true;
    }

    @Override
    public boolean undo()
    {
        presentation.prevSlide();

        return true;
    }

    @Override
    public String getDescription()
    {
        return "Next Slide";
    }
}
