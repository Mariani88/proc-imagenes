package untref.repository;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageRepositoryImpl implements ImageRepository {

	@Override
	public Image findImage(File file) {

		try {
			BufferedImage bufferedImage = ImageIO.read(file);
			return SwingFXUtils.toFXImage(bufferedImage, null);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void storeImage(Image image, File file) {
		if (file != null) {
			try {
				BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);

				// Remove alpha-channel from buffered image:
				BufferedImage imageRGB = new BufferedImage(toInt(image.getWidth()), toInt(image.getHeight()), BufferedImage.OPAQUE);
				Graphics2D graphics = imageRGB.createGraphics();
				graphics.drawImage(bufferedImage, 0, 0, null);
				ImageIO.write(imageRGB, "jpg", file);
				graphics.dispose();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private int toInt(double value) {
		return (int) value;
	}

}