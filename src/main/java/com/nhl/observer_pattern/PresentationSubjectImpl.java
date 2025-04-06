package com.nhl.observer_pattern;

import com.nhl.Slide;

import java.util.ArrayList;
import java.util.List;

public class PresentationSubjectImpl implements PresentationSubject
{
    private final List<PresentationObserver> observers = new ArrayList<>();

    @Override
    public void registerObserver(PresentationObserver observer)
    {
        if (!this.observers.contains(observer))
        {
            this.observers.add(observer);
        }
    }

    @Override
    public void unregisterObserver(PresentationObserver observer)
    {
        this.observers.remove(observer);
    }

    @Override
    public void notifyObservers(Presentation presentation, Slide currentSlide)
    {
        for (PresentationObserver observer : this.observers)
        {
            observer.update(presentation, currentSlide);
        }
    }

    @Override
    public List<PresentationObserver> getObservers()
    {
        return this.observers;
    }
} 