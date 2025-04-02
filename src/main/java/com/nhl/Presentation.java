package com.nhl;

import java.util.ArrayList;
import java.util.List;

public class Presentation {
	private String showTitle; // title of the presentation
	private ArrayList<Slide> showList = null; // an ArrayList with Slides
	private int currentSlideNumber = 0; // the slidenummer of the current com.nhl.Slide
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

	// change the current slide number and signal it to the window
	public void setSlideNumber(int number) {
		currentSlideNumber = number;
		notifyObservers();
	}

	void clear() {
		showList = new ArrayList<Slide>();
		setSlideNumber(-1);
		notifyObservers();
	}

	// Add a slide to the presentation
	public void append(Slide slide) {
		showList.add(slide);
		notifyObservers();
	}

	// Get a slide with a certain slidenumber
	public Slide getSlide(int number) {
		if (number < 0 || number >= getSize()) {
			return null;
		}
		return showList.get(number);
	}

	// Give the current slide
	public Slide getCurrentSlide() {
		return getSlide(currentSlideNumber);
	}

	public void exit(int n) {
		System.exit(n);
	}

	public void registerObserver(PresentationObserver observer) {
		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	public void unregisterObserver(PresentationObserver observer) {
		observers.remove(observer);
	}

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
