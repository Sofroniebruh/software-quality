package com.nhl.command_pattern;

import com.nhl.observer_pattern.Presentation;

public class PrevSlideCommand implements Command
{
    private Presentation presentation;

    public PrevSlideCommand(Presentation presentation)
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
    public void execute()
    {
        presentation.prevSlide();
    }

    @Override
    public void undo()
    {
        presentation.nextSlide();
    }
}
