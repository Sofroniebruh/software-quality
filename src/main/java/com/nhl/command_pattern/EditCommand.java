package com.nhl.command_pattern;

import com.nhl.Style;
import com.nhl.observer_pattern.Presentation;
import com.nhl.SlideItem;
import com.nhl.XMLAccessor;
import com.nhl.factory_method.SlideItemFactory;

import javax.swing.*;
import java.awt.*;

public class EditCommand implements Command
{
    private Presentation presentation;
    private Frame parent;
    private XMLAccessor accessor;

    public EditCommand(Presentation presentation, Frame parent)
    {
        this.presentation = presentation;
        this.parent = parent;
        this.accessor = new XMLAccessor();
    }

    @Override
    public void execute()
    {
        String[] options = {this.accessor.getText(), this.accessor.getImage()};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Choose an element to add:",
                "Edit Slide",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == 0)
        {
            addItem(this.accessor.getText());
        }
        else if (choice == 1)
        {
            addItem(this.accessor.getImage());
        }
    }

    private void addItem(String type)
    {
        String text = JOptionPane.showInputDialog("Enter text: ");
        String level = JOptionPane.showInputDialog("Enter level from 1 to " + Style.styles.length + ": ");

        if (text != null && !text.trim().isEmpty() && level != null && !level.trim().isEmpty())
        {
            try
            {
                int levelInt = Integer.parseInt(level);

                if (levelInt < 0 || levelInt > Style.styles.length)
                {
                    System.err.println("Error: Invalid level " + level + ". Must be between 0 and " + (Style.styles.length - 1));
                    return;
                }

                SlideItem slideItem = SlideItemFactory.createSlideItem(type, text, Integer.parseInt(level));

                this.presentation.getCurrentSlide().append(slideItem);
                parent.repaint();
            }
            catch (NumberFormatException e)
            {
                dialog();
            }
        }
        else
        {
            dialog();
        }
    }

    public void dialog()
    {
        int result = JOptionPane.showConfirmDialog(null, "Invalid values provided. Do you want to retry?", "Invalid values", JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION)
        {
            execute();
        }
    }

    @Override
    public void undo()
    {

    }
}
