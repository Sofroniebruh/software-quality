package com.nhl;

import com.nhl.observer_pattern.Presentation;
import com.nhl.observer_pattern.PresentationObserver;

import javax.swing.*;
import java.awt.*;

public class SlideViewerComponent extends JComponent implements PresentationObserver
{
    private Slide slide;
    private Presentation presentation;
    private Font labelFont;
    private JFrame frame;
    private JScrollPane scrollPane;

    private static final long serialVersionUID = 227L;
    private static final Color BGCOLOR = Color.white;
    private static final Color COLOR = Color.black;
    private static final String FONTNAME = "Dialog";
    private static final int FONTSTYLE = Font.BOLD;
    private static final int FONTHEIGHT = 10;
    private static final int XPOS = 20;
    private static final int YPOS = 20;

    public SlideViewerComponent(JFrame frame)
    {
        setBackground(BGCOLOR);
        labelFont = new Font(FONTNAME, FONTSTYLE, FONTHEIGHT);
        this.frame = frame;

        scrollPane = new JScrollPane(this, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getViewport().setBackground(BGCOLOR);

        scrollPane.setPreferredSize(new Dimension(Slide.WIDTH, Slide.HEIGHT));

        setPreferredSize(new Dimension(Slide.WIDTH, Slide.HEIGHT));
    }

    public JScrollPane getScrollPane()
    {
        return scrollPane;
    }

    public Dimension getPreferredSize()
    {
        int contentHeight = getSlideHeight();

        return new Dimension(Slide.WIDTH, Math.max(Slide.HEIGHT, contentHeight));
    }

    private int getSlideHeight()
    {
        if (slide == null) return Slide.HEIGHT;
        int height = YPOS;

        if (slide.getTitle() != null && !slide.getTitle().isEmpty())
        {
            height += 50;
        }

        for (SlideItem item : slide.getSlideItems())
        {
            height += 40;
        }

        return height + 20;
    }

    @Override
    public void update(Presentation presentation, Slide currentSlide)
    {
        this.presentation = presentation;
        this.slide = currentSlide;

        if (slide == null)
        {
            System.out.println("Slide is null! Check if currentSlideNumber is valid.");
        }
        else
        {
            System.out.println("Updated slide: " + slide.getTitle());
        }

        setPreferredSize(getPreferredSize());
        revalidate();
        repaint();
        frame.setTitle(presentation.getTitle());
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(BGCOLOR);
        g.fillRect(0, 0, getSize().width, getSize().height);

        if (presentation == null) return;

        g.setFont(labelFont);
        g.setColor(COLOR);
        g.drawString("Slide " + (1 + presentation.getSlideNumber()) + " of " + presentation.getSize(), XPOS, YPOS);

        if (slide == null) return;

        Rectangle area = new Rectangle(XPOS, YPOS + 20, getWidth(), getHeight());
        slide.draw(g, area, this);
    }
}
