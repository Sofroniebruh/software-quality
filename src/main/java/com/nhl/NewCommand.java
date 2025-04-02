package com.nhl;

import java.awt.Frame;

public class NewCommand implements Command {
    private Presentation presentation;
    private Frame parent;

    public NewCommand(Presentation presentation, Frame parent) {
        this.presentation = presentation;
        this.parent = parent;
    }

    public Presentation getPresentation() { return presentation; }
    public void setPresentation(Presentation presentation) { this.presentation = presentation; }
    public Frame getParent() { return parent; }
    public void setParent(Frame parent) { this.parent = parent; }

    @Override
    public void execute() {
        presentation.clear();
        parent.repaint();
    }

    @Override
    public void undo() {
        // No undo available.
    }
}
