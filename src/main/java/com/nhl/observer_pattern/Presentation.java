package com.nhl.observer_pattern;

import com.nhl.Slide;
import com.nhl.SlideViewerComponent;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Presentation
{
    private String showTitle;
    private ArrayList<Slide> showList = new ArrayList<>();
    private int currentSlideNumber = 0;
    private List<PresentationObserver> observers = new ArrayList<>();

    public Presentation()
    {
        clear();
    }

    public Presentation(SlideViewerComponent slideViewerComponent)
    {
        clear();
    }

    public int getSize()
    {
        return showList.size();
    }

    public String getTitle()
    {
        return showTitle;
    }

    public void setTitle(String nt)
    {
        showTitle = nt;
        notifyObservers();
    }

    public int getSlideNumber()
    {
        return currentSlideNumber;
    }

    public void setSlideNumber(int number)
    {
        System.out.println("Page number: " + number);

        if (number >= 0 && number < showList.size())
        {
            currentSlideNumber = number;
            notifyObservers();
        }
        else
        {
            if (number < 0)
            {
                JOptionPane.showMessageDialog(null,
                        "Invalid page number! Redirecting to the first page.",
                        "Invalid Page",
                        JOptionPane.WARNING_MESSAGE);
                setSlideNumber(0);
            }
            else if (number > showList.size())
            {
                JOptionPane.showMessageDialog(null,
                        "Invalid page number! Redirecting to the last page.",
                        "Invalid Page",
                        JOptionPane.WARNING_MESSAGE);
                setSlideNumber(showList.size() - 1);
            }
        }
    }


    public void clear()
    {
        setSlideNumber(0);
        this.showList.clear();
        notifyObservers();
    }

    public void append(Slide slide)
    {
        showList.add(slide);
        if (currentSlideNumber == -1)
        {
            setSlideNumber(0);
        }
        notifyObservers();
    }

    public Slide getSlide(int number)
    {
        if (number < 0 || number >= getSize())
        {
            return null;
        }

        return showList.get(number);
    }

    public Slide getCurrentSlide()
    {
        return getSlide(currentSlideNumber);
    }

    public void exit(int n)
    {
        System.exit(n);
    }

    public void registerObserver(PresentationObserver observer)
    {
        if (!observers.contains(observer))
        {
            observers.add(observer);
        }
    }

    public void unregisterObserver(PresentationObserver observer)
    {
        observers.remove(observer);
    }

    public void notifyObservers()
    {
        Slide current = getCurrentSlide();
        for (PresentationObserver observer : observers)
        {
            observer.update(this, current);
        }
    }

    public void nextSlide()
    {
        if (currentSlideNumber < (showList.size() - 1))
        {
            setSlideNumber(currentSlideNumber + 1);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "You've reached the end of the presentation", "End of the presentation", JOptionPane.OK_OPTION);
        }
    }

    public void prevSlide()
    {
        if (currentSlideNumber > 0)
        {
            setSlideNumber(currentSlideNumber - 1);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "You've reached the start of the presentation", "Start of the presentation", JOptionPane.OK_OPTION);
        }
    }

    public List<PresentationObserver> getObservers()
    {
        return observers;
    }
}
