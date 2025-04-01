import javax.swing.JOptionPane;
import java.io.IOException;

public class JabberPoint {
	protected static final String IOERR = "IO Error: ";
	protected static final String JABERR = "Jabberpoint Error ";
	protected static final String JABVERSION = "Jabberpoint 1.6 - OU version";

	public static void main(String argv[]) {
		Style.createStyles();
		Presentation presentation = new Presentation();
		new SlideViewerFrame(JABVERSION, presentation);
		try {
			if (argv.length == 0) {
				Accessor.getDemoAccessor().loadFile(presentation, "");
			} else {
				new XMLAccessor(new DefaultSlideFactory()).loadFile(presentation, argv[0]);
			}
			presentation.setSlideNumber(0);
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null,
					IOERR + ex, JABERR,
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
