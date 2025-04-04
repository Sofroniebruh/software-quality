package com.nhl;

import java.util.Vector;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.nhl.factory_method.SlideItemFactory;
import com.nhl.observer_pattern.Presentation;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

public class XMLAccessor extends Accessor
{
    protected static final String DEFAULT_API_TO_USE = "dom";
    protected static final String SHOWTITLE = "showtitle";
    protected static final String SLIDETITLE = "title";
    protected static final String SLIDE = "slide";
    protected static final String ITEM = "item";
    protected static final String LEVEL = "level";
    protected static final String KIND = "kind";
    protected static final String TEXT = "text";
    protected static final String IMAGE = "image";
    protected static final String PCE = "Parser Configuration Exception";
    protected static final String UNKNOWNTYPE = "Unknown Element type";
    protected static final String NFE = "Number Format Exception";

    public String getText()
    {
        return TEXT;
    }

    public String getImage()
    {
        return IMAGE;
    }

    private String getTitle(Element element, String tagName)
    {
        NodeList titles = element.getElementsByTagName(tagName);

        return titles.item(0).getTextContent();
    }

    public void loadFile(Presentation presentation, String filename) throws IOException
    {
        int slideNumber, itemNumber, max = 0, maxItems = 0;

        try
        {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(new File(filename));
            Element doc = document.getDocumentElement();
            presentation.setTitle(getTitle(doc, SHOWTITLE));
            NodeList slides = doc.getElementsByTagName(SLIDE);
            max = slides.getLength();

            for (slideNumber = 0; slideNumber < max; slideNumber++)
            {
                Element xmlSlide = (Element) slides.item(slideNumber);
                Slide slide = new Slide();

                slide.setTitle(getTitle(xmlSlide, SLIDETITLE));
                presentation.append(slide);

                NodeList slideItems = xmlSlide.getElementsByTagName(ITEM);
                maxItems = slideItems.getLength();

                for (itemNumber = 0; itemNumber < maxItems; itemNumber++)
                {
                    Element item = (Element) slideItems.item(itemNumber);
                    loadSlideItem(slide, item);
                }
            }
        }
        catch (IOException iox)
        {
            System.err.println(iox.toString());
        }
        catch (SAXException sax)
        {
            System.err.println(sax.getMessage());
        }
        catch (ParserConfigurationException pcx)
        {
            System.err.println(PCE);
        }
    }

    public void slideItemAction(Slide slide, String type, String text, int level)
    {
        SlideItem slideItem = SlideItemFactory.createSlideItem(type, text, level);

        if (slideItem == null)
        {
            System.err.println(UNKNOWNTYPE);

            return;
        }
        slide.append(slideItem);
    }

    public void loadSlideItem(Slide slide, Element item)
    {
        int level = 1;
        NamedNodeMap attributes = item.getAttributes();

        String levelText = (attributes.getNamedItem(LEVEL) != null) ? attributes.getNamedItem(LEVEL).getTextContent() : "1";
        try
        {
            level = Integer.parseInt(levelText);
        }
        catch (NumberFormatException x)
        {
            System.err.println("Invalid level format: " + levelText);
        }

        String type = (attributes.getNamedItem(KIND) != null) ? attributes.getNamedItem(KIND).getTextContent() : null;
        String text = item.getTextContent().trim();

        slideItemAction(slide, type, text, level);
    }

    public void saveFile(Presentation presentation, String filename) throws IOException
    {
        try (PrintWriter out = new PrintWriter(new FileWriter(filename)))
        {
            out.println("<?xml version=\"1.0\"?>");
            out.println("<!DOCTYPE presentation SYSTEM \"jabberpoint.dtd\">");
            out.println("<presentation>");

            out.print("<showtitle>");
            escapeAndPrint(out, presentation.getTitle());
            out.println("</showtitle>");

            for (int slideNumber = 0; slideNumber < presentation.getSize(); slideNumber++)
            {
                Slide slide = presentation.getSlide(slideNumber);
                out.println("<slide>");
                out.print("<title>");
                escapeAndPrint(out, slide.getTitle());
                out.println("</title>");

                Vector<SlideItem> slideItems = slide.getSlideItems();
                for (SlideItem slideItem : slideItems)
                {
                    out.print("<item kind=");

                    if (slideItem instanceof TextItem)
                    {
                        out.print("\"text\" level=\"" + slideItem.getLevel() + "\">");
                        escapeAndPrint(out, ((TextItem) slideItem).getText());
                    }
                    else if (slideItem instanceof BitmapItem)
                    {
                        out.print("\"image\" level=\"" + slideItem.getLevel() + "\">");
                        escapeAndPrint(out, ((BitmapItem) slideItem).getName());
                    }
                    else
                    {
                        System.out.println("Ignoring " + slideItem);
                    }

                    out.println("</item>");
                }

                out.println("</slide>");
            }

            out.println("</presentation>");
        }
    }

    private void escapeAndPrint(PrintWriter out, String text)
    {
        if (text != null)
        {
            out.print(text.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&apos;"));
        }
    }
}
