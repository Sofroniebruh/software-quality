package com.nhl;

public class NextSlideCommand implements Command {
    private Presentation presentation;

    public NextSlideCommand(Presentation presentation) {
        this.presentation = presentation;
    }

    public Presentation getPresentation() { return presentation; }
    public void setPresentation(Presentation presentation) { this.presentation = presentation; }

    @Override
    public void execute() {
        presentation.nextSlide();
    }

    @Override
    public void undo() {
        presentation.prevSlide();
    }
}
