package com.nhl;

import com.nhl.observer_pattern.Presentation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class XMLAccessorTest
{
    private XMLAccessor accessor;
    private Presentation presentation;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp()
    {
        accessor = new XMLAccessor();
        presentation = new Presentation();
    }

    @Test
    void testLoadFile() throws Exception
    {
        File xmlFile = tempDir.resolve("test.xml").toFile();

        xmlFile.getParentFile().mkdirs();

        try (FileWriter writer = new FileWriter(xmlFile))
        {
            writer.write("<?xml version=\"1.0\"?>\n");
            writer.write("<presentation>\n");
            writer.write("  <showtitle>Test Presentation</showtitle>\n");
            writer.write("  <slide>\n");
            writer.write("    <title>Test Slide</title>\n");
            writer.write("    <item kind=\"text\" level=\"1\">Test Item</item>\n");
            writer.write("  </slide>\n");
            writer.write("</presentation>");
        }

        assertTrue(xmlFile.exists(), "XML file should exist");
        assertTrue(xmlFile.length() > 0, "XML file should not be empty");

        accessor.loadFile(presentation, xmlFile.getAbsolutePath());

        assertEquals("Test Presentation", presentation.getTitle(), "Presentation title should match");
        assertEquals(1, presentation.getSize(), "Presentation should have 1 slide");
        assertEquals("Test Slide", presentation.getSlide(0).getTitle(), "Slide title should match");
        assertEquals(1, presentation.getSlide(0).getSlideItems().size(), "Slide should have 1 item");
        assertInstanceOf(TextItem.class, presentation.getSlide(0).getSlideItems().get(0), "Slide item should be a TextItem");

        TextItem textItem = (TextItem) presentation.getSlide(0).getSlideItems().get(0);

        assertEquals("Test Item", textItem.getText(), "Text item content should match");
        assertEquals(1, textItem.getLevel(), "Text item level should be 1");
    }

    @Test
    void saveFile_whenCalled_shouldSavePresentationToXMLFile() throws Exception
    {
        presentation.setTitle("Test Presentation");

        Slide slide = new Slide();

        slide.setTitle("Test Slide");
        slide.append(1, "Test Item");
        presentation.append(slide);

        File xmlFile = tempDir.resolve("test.xml").toFile();

        accessor.saveFile(presentation, xmlFile.getAbsolutePath());

        assertTrue(xmlFile.exists());
        assertTrue(xmlFile.length() > 0);

        String content = Files.readString(xmlFile.toPath());

        assertTrue(content.contains("<showtitle>Test Presentation</showtitle>"));
        assertTrue(content.contains("<title>Test Slide</title>"));
        assertTrue(content.contains("<item kind=\"text\" level=\"1\">Test Item</item>"));
    }

    @Test
    void loadFile_whenFileNotFound_shouldThrowIOException()
    {
        assertThrows(IOException.class, () ->
        {
            accessor.loadFile(presentation, "nonexistent.xml");
        });
    }

    @Test
    void loadFile_whenFileContainsInvalidXML_shouldThrowIOException() throws Exception
    {
        File xmlFile = tempDir.resolve("invalid.xml").toFile();

        try (FileWriter writer = new FileWriter(xmlFile))
        {
            writer.write("This is not valid XML");
        }

        assertThrows(IOException.class, () ->
        {
            accessor.loadFile(presentation, xmlFile.getAbsolutePath());
        });
    }

    @Test
    void saveFile_whenNoPermission_shouldThrowIOException()
    {
        assertThrows(IOException.class, () ->
        {
            accessor.saveFile(presentation, "/root/test.xml");
        });
    }

    @Test
    void loadFile_whenValidXMLProvided_shouldLoadPresentation() throws Exception
    {
        File xmlFile = tempDir.resolve("test.xml").toFile();

        xmlFile.getParentFile().mkdirs();

        try (FileWriter writer = new FileWriter(xmlFile))
        {
            writer.write("<?xml version=\"1.0\"?>\n");
            writer.write("<presentation>\n");
            writer.write("  <showtitle>Test Presentation</showtitle>\n");
            writer.write("  <slide>\n");
            writer.write("    <title>Test Slide</title>\n");
            writer.write("    <item kind=\"text\" level=\"1\">Test Item</item>\n");
            writer.write("  </slide>\n");
            writer.write("</presentation>");
        }

        assertTrue(xmlFile.exists(), "XML file should exist");
        assertTrue(xmlFile.length() > 0, "XML file should not be empty");

        accessor.loadFile(presentation, xmlFile.getAbsolutePath());

        assertEquals("Test Presentation", presentation.getTitle(), "Presentation title should match");
        assertEquals(1, presentation.getSize(), "Presentation should have 1 slide");
        assertEquals("Test Slide", presentation.getSlide(0).getTitle(), "Slide title should match");
        assertEquals(1, presentation.getSlide(0).getSlideItems().size(), "Slide should have 1 item");
        assertInstanceOf(TextItem.class, presentation.getSlide(0).getSlideItems().get(0), "Slide item should be a TextItem");

        TextItem textItem = (TextItem) presentation.getSlide(0).getSlideItems().get(0);

        assertEquals("Test Item", textItem.getText(), "Text item content should match");
        assertEquals(1, textItem.getLevel(), "Text item level should be 1");
    }
} 