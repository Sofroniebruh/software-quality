import java.util.ArrayList;
import java.util.List;

public class Presentation implements Subject {
	private String showTitle;
	private ArrayList<Slide> showList = null;
	private int currentSlideNumber = 0;
	private List<PresentationObserver> observers = new ArrayList<>();

	public Presentation() {
		clear();
	}

	public Presentation(SlideViewerComponent slideViewerComponent) {
		clear();
	}

	public int getSize() {
		return showList.size();
	}

	public String getTitle() {
		return showTitle;
	}

	public void setTitle(String nt) {
		showTitle = nt;
		notifyObservers();
	}

	public int getSlideNumber() {
		return currentSlideNumber;
	}

	public void setSlideNumber(int number) {
		currentSlideNumber = number;
		notifyObservers();
	}

	void clear() {
		showList = new ArrayList<Slide>();
		setSlideNumber(-1);
		notifyObservers();
	}

	public void append(Slide slide) {
		showList.add(slide);
		notifyObservers();
	}

	public Slide getSlide(int number) {
		if (number < 0 || number >= getSize()) {
			return null;
		}
		return showList.get(number);
	}

	public Slide getCurrentSlide() {
		return getSlide(currentSlideNumber);
	}

	public void exit(int n) {
		System.exit(n);
	}

	@Override
	public void registerObserver(PresentationObserver observer) {
		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	@Override
	public void unregisterObserver(PresentationObserver observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyObservers() {
		Slide current = getCurrentSlide();
		for (PresentationObserver observer : observers) {
			observer.update(this, current);
		}
	}

	public void nextSlide() {
		if (currentSlideNumber < (showList.size() - 1)) {
			setSlideNumber(currentSlideNumber + 1);
		}
	}

	public void prevSlide() {
		if (currentSlideNumber > 0) {
			setSlideNumber(currentSlideNumber - 1);
		}
	}

}
