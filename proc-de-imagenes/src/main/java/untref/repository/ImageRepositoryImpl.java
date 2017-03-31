package untref.repository;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

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
}