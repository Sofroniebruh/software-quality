import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public class KeyController extends KeyAdapter {
	private Command nextSlideCommand;
	private Command prevSlideCommand;
	private Command exitCommand;

	public KeyController(Presentation presentation) {
		nextSlideCommand = new NextSlideCommand(presentation);
		prevSlideCommand = new PrevSlideCommand(presentation);
		exitCommand = new ExitCommand(presentation);
	}

	public Command getNextSlideCommand() { return nextSlideCommand; }
	public void setNextSlideCommand(Command nextSlideCommand) { this.nextSlideCommand = nextSlideCommand; }
	public Command getPrevSlideCommand() { return prevSlideCommand; }
	public void setPrevSlideCommand(Command prevSlideCommand) { this.prevSlideCommand = prevSlideCommand; }
	public Command getExitCommand() { return exitCommand; }
	public void setExitCommand(Command exitCommand) { this.exitCommand = exitCommand; }

	public void keyPressed(KeyEvent keyEvent) {
		switch(keyEvent.getKeyCode()) {
			case KeyEvent.VK_PAGE_DOWN:
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_ENTER:
			case '+':
				nextSlideCommand.execute();
				break;
			case KeyEvent.VK_PAGE_UP:
			case KeyEvent.VK_UP:
			case '-':
				prevSlideCommand.execute();
				break;
			case 'q':
			case 'Q':
				exitCommand.execute();
				break;
			default:
				break;
		}
	}
}
