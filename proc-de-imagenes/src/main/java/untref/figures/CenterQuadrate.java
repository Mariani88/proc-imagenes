package untref.figures;

import javafx.scene.image.PixelWriter;

import static javafx.scene.paint.Color.WHITE;

public class CenterQuadrate implements ImageFigure {

	@Override
	public void create(int width, int height, PixelWriter pixelWriter) {
		for (int row = height / 3; row <= height * 2 / 3; row++) {
			for (int column = width / 3; column <= width * 2 / 3; column++) {
				pixelWriter.setColor(column, row, WHITE);
			}
		}
	}
}