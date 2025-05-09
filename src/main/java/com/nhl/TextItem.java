package com.nhl;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.TextLayout;
import java.awt.font.TextAttribute;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.text.AttributedString;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

public class TextItem extends SlideItem
{
    private String text;
    private static final String EMPTYTEXT = "No Text Given";

    public TextItem(int level, String string)
    {
        super(level);
        text = string;
    }

    public String getText()
    {
        return text == null ? "" : text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public AttributedString getAttributedString(Style style, float scale)
    {
        String actualText = getText();

        if (actualText == null || actualText.isEmpty())
        {
            actualText = EMPTYTEXT;
        }

        AttributedString attrStr = new AttributedString(actualText);
        attrStr.addAttribute(TextAttribute.FONT, style.getFont(scale), 0, actualText.length());

        return attrStr;
    }

    public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style myStyle)
    {
        List<TextLayout> layouts = getLayouts(g, myStyle, scale);
        int xsize = 0;
        int ysize = (int) (myStyle.getLeading() * scale);
        Iterator<TextLayout> iterator = layouts.iterator();
        while (iterator.hasNext())
        {
            TextLayout layout = iterator.next();
            Rectangle2D bounds = layout.getBounds();
            if (bounds.getWidth() > xsize)
            {
                xsize = (int) bounds.getWidth();
            }
            if (bounds.getHeight() > 0)
            {
                ysize += bounds.getHeight();
            }
            ysize += layout.getLeading() + layout.getDescent();
        }

        return new Rectangle((int) (myStyle.getIndent() * scale), 0, xsize, ysize);
    }

    public void draw(int x, int y, float scale, Graphics g, Style myStyle, ImageObserver o)
    {
        if (text == null || text.length() == 0)
        {
            return;
        }

        List<TextLayout> layouts = getLayouts(g, myStyle, scale);
        Point pen = new Point(x + (int) (myStyle.getIndent() * scale), y + (int) (myStyle.getLeading() * scale));
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(myStyle.getColor());
        Iterator<TextLayout> it = layouts.iterator();

        while (it.hasNext())
        {
            TextLayout layout = it.next();
            pen.y += layout.getAscent();
            layout.draw(g2d, pen.x, pen.y);
            pen.y += layout.getDescent();
        }
    }

    private List<TextLayout> getLayouts(Graphics g, Style s, float scale)
    {
        List<TextLayout> layouts = new ArrayList<TextLayout>();
        AttributedString attrStr = getAttributedString(s, scale);
        Graphics2D g2d = (Graphics2D) g;
        FontRenderContext frc = g2d.getFontRenderContext();
        LineBreakMeasurer measurer = new LineBreakMeasurer(attrStr.getIterator(), frc);
        float wrappingWidth = (Slide.WIDTH - s.getIndent()) * scale;
        while (measurer.getPosition() < getText().length())
        {
            TextLayout layout = measurer.nextLayout(wrappingWidth);
            layouts.add(layout);
        }

        return layouts;
    }

    public String toString()
    {
        return "TextItem[" + getLevel() + "," + getText() + "]";
    }
}
