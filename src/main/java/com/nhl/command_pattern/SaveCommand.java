package com.nhl.command_pattern;

import com.nhl.Accessor;
import com.nhl.observer_pattern.Presentation;
import com.nhl.XMLAccessor;

import java.awt.Frame;
import javax.swing.JOptionPane;
import java.io.IOException;
import java.io.File;

public class SaveCommand implements Command
{
    private Presentation presentation;
    private Frame parent;
    private String savedFilename;
    private String previousContent;
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
    public boolean execute()
    {
        String filename = dialog();

        if (filename == null)
        {
            return false;
        }

        savedFilename = filename + ".xml";
        Accessor xmlAccessor = new XMLAccessor();

        try
        {
            File file = new File(savedFilename);

            if (file.exists())
            {
                previousContent = new String(java.nio.file.Files.readAllBytes(file.toPath()));
            }

            xmlAccessor.saveFile(presentation, savedFilename);

            return true;
        }
        catch (IOException exc)
        {
            JOptionPane.showMessageDialog(parent, IOEX + exc, SAVEERR, JOptionPane.ERROR_MESSAGE);

            return false;
        }
    }

    @Override
    public boolean undo()
    {
        if (previousContent != null && savedFilename != null)
        {
            try
            {
                java.nio.file.Files.write(
                        new File(savedFilename).toPath(),
                        previousContent.getBytes()
                );

                return true;
            }
            catch (IOException exc)
            {
                JOptionPane.showMessageDialog(parent,
                        "Failed to undo save: " + exc.getMessage(),
                        "Undo Error",
                        JOptionPane.ERROR_MESSAGE);

                return false;
            }
        }

        return false;
    }

    @Override
    public String getDescription()
    {
        return "Save Presentation";
    }

    private String dialog()
    {
        String filename = JOptionPane.showInputDialog("Enter the filename");

        if (filename == null || filename.isEmpty())
        {
            filename = SAVEFILE;
        }

        return filename;
    }
}
