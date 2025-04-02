package com.nhl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class SlideViewerComponent extends JComponent implements PresentationObserver {
	private Slide slide;
	private Presentation presentation;
	private Font labelFont;
	private JFrame frame;
	private static final long serialVersionUID = 227L;
	private static final Color BGCOLOR = Color.white;
	private static final Color COLOR = Color.black;
	private static final String FONTNAME = "Dialog";
	private static final int FONTSTYLE = Font.BOLD;
	private static final int FONTHEIGHT = 10;
	private static final int XPOS = 1100;
	private static final int YPOS = 20;

	public SlideViewerComponent(JFrame frame) {
		setBackground(BGCOLOR);
		labelFont = new Font(FONTNAME, FONTSTYLE, FONTHEIGHT);
		this.frame = frame;
	}

	public Dimension getPreferredSize() {
		return new Dimension(Slide.WIDTH, Slide.HEIGHT);
	}

	@Override
	public void update(Presentation presentation, Slide currentSlide) {
		this.presentation = presentation;
		this.slide = currentSlide;
		repaint();
		frame.setTitle(presentation.getTitle());
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(BGCOLOR);
		g.fillRect(0, 0, getSize().width, getSize().height);
		if (presentation != null && presentation.getSlideNumber() >= 0) {
			g.setFont(labelFont);
			g.setColor(COLOR);
			g.drawString(
					"Slide " + (1 + presentation.getSlideNumber()) + " of " + presentation.getSize(),
					XPOS,
					YPOS
			);
		}
		if (slide == null) {
			return;
		}
		Rectangle area = new Rectangle(0, YPOS, getWidth(), (getHeight() - YPOS));
		slide.draw(g, area, this);
	}
}
