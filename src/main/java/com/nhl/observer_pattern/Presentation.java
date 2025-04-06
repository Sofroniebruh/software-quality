package com.nhl.observer_pattern;

import com.nhl.Slide;
import com.nhl.SlideViewerComponent;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;

public class Presentation implements ISlideManager, INavigation
{
    private String showTitle;
    private final ArrayList<Slide> showList = new ArrayList<>();
    private int currentSlideNumber = 0;
    private final Subject subject;
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
        this.subject = new SubjectImpl();
        clear();
    }

    public Presentation(SlideViewerComponent slideViewerComponent)
    {
        this(new SwingUIInteraction());
    }

    @Override
    public int getSize()
    {
        return this.showList.size();
    }

    @Override
    public String getTitle()
    {
        return this.showTitle;
    }

    @Override
    public void setTitle(String title)
    {
        this.showTitle = title;
        this.notifyObservers();
    }

    @Override
    public Slide getSlide(int number)
    {
        if (number < 0 || number >= this.getSize())
        {
            return null;
        }

        return this.showList.get(number);
    }

    @Override
    public Slide getCurrentSlide()
    {
        return this.getSlide(this.currentSlideNumber);
    }

    @Override
    public void append(Slide slide)
    {
        this.showList.add(slide);
        if (this.currentSlideNumber == -1)
        {
            this.setSlideNumber(0);
        }
        this.notifyObservers();
    }

    @Override
    public void clear()
    {
        this.setSlideNumber(0);
        this.showList.clear();
        this.notifyObservers();
    }

    @Override
    public boolean removeSlide(int index)
    {
        if (index < 0 || index >= this.showList.size())
        {
            return false;
        }

        this.showList.remove(index);

        if (this.currentSlideNumber >= this.showList.size())
        {
            this.setSlideNumber(Math.max(0, this.showList.size() - 1));
        }

        this.notifyObservers();

        return true;
    }

    @Override
    public int getSlideNumber()
    {
        return this.currentSlideNumber;
    }

    @Override
    public void setSlideNumber(int number)
    {
        System.out.println("Page number: " + number);

        if (this.showList.isEmpty())
        {
            this.currentSlideNumber = 0;
            this.notifyObservers();

            return;
        }

        if (number >= 0 && number < this.showList.size())
        {
            this.currentSlideNumber = number;
            this.notifyObservers();
        }
        else
        {
            this.errorHandler.handleInvalidSlideNumber(number, 0, this.showList.size() - 1);
            if (number < 0)
            {
                this.setSlideNumber(0);
            }
            else if (number >= this.showList.size())
            {
                this.setSlideNumber(this.showList.size() - 1);
            }
        }
    }

    @Override
    public void nextSlide()
    {
        if (this.currentSlideNumber < (this.showList.size() - 1))
        {
            this.setSlideNumber(this.currentSlideNumber + 1);
        }
        else
        {
            this.uiInteraction.showMessage("You've reached the end of the presentation",
                    "End of the presentation",
                    JOptionPane.OK_OPTION);
        }
    }

    @Override
    public void prevSlide()
    {
        if (this.currentSlideNumber > 0)
        {
            this.setSlideNumber(this.currentSlideNumber - 1);
        }
        else
        {
            this.uiInteraction.showMessage("You've reached the start of the presentation",
                    "Start of the presentation",
                    JOptionPane.OK_OPTION);
        }
    }

    public void registerObserver(Observer observer)
    {
        this.subject.registerObserver(observer);
    }

    public void unregisterObserver(Observer observer)
    {
        this.subject.unregisterObserver(observer);
    }

    private void notifyObservers()
    {
        this.subject.notifyObservers(this, this.getCurrentSlide());
    }

    public void exit(int n)
    {
        SystemOperations.exit(n);
    }

    public List<Observer> getObservers()
    {
        return this.subject.getObservers();
    }
}
