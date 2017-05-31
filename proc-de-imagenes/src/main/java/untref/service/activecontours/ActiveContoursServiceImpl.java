package untref.service.activecontours;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import untref.domain.ImagePosition;

import static untref.domain.utils.ImageValuesTransformer.toInt;

public class ActiveContoursServiceImpl implements ActiveContoursService {

	@Override
	public Image initializeActiveContours(Image image, ImagePosition imagePosition, ImagePosition imagePosition2) {
		WritableImage imageWithContours = replicateImage(image);
		paintExternalContours(imageWithContours, imagePosition, imagePosition2);

		return imageWithContours;
	}

	private void paintExternalContours(WritableImage imageWithContours, ImagePosition imagePosition, ImagePosition imagePosition2) {
		int firstRow = Math.min(imagePosition.getRow(), imagePosition2.getRow());
		int secondRow = Math.max(imagePosition.getRow(), imagePosition2.getRow());
		int firstColumn = Math.min(imagePosition.getColumn(), imagePosition2.getColumn());
		int secondColumn = Math.max(imagePosition.getColumn(), imagePosition2.getColumn());
		PixelWriter pixelWriter = imageWithContours.getPixelWriter();

		for (int index = firstRow; index <= secondRow; index++) {
			pixelWriter.setColor(firstColumn, index, Color.BLUE);
			pixelWriter.setColor(secondColumn, index, Color.BLUE);
		}

		for (int index = firstColumn; index <= secondColumn; index++) {
			pixelWriter.setColor(index, firstRow, Color.BLUE);
			pixelWriter.setColor(index, secondRow, Color.BLUE);
		}

		for (int index = firstRow + 1; index <= secondRow - 1; index++) {
			pixelWriter.setColor(firstColumn + 1, index, Color.RED);
			pixelWriter.setColor(secondColumn - 1, index, Color.RED);
		}

		for (int index = firstColumn + 1; index <= secondColumn - 1; index++) {
			pixelWriter.setColor(index, firstRow + 1, Color.RED);
			pixelWriter.setColor(index, secondRow - 1, Color.RED);
		}

	}

	private WritableImage replicateImage(Image image) {
		int width = toInt(image.getWidth());
		int height = toInt(image.getHeight());
		WritableImage writableImage = new WritableImage(width, height);
		PixelReader pixelReader = image.getPixelReader();
		PixelWriter pixelWriter = writableImage.getPixelWriter();

		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				pixelWriter.setColor(column, row, pixelReader.getColor(column, row));
			}
		}

		return writableImage;
	}
}