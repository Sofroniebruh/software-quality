package com.nhl.command_pattern;

import com.nhl.observer_pattern.Presentation;
import com.nhl.SlideItem;
import com.nhl.XMLAccessor;
import com.nhl.factory_method.SlideItemFactory;

import javax.swing.*;
import java.awt.*;

public class EditCommand implements Command
{
    private final Presentation presentation;
    private final Frame parent;
    private final XMLAccessor accessor;
    private SlideItem addedItem;
    private int addedItemIndex;

    public EditCommand(Presentation presentation, Frame parent)
    {
        this.presentation = presentation;
        this.parent = parent;
        this.accessor = new XMLAccessor();
    }

    @Override
    public boolean execute()
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
            return this.addItem(this.accessor.getText());
        }
        else if (choice == 1)
        {
            return this.addItem(this.accessor.getImage());
        }

        return false;
    }

    @Override
    public boolean undo()
    {
        if (this.addedItem != null && this.presentation.getCurrentSlide() != null)
        {
            this.presentation.getCurrentSlide().removeItem(this.addedItemIndex);
            this.parent.repaint();

            return true;
        }

        return false;
    }

    @Override
    public String getDescription()
    {
        return "Edit Slide";
    }

    private boolean addItem(String type)
    {
        if (this.presentation.getCurrentSlide() == null)
        {
            JOptionPane.showMessageDialog(this.parent,
                    "Please select a slide first",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);

            return false;
        }

        String text = JOptionPane.showInputDialog("Enter the text");
        if (text == null)
        {
            return false;
        }

        int level = 1;
        String[] levels = {"1", "2", "3", "4"};
        int levelChoice = JOptionPane.showOptionDialog(
                null,
                "Choose the level:",
                "Select Level",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                levels,
                levels[0]
        );

        if (levelChoice >= 0)
        {
            level = Integer.parseInt(levels[levelChoice]);
        }

        this.addedItem = SlideItemFactory.createSlideItem(type, text, level);
        if (this.addedItem == null)
        {
            JOptionPane.showMessageDialog(this.parent,
                    "Error: Unknown type '" + type + "'. Cannot create SlideItem.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);

            return false;
        }

        this.addedItemIndex = this.presentation.getCurrentSlide().getSlideItems().size();
        this.presentation.getCurrentSlide().append(this.addedItem);
        this.parent.repaint();

        return true;
    }
}
