package com.nhl.observer_pattern;

import com.nhl.Slide;

public interface PresentationObserver
{
    void update(Presentation presentation, Slide currentSlide);
}
