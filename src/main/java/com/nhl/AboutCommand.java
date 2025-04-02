package com.nhl;

import java.awt.Frame;

public class AboutCommand implements Command
{
    private Frame parent;

    public AboutCommand(Frame parent)
    {
        this.parent = parent;
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
        AboutBox.show(parent);
    }

    @Override
    public void undo()
    {
        // No undo available.
    }
}
