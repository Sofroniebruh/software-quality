package com.nhl.observer_pattern;

import javax.swing.JOptionPane;

public class PresentationErrorHandler implements IErrorHandler
{
    private final IUIInteraction uiInteraction;

    public PresentationErrorHandler(IUIInteraction uiInteraction)
    {
        this.uiInteraction = uiInteraction;
    }

    @Override
    public void handleInvalidSlideNumber(int number, int min, int max)
    {
        if (min == 0 && max == 0)
        {
            return;
        }

        if (number < min)
        {
            uiInteraction.showMessage("Invalid page number! Redirecting to the first page.",
                    "Invalid Page",
                    JOptionPane.WARNING_MESSAGE);
        }
        else if (number > max)
        {
            uiInteraction.showMessage("Invalid page number! Redirecting to the last page.",
                    "Invalid Page",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
} 