package untref.service;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import untref.figures.CenterCircle;
import untref.figures.CenterQuadrate;

import java.util.function.Consumer;

import static javafx.scene.paint.Color.BLACK;

public class CreationImageServiceImpl implements CreationImageService {

	@Override
	public Image createBinaryImageWithCenterQuadrate(int width, int height) {
		return creationWithCenterFigure(width, height, pixelWriter -> new CenterQuadrate().create(width, height, pixelWriter));
	}

	@Override
	public Image createBinaryImageWithCenterCircle(int width, int height) {
		return creationWithCenterFigure(width, height, pixelWriter -> new CenterCircle().create(width, height, pixelWriter));
	}

	@Override
	public Image createImageWithGrayDegree(int width, int height) {
		WritableImage writableImage = new WritableImage(width, height);
		PixelWriter pixelWriter = writableImage.getPixelWriter();
		double offset = 10;
		double grayColor = -offset;

		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {

				if (column % offset == 0) {
					grayColor += offset;
				}

				pixelWriter.setColor(column, row, Color.gray(grayColor / 255));
			}

			grayColor = - offset;
		}

		return writableImage;
	}

	private WritableImage creationWithCenterFigure(int width, int height, Consumer<PixelWriter> creationFigure) {
		WritableImage writableImage = new WritableImage(width, height);
		PixelWriter pixelWriter = writableImage.getPixelWriter();
		createBlackImage(width, height, pixelWriter);
		creationFigure.accept(pixelWriter);
		return writableImage;
	}

	private void createBlackImage(int width, int height, PixelWriter pixelWriter) {
		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				pixelWriter.setColor(column, row, BLACK);
			}
		}
	}

	@Override
	public Image createImageWithColorDegree(int width, int height) {
		WritableImage writableImage = new WritableImage(width, height);
		PixelWriter pixelWriter = writableImage.getPixelWriter();
		 Color colorRGB;

		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {

			 colorRGB=Color.rgb(255-column ,row ,column );
				pixelWriter.setColor(column, row, colorRGB);
			}

			 
		}

		return writableImage;
	}
}