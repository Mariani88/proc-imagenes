package untref.utils;

import javafx.scene.image.Image;

import static untref.domain.utils.ImageValuesTransformer.toInt;

public class ImageValidator {

	public static boolean existPosition(Image image, int row, int column) {
		boolean columnIsValid = column < toInt(image.getWidth()) && 0 <= column;
		boolean rowIsValid = row < toInt(image.getHeight()) && 0 <= row;
		return columnIsValid && rowIsValid;
	}
}