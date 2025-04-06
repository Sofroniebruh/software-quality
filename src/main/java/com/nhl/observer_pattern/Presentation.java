package com.nhl.observer_pattern;

import com.nhl.Slide;
import com.nhl.SlideViewerComponent;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;

public class Presentation implements ISlideManager, INavigation
{
    private String showTitle;
    private ArrayList<Slide> showList = new ArrayList<>();
    private int currentSlideNumber = 0;
    private final PresentationSubject subject;
    private final IUIInteraction uiInteraction;
    private final IErrorHandler errorHandler;

    public Presentation()
    {
        this(new SwingUIInteraction());
    }

    public Presentation(IUIInteraction uiInteraction)
    {
        this.uiInteraction = uiInteraction;
        this.errorHandler = new PresentationErrorHandler(uiInteraction);
        this.subject = new PresentationSubjectImpl();
        clear();
    }

    public Presentation(SlideViewerComponent slideViewerComponent)
    {
        this(new SwingUIInteraction());
    }

    @Override
    public int getSize()
    {
        return showList.size();
    }

    @Override
    public String getTitle()
    {
        return showTitle;
    }

    @Override
    public void setTitle(String title)
    {
        showTitle = title;
        notifyObservers();
    }

    @Override
    public Slide getSlide(int number)
    {
        if (number < 0 || number >= getSize())
        {
            return null;
        }

        return showList.get(number);
    }

    @Override
    public Slide getCurrentSlide()
    {
        return getSlide(currentSlideNumber);
    }

    @Override
    public void append(Slide slide)
    {
        showList.add(slide);
        if (currentSlideNumber == -1)
        {
            setSlideNumber(0);
        }
        notifyObservers();
    }

    @Override
    public void clear()
    {
        setSlideNumber(0);
        this.showList.clear();
        notifyObservers();
    }

    @Override
    public boolean removeSlide(int index)
    {
        if (index < 0 || index >= showList.size())
        {
            return false;
        }

        showList.remove(index);

        if (currentSlideNumber >= showList.size())
        {
            setSlideNumber(Math.max(0, showList.size() - 1));
        }

        notifyObservers();

        return true;
    }

    @Override
    public int getSlideNumber()
    {
        return currentSlideNumber;
    }

    @Override
    public void setSlideNumber(int number)
    {
        System.out.println("Page number: " + number);

        if (showList.isEmpty())
        {
            currentSlideNumber = 0;
            notifyObservers();

            return;
        }

        if (number >= 0 && number < showList.size())
        {
            currentSlideNumber = number;
            notifyObservers();
        }
        else
        {
            errorHandler.handleInvalidSlideNumber(number, 0, showList.size() - 1);
            if (number < 0)
            {
                setSlideNumber(0);
            }
            else if (number >= showList.size())
            {
                setSlideNumber(showList.size() - 1);
            }
        }
    }

    @Override
    public void nextSlide()
    {
        if (currentSlideNumber < (showList.size() - 1))
        {
            setSlideNumber(currentSlideNumber + 1);
        }
        else
        {
            uiInteraction.showMessage("You've reached the end of the presentation",
                    "End of the presentation",
                    JOptionPane.OK_OPTION);
        }
    }

    @Override
    public void prevSlide()
    {
        if (currentSlideNumber > 0)
        {
            setSlideNumber(currentSlideNumber - 1);
        }
        else
        {
            uiInteraction.showMessage("You've reached the start of the presentation",
                    "Start of the presentation",
                    JOptionPane.OK_OPTION);
        }
    }

    public void registerObserver(PresentationObserver observer)
    {
        subject.registerObserver(observer);
    }

    public void unregisterObserver(PresentationObserver observer)
    {
        subject.unregisterObserver(observer);
    }

    private void notifyObservers()
    {
        subject.notifyObservers(this, getCurrentSlide());
    }

    public void exit(int n)
    {
        SystemOperations.exit(n);
    }

    public List<PresentationObserver> getObservers()
    {
        return subject.getObservers();
    }
}
