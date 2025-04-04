package com.nhl.command_pattern;

import com.nhl.Accessor;
import com.nhl.observer_pattern.Presentation;
import com.nhl.XMLAccessor;

import java.awt.Frame;
import javax.swing.JOptionPane;
import java.io.IOException;

public class SaveCommand implements Command
{
    private Presentation presentation;
    private Frame parent;
    public static final String SAVEFILE = "dump.xml";
    public static final String IOEX = "IO Exception: ";
    public static final String SAVEERR = "Save Error";

    public SaveCommand(Presentation presentation, Frame parent)
    {
        this.presentation = presentation;
        this.parent = parent;
    }

    public Presentation getPresentation()
    {
        return presentation;
    }

    public void setPresentation(Presentation presentation)
    {
        this.presentation = presentation;
    }

    public Frame getParent()
    {
        return parent;
    }

    public void setParent(Frame parent)
    {
        this.parent = parent;
    }

    @Override
    public void execute()
    {
        Accessor xmlAccessor = new XMLAccessor();
        try
        {
            xmlAccessor.saveFile(presentation, SAVEFILE);
        }
        catch (IOException exc)
        {
            JOptionPane.showMessageDialog(parent, IOEX + exc, SAVEERR, JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void undo()
    {
        // No undo available.
    }
}
