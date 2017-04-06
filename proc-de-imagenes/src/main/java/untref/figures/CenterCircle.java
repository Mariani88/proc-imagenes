package untref.figures;

import javafx.scene.image.PixelWriter;
import javafx.scene.shape.Circle;

import static javafx.scene.paint.Color.WHITE;

public class CenterCircle implements ImageFigure {

	@Override
	public void create(int width, int height, PixelWriter pixelWriter) {
		int centerColumn = width / 2;
		int centerRow = height / 2;
		Circle circle = new Circle(centerRow, centerColumn, height / 6);

		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				if (circle.contains(row, column)) {
					pixelWriter.setColor(column, row, WHITE);
				}
			}
		}
	}
}
