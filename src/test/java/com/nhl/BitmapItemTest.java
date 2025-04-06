package com.nhl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.MockedStatic;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BitmapItemTest
{
    private BitmapItem bitmapItem;
    private static final String TEST_IMAGE_NAME = "test-image.jpg";

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp()
    {
        try (MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class))
        {
            bitmapItem = new BitmapItem(1, TEST_IMAGE_NAME);
        }
    }

    @Test
    void constructor_whenCalled_shouldInitializeWithLevelAndName()
    {
        try (MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class))
        {
            BitmapItem item = new BitmapItem(1, TEST_IMAGE_NAME);
            assertEquals(1, item.getLevel());
            assertEquals(TEST_IMAGE_NAME, item.getName());
        }
    }

    @Test
    void getName_whenCalled_shouldReturnImageName()
    {
        assertEquals(TEST_IMAGE_NAME, bitmapItem.getName());
    }

    @Test
    void setName_whenCalled_shouldUpdateImageNameAndReloadImage()
    {
        try (MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class))
        {
            String newImageName = "new-image.jpg";
            bitmapItem.setName(newImageName);
            assertEquals(newImageName, bitmapItem.getName());
        }
    }

    @Test
    void getBufferedImage_whenImageNotLoaded_shouldReturnNull()
    {
        assertNull(bitmapItem.getBufferedImage());
    }

    @Test
    void setBufferedImage_whenCalled_shouldUpdateBufferedImage()
    {
        BufferedImage mockImage = mock(BufferedImage.class);
        bitmapItem.setBufferedImage(mockImage);
        assertEquals(mockImage, bitmapItem.getBufferedImage());
    }

    @Test
    void getBoundingBox_whenImageIsNull_shouldReturnEmptyRectangle()
    {
        Graphics g = mock(Graphics.class);
        ImageObserver observer = mock(ImageObserver.class);
        Style style = mock(Style.class);

        Rectangle boundingBox = bitmapItem.getBoundingBox(g, observer, 1.0f, style);

        assertEquals(0, boundingBox.width);
        assertEquals(0, boundingBox.height);
    }

    @Test
    void getBoundingBox_whenImageExists_shouldReturnCorrectRectangle()
    {
        Graphics g = mock(Graphics.class);
        ImageObserver observer = mock(ImageObserver.class);
        Style style = mock(Style.class);
        BufferedImage mockImage = mock(BufferedImage.class);

        when(style.getIndent()).thenReturn(10);
        when(style.getLeading()).thenReturn(20);
        when(mockImage.getWidth(observer)).thenReturn(100);
        when(mockImage.getHeight(observer)).thenReturn(50);

        bitmapItem.setBufferedImage(mockImage);

        Rectangle boundingBox = bitmapItem.getBoundingBox(g, observer, 1.0f, style);

        assertEquals(10, boundingBox.x);
        assertEquals(0, boundingBox.y);
        assertEquals(100, boundingBox.width);
        assertEquals(70, boundingBox.height);
    }

    @Test
    void draw_whenImageIsNull_shouldNotDraw()
    {
        Graphics g = mock(Graphics.class);
        ImageObserver observer = mock(ImageObserver.class);
        Style style = mock(Style.class);

        bitmapItem.draw(0, 0, 1.0f, g, style, observer);

        verify(g, never()).drawImage(any(), anyInt(), anyInt(), anyInt(), anyInt(), any());
    }

    @Test
    void draw_whenImageExists_shouldDrawImage()
    {
        Graphics g = mock(Graphics.class);
        ImageObserver observer = mock(ImageObserver.class);
        Style style = mock(Style.class);
        BufferedImage mockImage = mock(BufferedImage.class);

        when(style.getIndent()).thenReturn(10);
        when(style.getLeading()).thenReturn(20);
        when(mockImage.getWidth(observer)).thenReturn(100);
        when(mockImage.getHeight(observer)).thenReturn(50);

        bitmapItem.setBufferedImage(mockImage);

        bitmapItem.draw(0, 0, 1.0f, g, style, observer);

        verify(g).drawImage(eq(mockImage), eq(10), eq(20), eq(100), eq(50), eq(observer));
    }

    @Test
    void toString_whenCalled_shouldReturnFormattedString()
    {
        String expected = String.format("BitmapItem[level=%d, image='%s']", 1, TEST_IMAGE_NAME);
        assertEquals(expected, bitmapItem.toString());
    }

    @Test
    void loadBufferedImage_whenImageExists_shouldLoadImage() throws IOException
    {
        try (MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class))
        {
            File imageFile = tempDir.resolve(TEST_IMAGE_NAME).toFile();
            BufferedImage testImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
            ImageIO.write(testImage, "jpg", imageFile);

            BitmapItem item = new BitmapItem(1, imageFile.getAbsolutePath());

            assertNotNull(item.getBufferedImage());
        }
    }

    @Test
    void loadBufferedImage_whenImageDoesNotExist_shouldShowErrorDialog()
    {
        try (MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class))
        {
            BitmapItem item = new BitmapItem(1, "non-existent-image.jpg");

            mockedJOptionPane.verify(() ->
                    JOptionPane.showMessageDialog(null, "Image was not found", "Image error", JOptionPane.ERROR_MESSAGE)
            );

            assertNull(item.getBufferedImage());
        }
    }

    @Test
    void loadBufferedImage_whenImageNameIsNull_shouldNotAttemptToLoadImage()
    {
        try (MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class))
        {
            BitmapItem item = new BitmapItem(1, null);

            assertNull(item.getBufferedImage());
            assertNull(item.getName());
            mockedJOptionPane.verifyNoInteractions();
        }
    }

    @Test
    void setName_whenSetToNull_shouldUpdateImageNameAndNotAttemptToLoadImage()
    {
        try (MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class))
        {
            bitmapItem.setName(null);

            assertNull(bitmapItem.getName());
            assertNull(bitmapItem.getBufferedImage());
            mockedJOptionPane.verifyNoInteractions();
        }
    }
} 