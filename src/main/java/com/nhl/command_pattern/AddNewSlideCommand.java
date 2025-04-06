package com.nhl.command_pattern;

import com.nhl.Slide;
import com.nhl.observer_pattern.Presentation;

public class AddNewSlideCommand implements Command
{
    private final Presentation presentation;
    private int slideIndex;

    public AddNewSlideCommand(Presentation presentation)
    {
        this.presentation = presentation;
    }

    @Override
    public boolean execute()
    {
        Slide newSlide = new Slide();
        newSlide.setTitle("New Slide");

        this.presentation.append(newSlide);
        this.slideIndex = this.presentation.getSize() - 1;

        return true;
    }

    @Override
    public boolean undo()
    {
        return this.presentation.removeSlide(this.slideIndex);
    }

    @Override
    public String getDescription()
    {
        return "Add New Slide";
    }
}
