package com.nhl;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;

public class BitmapItem extends SlideItem
{
    private BufferedImage bufferedImage;
    private String imageName;
    protected static final String FILE = "File ";
    protected static final String NOTFOUND = " not found";

    public BitmapItem(int level, String name)
    {
        super(level);
        this.imageName = name;
        loadBufferedImage();
    }

    private void loadBufferedImage()
    {
        if (this.imageName != null)
        {
            try
            {
                this.bufferedImage = ImageIO.read(new File(this.imageName));
            }
            catch (IOException e)
            {
                System.err.println(FILE + this.imageName + NOTFOUND);
                this.bufferedImage = null;
                JOptionPane.showMessageDialog(null, "Image was not found", "Image error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public String getName()
    {
        return imageName;
    }

    public void setName(String imageName)
    {
        this.imageName = imageName;
        loadBufferedImage();
    }

    public BufferedImage getBufferedImage()
    {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage)
    {
        this.bufferedImage = bufferedImage;
    }

    public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style myStyle)
    {
        if (bufferedImage == null)
        {
            return new Rectangle(0, 0, 0, 0);
        }

        return new Rectangle(
                (int) (myStyle.indent * scale),
                0,
                (int) (bufferedImage.getWidth(observer) * scale),
                ((int) (myStyle.leading * scale)) + (int) (bufferedImage.getHeight(observer) * scale)
        );
    }

    public void draw(int x, int y, float scale, Graphics g, Style myStyle, ImageObserver observer)
    {
        if (bufferedImage == null)
        {
            System.err.println("Cannot draw: Image is null.");
            return;
        }
        int width = x + (int) (myStyle.indent * scale);
        int height = y + (int) (myStyle.leading * scale);
        g.drawImage(
                bufferedImage,
                width,
                height,
                (int) (bufferedImage.getWidth(observer) * scale),
                (int) (bufferedImage.getHeight(observer) * scale),
                observer
        );
    }

    @Override
    public String toString()
    {
        return String.format("BitmapItem[level=%d, image='%s']", getLevel(), imageName);
    }
}
