package com.nhl.observer_pattern;

import com.nhl.Slide;

import java.util.List;

public interface PresentationSubject
{
    void registerObserver(PresentationObserver observer);
    void unregisterObserver(PresentationObserver observer);
    void notifyObservers(Presentation presentation, Slide currentSlide);
    List<PresentationObserver> getObservers();
} 