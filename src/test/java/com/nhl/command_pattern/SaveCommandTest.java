package com.nhl.command_pattern;

import com.nhl.Accessor;
import com.nhl.observer_pattern.Presentation;
import com.nhl.XMLAccessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.JOptionPane;
import java.awt.Frame;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaveCommandTest
{

    @Mock
    private Presentation mockPresentation;

    @Mock
    private Frame mockParent;

    @Mock
    private XMLAccessor mockXmlAccessor;

    @Mock
    private Path mockPath;

    private SaveCommand saveCommand;

    @BeforeEach
    void setUp()
    {
        saveCommand = new SaveCommand(mockPresentation, mockParent, mockXmlAccessor);
    }

    @Test
    void execute_whenUserProvidesFilename_shouldSaveSuccessfully() throws IOException
    {
        try (MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class);
             MockedStatic<Files> mockedFiles = mockStatic(Files.class))
        {
            String filename = "test";
            mockedJOptionPane.when(() -> JOptionPane.showInputDialog(anyString())).thenReturn(filename);
            mockedFiles.when(() -> Files.readAllBytes(any())).thenReturn("previous content".getBytes());
            mockedFiles.when(() -> Files.write(any(), (byte[]) any())).thenReturn(mockPath);

            boolean result = saveCommand.execute();

            assertTrue(result);
            verify(mockXmlAccessor).saveFile(mockPresentation, filename + ".xml");
        }
    }

    @Test
    void execute_whenUserCancels_shouldReturnFalse()
    {
        try (MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class))
        {
            mockedJOptionPane.when(() -> JOptionPane.showInputDialog(anyString())).thenReturn(null);

            boolean result = saveCommand.execute();

            assertFalse(result);
        }
    }

    @Test
    void execute_whenIOExceptionOccurs_shouldShowErrorAndReturnFalse() throws IOException
    {
        try (MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class))
        {
            String filename = "test";

            mockedJOptionPane.when(() -> JOptionPane.showInputDialog(anyString())).thenReturn(filename);
            doThrow(new IOException("Test error")).when(mockXmlAccessor).saveFile(any(), anyString());

            boolean result = saveCommand.execute();

            assertFalse(result);
            mockedJOptionPane.verify(() -> JOptionPane.showMessageDialog(
                    any(Frame.class),
                    anyString(),
                    anyString(),
                    anyInt()
            ));
        }
    }

    @Test
    void undo_whenPreviousContentExists_shouldRestoreFile() throws IOException
    {
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class))
        {
            String previousContent = "previous content";
            String savedFilename = "test.xml";

            java.lang.reflect.Field previousContentField = SaveCommand.class.getDeclaredField("previousContent");
            java.lang.reflect.Field savedFilenameField = SaveCommand.class.getDeclaredField("savedFilename");

            previousContentField.setAccessible(true);
            savedFilenameField.setAccessible(true);
            previousContentField.set(saveCommand, previousContent);
            savedFilenameField.set(saveCommand, savedFilename);
            mockedFiles.when(() -> Files.write(any(), (byte[]) any())).thenReturn(mockPath);

            boolean result = saveCommand.undo();

            assertTrue(result);
            mockedFiles.verify(() -> Files.write(any(), eq(previousContent.getBytes())));
        }
        catch (Exception e)
        {
            fail("Test failed with exception: " + e.getMessage());
        }
    }

    @Test
    void undo_whenIOExceptionOccurs_shouldShowErrorAndReturnFalse() throws IOException
    {
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class);
             MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class))
        {
            String previousContent = "previous content";
            String savedFilename = "test.xml";

            java.lang.reflect.Field previousContentField = SaveCommand.class.getDeclaredField("previousContent");
            java.lang.reflect.Field savedFilenameField = SaveCommand.class.getDeclaredField("savedFilename");

            previousContentField.setAccessible(true);
            savedFilenameField.setAccessible(true);
            previousContentField.set(saveCommand, previousContent);
            savedFilenameField.set(saveCommand, savedFilename);
            mockedFiles.when(() -> Files.write(any(), (byte[]) any())).thenThrow(new IOException("Test error"));

            boolean result = saveCommand.undo();

            assertFalse(result);
            mockedJOptionPane.verify(() -> JOptionPane.showMessageDialog(
                    any(Frame.class),
                    anyString(),
                    anyString(),
                    anyInt()
            ));
        }
        catch (Exception e)
        {
            fail("Test failed with exception: " + e.getMessage());
        }
    }

    @Test
    void undo_whenNoPreviousContent_shouldReturnFalse()
    {
        boolean result = saveCommand.undo();
        assertFalse(result);
    }

    @Test
    void getDescription_shouldReturnCorrectDescription()
    {
        assertEquals("Save Presentation", saveCommand.getDescription());
    }
} 