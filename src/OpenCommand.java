import java.awt.Frame;
import javax.swing.JOptionPane;
import java.io.IOException;

public class OpenCommand implements Command {
    private Presentation presentation;
    private Frame parent;
    public static final String TESTFILE = "test.xml";
    public static final String IOEX = "IO Exception: ";
    public static final String LOADERR = "Load Error";

    public OpenCommand(Presentation presentation, Frame parent) {
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
        Accessor xmlAccessor = new XMLAccessor(new DefaultSlideFactory());
        try {
            xmlAccessor.loadFile(presentation, TESTFILE);
            presentation.setSlideNumber(0);
        } catch (IOException exc) {
            JOptionPane.showMessageDialog(parent, IOEX + exc, LOADERR, JOptionPane.ERROR_MESSAGE);
        }
        parent.repaint();
    }

    @Override
    public void undo() {
        // No undo available.
    }
}
