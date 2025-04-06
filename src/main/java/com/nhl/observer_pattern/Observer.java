package com.nhl.observer_pattern;

import com.nhl.Slide;

public interface Observer
{
    void update(Presentation presentation, Slide currentSlide);
}
