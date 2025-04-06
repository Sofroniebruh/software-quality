package com.nhl;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.Vector;

public class Slide
{
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;
    protected String title;
    protected Vector<SlideItem> items;

    public Slide()
    {
        this.items = new Vector<SlideItem>();
    }

    public void append(SlideItem anItem)
    {
        this.items.addElement(anItem);
    }

    public String getTitle()
    {
        return this.title;
    }

    public void setTitle(String newTitle)
    {
        this.title = newTitle;
    }

    public void append(int level, String message)
    {
        this.append(new TextItem(level, message));
    }

    public SlideItem getSlideItem(int number)
    {
        if (number < 0 || number >= this.items.size())
        {
            return null;
        }

        return this.items.get(number);
    }

    public Vector<SlideItem> getSlideItems()
    {
        return this.items;
    }

    public int getSize()
    {
        return this.items.size();
    }

    public boolean removeItem(int index)
    {
        if (index < 0 || index >= this.items.size())
        {
            return false;
        }

        this.items.removeElementAt(index);

        return true;
    }

    public void draw(Graphics g, Rectangle area, ImageObserver view)
    {
        float scale = this.getScale(area);
        int y = area.y;

        if (this.title != null && !this.title.isEmpty())
        {
            SlideItem slideItem = new TextItem(0, this.title);
            Style style = Style.getStyle(slideItem.getLevel());
            slideItem.draw(area.x, y, scale, g, style, view);
            y += slideItem.getBoundingBox(g, view, scale, style).height;
        }

        for (SlideItem slideItem : this.items)
        {
            Style style = Style.getStyle(slideItem.getLevel());
            slideItem.draw(area.x, y, scale, g, style, view);
            y += slideItem.getBoundingBox(g, view, scale, style).height;
        }
    }

    private float getScale(Rectangle area)
    {
        return Math.min(((float) area.width) / ((float) WIDTH), ((float) area.height) / ((float) HEIGHT));
    }
}
