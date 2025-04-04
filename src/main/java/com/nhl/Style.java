package com.nhl;

import java.awt.Color;
import java.awt.Font;

public class Style
{
    public static Style[] styles;
    private static final String FONTNAME = "Helvetica";
    public int indent;
    private Color color;
    private Font font;
    private int fontSize;
    public int leading;

    public Style(int indent, Color color, int points, int leading)
    {
        this.indent = indent;
        this.color = color;
        this.fontSize = points;
        this.font = new Font(FONTNAME, Font.BOLD, points);
        this.leading = leading;
    }

    public int getIndent()
    {
        return indent;
    }

    public void setIndent(int indent)
    {
        this.indent = indent;
    }

    public Color getColor()
    {
        return color;
    }

    public void setColor(Color color)
    {
        this.color = color;
    }

    public Font getFont()
    {
        return font;
    }

    public void setFont(Font font)
    {
        this.font = font;
    }

    public int getFontSize()
    {
        return fontSize;
    }

    public void setFontSize(int fontSize)
    {
        this.fontSize = fontSize;
    }

    public int getLeading()
    {
        return leading;
    }

    public void setLeading(int leading)
    {
        this.leading = leading;
    }

    public static void createStyles()
    {
        styles = new Style[5];
        styles[0] = new Style(0, Color.red, 48, 20);
        styles[1] = new Style(20, Color.blue, 40, 10);
        styles[2] = new Style(50, Color.black, 36, 10);
        styles[3] = new Style(70, Color.black, 30, 10);
        styles[4] = new Style(90, Color.black, 24, 10);
    }

    public static Style getStyle(int level)
    {
        if (level >= styles.length)
        {
            level = styles.length - 1;
        }

        return styles[level];
    }

    public String toString()
    {
        return "[" + indent + "," + color + "; " + fontSize + " on " + leading + "]";
    }

    public Font getFont(float scale)
    {
        return font.deriveFont(fontSize * scale);
    }
}
