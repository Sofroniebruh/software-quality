import javax.swing.JOptionPane;

public class GoToCommand implements Command {
    private Presentation presentation;
    public int previousSlideNumber;
    public static final String PAGENR = "Page number?";

    public GoToCommand(Presentation presentation) {
        this.presentation = presentation;
        previousSlideNumber = presentation.getSlideNumber();
    }

    public Presentation getPresentation() { return presentation; }
    public void setPresentation(Presentation presentation) { this.presentation = presentation; }

    @Override
    public void execute() {
        previousSlideNumber = presentation.getSlideNumber();
        String pageNumberStr = JOptionPane.showInputDialog(PAGENR);
        int pageNumber = Integer.parseInt(pageNumberStr);
        presentation.setSlideNumber(pageNumber - 1);
    }

    @Override
    public void undo() {
        presentation.setSlideNumber(previousSlideNumber);
    }
}
