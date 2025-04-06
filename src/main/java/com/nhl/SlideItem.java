package com.nhl;

import javax.swing.*;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

public abstract class SlideItem
{
    private int level = 0;

    public SlideItem(int lev)
    {
        this.level = lev;
    }

    public SlideItem()
    {
        this(0);
    }

    public int getLevel()
    {
        return this.level;
    }

    public abstract Rectangle getBoundingBox(Graphics g,
                                             ImageObserver observer, float scale, Style style);

    public abstract void draw(int x, int y, float scale,
                              Graphics g, Style style, ImageObserver observer);
}
