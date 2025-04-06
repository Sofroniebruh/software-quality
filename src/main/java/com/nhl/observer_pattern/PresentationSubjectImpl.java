package com.nhl.observer_pattern;

import com.nhl.Slide;

import java.util.ArrayList;
import java.util.List;

public class PresentationSubjectImpl implements PresentationSubject
{
    private List<PresentationObserver> observers = new ArrayList<>();

    @Override
    public void registerObserver(PresentationObserver observer)
    {
        if (!observers.contains(observer))
        {
            observers.add(observer);
        }
    }

    @Override
    public void unregisterObserver(PresentationObserver observer)
    {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Presentation presentation, Slide currentSlide)
    {
        for (PresentationObserver observer : observers)
        {
            observer.update(presentation, currentSlide);
        }
    }

    @Override
    public List<PresentationObserver> getObservers()
    {
        return observers;
    }
} 