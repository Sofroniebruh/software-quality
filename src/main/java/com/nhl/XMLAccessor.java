package com.nhl;

import java.util.Vector;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

public class XMLAccessor extends Accessor {
	private SlideFactory factory;
	protected static final String SHOWTITLE = "showtitle";
	protected static final String SLIDETITLE = "title";
	protected static final String SLIDE = "slide";
	protected static final String ITEM = "item";
	protected static final String LEVEL = "level";
	protected static final String KIND = "kind";

	public XMLAccessor(SlideFactory factory) {
		this.factory = factory;
	}

	public void loadFile(Presentation presentation, String filename) throws IOException {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = builder.parse(new File(filename));
			Element doc = document.getDocumentElement();
			presentation.setTitle(getTextContent(doc, SHOWTITLE));
			NodeList slides = doc.getElementsByTagName(SLIDE);
			for (int i = 0; i < slides.getLength(); i++) {
				Element xmlSlide = (Element) slides.item(i);
				Slide slide = factory.createSlide(getTextContent(xmlSlide, SLIDETITLE));
				presentation.append(slide);
				NodeList slideItems = xmlSlide.getElementsByTagName(ITEM);
				for (int j = 0; j < slideItems.getLength(); j++) {
					Element item = (Element) slideItems.item(j);
					NamedNodeMap attrs = item.getAttributes();
					String levelStr = attrs.getNamedItem(LEVEL).getTextContent();
					int level = Integer.parseInt(levelStr);
					String type = attrs.getNamedItem(KIND).getTextContent();
					SlideItem slideItem = factory.createSlideItem(type, level, item.getTextContent());
					if (slideItem != null) {
						slide.append(slideItem);
					}
				}
			}
		} catch (ParserConfigurationException | SAXException e) {
			System.err.println(e.getMessage());
		}
	}

	private String getTextContent(Element element, String tagName) {
		NodeList list = element.getElementsByTagName(tagName);
		if (list.getLength() == 0) return "";
		return list.item(0).getTextContent();
	}

	public void saveFile(Presentation presentation, String filename) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(filename));
		out.println("<?xml version=\"1.0\"?>");
		out.println("<!DOCTYPE presentation SYSTEM \"jabberpoint.dtd\">");
		out.println("<presentation>");
		out.print("<showtitle>");
		out.print(presentation.getTitle());
		out.println("</showtitle>");
		for (int slideNumber = 0; slideNumber < presentation.getSize(); slideNumber++) {
			Slide slide = presentation.getSlide(slideNumber);
			out.println("<slide>");
			out.println("<title>" + slide.getTitle() + "</title>");
			Vector<SlideItem> slideItems = slide.getSlideItems();
			for (SlideItem si : slideItems) {
				out.print("<item kind=");
				if (si instanceof TextItem) {
					out.print("\"text\" level=\"" + si.getLevel() + "\">");
					out.print(((TextItem) si).getText());
				} else if (si instanceof BitmapItem) {
					out.print("\"image\" level=\"" + si.getLevel() + "\">");
					out.print(((BitmapItem) si).getName());
				}
				out.println("</item>");
			}
			out.println("</slide>");
		}
		out.println("</presentation>");
		out.close();
	}
}
