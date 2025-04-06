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
    private final XMLAccessor xmlAccessor;
    public static final String SAVEFILE = "dump.xml";
    public static final String IOEX = "IO Exception: ";
    public static final String SAVEERR = "Save Error";

    public SaveCommand(Presentation presentation, Frame parent)
    {
        this(presentation, parent, new XMLAccessor());
    }

    public SaveCommand(Presentation presentation, Frame parent, XMLAccessor xmlAccessor)
    {
        this.presentation = presentation;
        this.parent = parent;
        this.xmlAccessor = xmlAccessor;
    }

    public Presentation getPresentation()
    {
        return this.presentation;
    }

    public void setPresentation(Presentation presentation)
    {
        this.presentation = presentation;
    }

    public Frame getParent()
    {
        return this.parent;
    }

    public void setParent(Frame parent)
    {
        this.parent = parent;
    }

    @Override
    public boolean execute()
    {
        String filename = this.dialog();

        if (filename == null)
        {
            return false;
        }

        this.savedFilename = filename + ".xml";

        try
        {
            File file = new File(this.savedFilename);

            if (file.exists())
            {
                this.previousContent = new String(java.nio.file.Files.readAllBytes(file.toPath()));
            }

            this.xmlAccessor.saveFile(this.presentation, this.savedFilename);

            return true;
        }
        catch (IOException exc)
        {
            JOptionPane.showMessageDialog(this.parent, IOEX + exc, SAVEERR, JOptionPane.ERROR_MESSAGE);

            return false;
        }
    }

    @Override
    public boolean undo()
    {
        if (this.previousContent != null && this.savedFilename != null)
        {
            try
            {
                java.nio.file.Files.write(
                        new File(this.savedFilename).toPath(),
                        this.previousContent.getBytes()
                );

                return true;
            }
            catch (IOException exc)
            {
                JOptionPane.showMessageDialog(this.parent,
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

        if (filename == null)
        {
            return null;
        }

        if (filename.isEmpty())
        {
            return SAVEFILE;
        }

        return filename;
    }
}
