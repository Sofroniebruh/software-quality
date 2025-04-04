package com.nhl.observer_pattern;

public interface Subject
{
    void registerObserver(PresentationObserver observer);
    void unregisterObserver(PresentationObserver observer);
    void notifyObservers();
}
