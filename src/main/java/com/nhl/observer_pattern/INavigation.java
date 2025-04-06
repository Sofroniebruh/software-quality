package com.nhl.observer_pattern;

public interface INavigation
{
    int getSlideNumber();
    void setSlideNumber(int number);
    void nextSlide();
    void prevSlide();
} 