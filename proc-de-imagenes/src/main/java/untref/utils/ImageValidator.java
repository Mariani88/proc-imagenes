package untref.utils;

import javafx.scene.image.Image;
import untref.domain.ImagePosition;
import untref.domain.TemporalColor;

import static untref.domain.utils.ImageValuesTransformer.toInt;

public class ImageValidator {

	public static boolean existPosition(Image image, int row, int column) {
		boolean columnIsValid = column < toInt(image.getWidth()) && 0 <= column;
		boolean rowIsValid = row < toInt(image.getHeight()) && 0 <= row;
		return columnIsValid && rowIsValid;
	}

	public static boolean existPosition(TemporalColor[][] temporalColors, int row, int column) {
		boolean columnIsValid = column < toInt(temporalColors[0].length) && 0 <= column;
		boolean rowIsValid = row < toInt(temporalColors.length) && 0 <= row;
		return columnIsValid && rowIsValid;
	}

	public static boolean existPosition(Image image, ImagePosition imagePosition) {
		int row = imagePosition.getRow();
		int column = imagePosition.getColumn();
		return existPosition(image, row, column);
	}
}