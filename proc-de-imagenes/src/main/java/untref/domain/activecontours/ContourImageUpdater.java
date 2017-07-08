package untref.domain.activecontours;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import static untref.domain.utils.ImageValuesTransformer.toInt;

public class ContourImageUpdater {

	public WritableImage updateImage(Image originalImage, int lIn, int lOut, int matrix[][]) {
		int width = toInt(originalImage.getWidth());
		int height = toInt(originalImage.getHeight());
		WritableImage writableImage = new WritableImage(width, height);
		PixelWriter pixelWriter = writableImage.getPixelWriter();
		PixelReader pixelReader = originalImage.getPixelReader();

		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				if (matrix[row][column] == lOut) {
					pixelWriter.setColor(column, row, Color.BLUE);
				} else if (matrix[row][column] == lIn) {
					pixelWriter.setColor(column, row, Color.RED);
				} else {
					pixelWriter.setColor(column, row, pixelReader.getColor(column, row));
				}
			}
		}

		return writableImage;
	}
}
