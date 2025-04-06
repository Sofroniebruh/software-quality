package com.nhl.command_pattern;

import com.nhl.observer_pattern.Presentation;

import javax.swing.JOptionPane;

public class GoToCommand implements Command
{
    private Presentation presentation;
    private int previousSlideNumber;
    public static final String PAGENR = "Choose the page to go?";

    public GoToCommand(Presentation presentation)
    {
        this.presentation = presentation;
        this.previousSlideNumber = presentation.getSlideNumber();
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
        previousSlideNumber = presentation.getSlideNumber();
        String pageNumberStr = JOptionPane.showInputDialog(PAGENR);

        try
        {
            int pageNumber = Integer.parseInt(pageNumberStr);
            presentation.setSlideNumber(pageNumber - 1);

            return true;
        }
        catch (NumberFormatException ex)
        {
            return dialog();
        }
    }

    @Override
    public boolean undo()
    {
        presentation.setSlideNumber(previousSlideNumber);

        return true;
    }

    @Override
    public String getDescription()
    {
        return "Go to Slide";
    }

    private boolean dialog()
    {
        int result = JOptionPane.showConfirmDialog(null,
                "Invalid page number provided. Do you want to retry?",
                "Invalid page number",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION)
        {
            return execute();
        }

        return false;
    }
}
