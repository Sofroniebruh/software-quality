package com.nhl;

import com.nhl.command_pattern.Command;
import com.nhl.observer_pattern.Presentation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.awt.*;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;

public class OpenCommand implements Command
{
    private Presentation presentation;
    private Frame parent;
    public static final String TESTFILE = "test.xml";
    public static final String IOEX = "IO Exception: ";
    public static final String LOADERR = "Load Error";

    public OpenCommand(Presentation presentation, Frame parent)
    {
        this.presentation = presentation;
        this.parent = parent;
    }

    public Presentation getPresentation()
    {
        return presentation;
    }

    public void setPresentation(Presentation presentation)
    {
        this.presentation = presentation;
    }

    public Frame getParent()
    {
        return parent;
    }

    public void setParent(Frame parent)
    {
        this.parent = parent;
    }

    @Override
    public void execute()
    {
        FileDialog fileDialog = new FileDialog(parent, "Open Presentation", FileDialog.LOAD);
        fileDialog.setVisible(true);

        String fileDir = fileDialog.getDirectory();
        String fileName = fileDialog.getFile();

        if (fileDir != null && fileName != null)
        {
            try
            {
                File file = new File(fileDir, fileName);
                loadPresentationFromXML(file);
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(parent, "Error loading file: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        parent.repaint();
    }

    private void loadPresentationFromXML(File file) throws Exception
    {
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            document.getDocumentElement().normalize();

            Element root = document.getDocumentElement();
            if (!root.getNodeName().equals("presentation"))
            {
                throw new IOException("Invalid XML format: Root element should be <presentation>");
            }

            presentation.clear();

            NodeList slideNodes = root.getElementsByTagName("slide");

            for (int i = 0; i < slideNodes.getLength(); i++)
            {
                Element slideElement = (Element) slideNodes.item(i);
                Slide slide = new Slide();
                slide.setTitle(slideElement.getAttribute("title"));

                NodeList itemNodes = slideElement.getElementsByTagName("item");
                XMLAccessor xmlAccessor = new XMLAccessor();
                for (int j = 0; j < itemNodes.getLength(); j++)
                {
                    Element itemElement = (Element) itemNodes.item(j);
                    xmlAccessor.loadSlideItem(slide, itemElement);
                }

                presentation.append(slide);
                parent.repaint();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parent, "Error parsing the XML: " + e.getMessage());
        }
    }

    @Override
    public void undo()
    {
        // No undo available.
    }
}
