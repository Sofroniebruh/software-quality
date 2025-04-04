package com.nhl.command_pattern;

import com.nhl.observer_pattern.Presentation;

import javax.swing.JOptionPane;

public class GoToCommand implements Command
{
    private Presentation presentation;
    public int previousSlideNumber;
    public static final String PAGENR = "Choose the page to go?";

    public GoToCommand(Presentation presentation)
    {
        this.presentation = presentation;
        previousSlideNumber = presentation.getSlideNumber();
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
        previousSlideNumber = presentation.getSlideNumber();
        String pageNumberStr = JOptionPane.showInputDialog(PAGENR);
        try
        {
            int pageNumber = Integer.parseInt(pageNumberStr);
            presentation.setSlideNumber(pageNumber - 1);
        }
        catch (NumberFormatException ex)
        {
            dialog();
        }
    }

    public void dialog()
    {
        int result = JOptionPane.showConfirmDialog(null, "Invalid page number provided. Do you want to retry?", "Invalid page number", JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION)
        {
            execute();
        }
    }

    @Override
    public void undo()
    {
        presentation.setSlideNumber(previousSlideNumber);
    }
}
