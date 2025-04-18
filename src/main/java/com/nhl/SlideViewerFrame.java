package com.nhl;

import com.nhl.observer_pattern.Presentation;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import javax.swing.*;

public class SlideViewerFrame extends JFrame
{
    private static final long serialVersionUID = 3227L;
    private static final String JABTITLE = "Jabberpoint 1.6 - OU";
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;

    public SlideViewerFrame(String title, Presentation presentation)
    {
        super(title);
        SlideViewerComponent slideViewerComponent = new SlideViewerComponent(this);
        presentation.registerObserver(slideViewerComponent);
        setupWindow(slideViewerComponent, presentation);
    }

    public void setupWindow(SlideViewerComponent slideViewerComponent, Presentation presentation)
    {
        setTitle(JABTITLE);
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });

        JScrollPane scrollPane = slideViewerComponent.getScrollPane();
        getContentPane().add(scrollPane);
        setSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        
        addKeyListener(new KeyController(presentation));
        setMenuBar(new MenuController(this, presentation));
        
        setVisible(true);
    }
}
