package com.nhl.command_pattern;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;

import com.nhl.observer_pattern.Presentation;
import com.nhl.Slide;
import com.nhl.XMLAccessor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.List;

public class NewCommand implements Command
{
    private final Presentation presentation;
    private final Frame parent;
    private List<Slide> previousSlides;
    private String previousTitle;

    public NewCommand(Presentation presentation, Frame parent)
    {
        this.presentation = presentation;
        this.parent = parent;
    }

    @Override
    public boolean execute()
    {
        this.storeCurrentState();

        int choice = JOptionPane.showOptionDialog(
                this.parent,
                "Do you want to create a new blank presentation or open an existing one?",
                "New Presentation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[] {"Blank", "Open File"},
                "Blank"
        );

        if (choice == 0)
        {
            this.presentation.clear();
            this.presentation.setTitle("New Presentation");
            this.presentation.append(new Slide());
            this.parent.repaint();

            return true;
        }
        else if (choice == 1)
        {
            FileDialog fileDialog = new FileDialog(this.parent, "Open Presentation", FileDialog.LOAD);
            fileDialog.setVisible(true);

            String fileDir = fileDialog.getDirectory();
            String fileName = fileDialog.getFile();

            if (fileDir != null && fileName != null)
            {
                try
                {
                    File file = new File(fileDir, fileName);
                    this.loadPresentationFromXML(file);
                    return true;
                }
                catch (Exception e)
                {
                    JOptionPane.showMessageDialog(this.parent,
                            "Error loading file: " + e.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);

                    return false;
                }
            }
        }

        return false;
    }

    @Override
    public boolean undo()
    {
        if (this.previousSlides != null)
        {
            this.presentation.clear();
            this.presentation.setTitle(this.previousTitle);
            for (Slide slide : this.previousSlides)
            {
                this.presentation.append(slide);
            }
            this.parent.repaint();
            return true;
        }
        return false;
    }

    @Override
    public String getDescription()
    {
        return "New Presentation";
    }

    private void storeCurrentState()
    {
        this.previousTitle = this.presentation.getTitle();
        this.previousSlides = new ArrayList<>();
        for (int i = 0; i < this.presentation.getSize(); i++)
        {
            this.previousSlides.add(this.presentation.getSlide(i));
        }
    }

    public void loadPresentationFromXML(File file) throws Exception
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

            this.presentation.clear();

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

                this.presentation.append(slide);
                this.parent.repaint();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.parent, "Error parsing the XML: " + e.getMessage());
            throw e;
        }
    }
}
