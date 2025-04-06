package com.nhl.observer_pattern;

import com.nhl.Slide;

import java.util.List;

public interface Subject
{
    void registerObserver(Observer observer);
    void unregisterObserver(Observer observer);
    void notifyObservers(Presentation presentation, Slide currentSlide);
    List<Observer> getObservers();
} 