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
        return this.presentation;
    }

    public void setPresentation(Presentation presentation)
    {
        this.presentation = presentation;
    }

    @Override
    public boolean execute()
    {
        this.presentation.prevSlide();

        return true;
    }

    @Override
    public boolean undo()
    {
        this.presentation.nextSlide();

        return true;
    }

    @Override
    public String getDescription()
    {
        return "Previous Slide";
    }
}
