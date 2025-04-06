package com.nhl.observer_pattern;

import com.nhl.Slide;

import java.util.ArrayList;
import java.util.List;

public class SubjectImpl implements Subject
{
    private final List<Observer> observers = new ArrayList<>();

    @Override
    public void registerObserver(Observer observer)
    {
        if (!this.observers.contains(observer))
        {
            this.observers.add(observer);
        }
    }

    @Override
    public void unregisterObserver(Observer observer)
    {
        this.observers.remove(observer);
    }

    @Override
    public void notifyObservers(Presentation presentation, Slide currentSlide)
    {
        for (Observer observer : this.observers)
        {
            observer.update(presentation, currentSlide);
        }
    }

    @Override
    public List<Observer> getObservers()
    {
        return this.observers;
    }
} 