package com.nhl.command_pattern;

import com.nhl.Slide;
import com.nhl.observer_pattern.Presentation;

public class AddNewSlideCommand implements Command
{
    private Presentation presentation;
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

        presentation.append(newSlide);
        slideIndex = presentation.getSize() - 1;

        return true;
    }

    @Override
    public boolean undo()
    {
        return presentation.removeSlide(slideIndex);
    }

    @Override
    public String getDescription()
    {
        return "Add New Slide";
    }
}
