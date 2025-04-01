import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class BitmapItem extends SlideItem {
	private BufferedImage bufferedImage;
	private String imageName;
	protected static final String FILE = "File ";
	protected static final String NOTFOUND = " not found";

	public BitmapItem(int level, String name) {
		super(level);
		this.imageName = name;
		loadBufferedImage();
	}

	public BitmapItem() {
		this(0, null);
	}

	private void loadBufferedImage() {
		if (this.imageName != null) {
			try {
				this.bufferedImage = ImageIO.read(new File(this.imageName));
			} catch (IOException e) {
				System.err.println(FILE + this.imageName + NOTFOUND);
				this.bufferedImage = null;
			}
		}
	}

	public String getName() {
		return imageName;
	}

	public void setName(String imageName) {
		this.imageName = imageName;
		loadBufferedImage();
	}

	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}

	public void setBufferedImage(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}

	public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style myStyle) {
		return new Rectangle(
				(int) (myStyle.indent * scale),
				0,
				(int) (bufferedImage.getWidth(observer) * scale),
				((int) (myStyle.leading * scale)) + (int) (bufferedImage.getHeight(observer) * scale)
		);
	}

	public void draw(int x, int y, float scale, Graphics g, Style myStyle, ImageObserver observer) {
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

	public String toString() {
		return "BitmapItem[" + getLevel() + "," + imageName + "]";
	}
}
