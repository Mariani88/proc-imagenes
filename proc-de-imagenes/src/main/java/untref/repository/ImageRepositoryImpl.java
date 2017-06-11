package untref.repository;

import ij.ImagePlus;
import ij.io.Opener;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import untref.controllers.RawImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import static untref.domain.utils.ImageValuesTransformer.toInt;

public class ImageRepositoryImpl implements ImageRepository {

	@Override
	public Image findImage(File file, RawImage rawImage) {
		if (!getFileExtension(file).equalsIgnoreCase("RAW")) {
			return findImageWithFormat(file);
		} else {
			return findImageRaw(file, rawImage);
		}
	}

	@Override
	public Image findImageWithFormat(File file) {
		BufferedImage bufferedImage;
		Opener opener = new Opener();
		Path path = file.toPath();
		ImagePlus img = opener.openImage(path.toString());
		bufferedImage = img.getBufferedImage();
		return SwingFXUtils.toFXImage(bufferedImage, null);
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

	private Image findImageRaw(File file, RawImage rawImage) {
		Imagen imagenRawOut;
		BufferedImage bufferedImage;
		ImageRaw imageRaw = new ImageRaw();
		imagenRawOut = imageRaw.cargarUnaImagenRawDesdeArchivo(rawImage.getValueFieldAncho(), rawImage.getValueFieldAlto(), file.toPath().toString
				());
		bufferedImage = imagenRawOut.getBufferedImage();
		return SwingFXUtils.toFXImage(bufferedImage, null);
	}

	private BufferedImage findBufferedImageWithFormat(File file) {
		BufferedImage bufferedImage;
		Opener opener = new Opener();

		Path path = file.toPath();

		ImagePlus img = opener.openImage(path.toString());

		bufferedImage = img.getBufferedImage();
		return bufferedImage;
	}

	private static String getFileExtension(File file) {
		String fileName = file.getName();
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		else
			return "";
	}
}