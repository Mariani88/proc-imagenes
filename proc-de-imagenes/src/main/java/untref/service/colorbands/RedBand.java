package untref.service.colorbands;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class RedBand implements SpecificBand {

	@Override
	public Image createWithBand(Image image) {
		int width = (int) image.getWidth();
		int height = (int) image.getHeight();
		PixelReader pixelReader = image.getPixelReader();
		WritableImage writableImage = new WritableImage(width, height);
		PixelWriter pixelWriter = writableImage.getPixelWriter();
		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				double red = pixelReader.getColor(column, row).getRed();
				pixelWriter.setColor(column, row, Color.color(red, 0, 0));
			}
		}

		return writableImage;
	}
}
