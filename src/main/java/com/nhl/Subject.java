package com.nhl;

public interface Subject
{
    void registerObserver(PresentationObserver observer);
    void unregisterObserver(PresentationObserver observer);
    void notifyObservers();
}
