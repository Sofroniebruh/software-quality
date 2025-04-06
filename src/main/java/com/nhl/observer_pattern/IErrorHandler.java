package com.nhl.observer_pattern;

public interface IErrorHandler
{
    void handleInvalidSlideNumber(int number, int min, int max);
} 