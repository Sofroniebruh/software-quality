package com.nhl.observer_pattern;

import com.nhl.Slide;

public interface ISlideManager
{
    int getSize();
    String getTitle();
    void setTitle(String title);
    Slide getSlide(int number);
    Slide getCurrentSlide();
    void append(Slide slide);
    void clear();
    boolean removeSlide(int index);
} 