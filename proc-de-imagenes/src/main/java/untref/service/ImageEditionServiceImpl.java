package untref.service;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class ImageEditionServiceImpl implements ImageEditionService {

	@Override
	public Image modifyPixelValue(Image image, String aX, String aY, String pixelValue) {
		PixelReader pixelReader = image.getPixelReader();
		int width = (int) image.getWidth();
		int height = (int) image.getHeight();
		WritableImage writableImage = new WritableImage(width, height);
		PixelWriter pixelWriter = writableImage.getPixelWriter();

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				pixelWriter.setColor(x, y, pixelReader.getColor(x, y));
			}
		}

		int x = toInt(aX);
		int y = toInt(aY);
		pixelWriter.setArgb(x, y, toInt(pixelValue));
		return writableImage;
	}

	private int toInt(String text) {
		return Integer.parseInt(text);
	}
}