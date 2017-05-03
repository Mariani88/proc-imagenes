package untref.service.evaluators;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class ImagePositionEvaluator {

	public Color obtainSumsColor(Image image, int row, int column) {
		Color color = Color.rgb(0, 0, 0);

		if (imageContainsPosition(image, row, column)) {
			color = image.getPixelReader().getColor(column, row);
		}

		return color;
	}

	private boolean imageContainsPosition(Image image, int row, int column) {
		return row < image.getHeight() && column < image.getWidth();
	}

}