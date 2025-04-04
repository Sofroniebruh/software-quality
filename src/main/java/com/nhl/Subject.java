package com.nhl;

import com.nhl.observer_pattern.PresentationObserver;

public interface Subject
{
    void registerObserver(PresentationObserver observer);
    void unregisterObserver(PresentationObserver observer);
    void notifyObservers();
}
