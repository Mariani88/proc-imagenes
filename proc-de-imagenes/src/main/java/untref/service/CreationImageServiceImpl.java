package untref.service;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.WHITE;

public class CreationImageServiceImpl implements CreationImageService {

	@Override
	public Image createBinaryImageWithCenterQuadrate(int width, int height) {
		WritableImage writableImage = new WritableImage(width, height);
		PixelWriter pixelWriter = writableImage.getPixelWriter();
		createBlankImage(width, height, pixelWriter);
		createCenterQuadrate(width, height, pixelWriter);
		return writableImage;
	}

	private void createBlankImage(int width, int height, PixelWriter pixelWriter) {
		for (int row = 0; row < width; row++) {
			for (int column = 0; column < height; column++) {
				pixelWriter.setColor(row, column, WHITE);
			}
		}
	}

	private void createCenterQuadrate(int width, int height, PixelWriter pixelWriter) {
		for (int row = width / 3; row <= width * 2 / 3; row++) {
			for (int column = height / 3; column <= height * 2 / 3; column++) {
				pixelWriter.setColor(row, column, BLACK);
			}
		}
	}
}