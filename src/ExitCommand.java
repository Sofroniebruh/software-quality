public class ExitCommand implements Command {
    private Presentation presentation;

    public ExitCommand(Presentation presentation) {
        this.presentation = presentation;
    }

    public Presentation getPresentation() { return presentation; }
    public void setPresentation(Presentation presentation) { this.presentation = presentation; }

    @Override
    public void execute() {
        presentation.exit(0);
    }

    @Override
    public void undo() {
        // No undo available for exit.
    }
}
