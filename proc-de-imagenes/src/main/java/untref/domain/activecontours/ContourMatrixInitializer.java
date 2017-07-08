package untref.domain.activecontours;

import javafx.scene.image.Image;
import untref.domain.ImagePosition;

import java.util.List;

import static untref.domain.activecontours.ContourObjectElement.*;
import static untref.domain.utils.ImageValuesTransformer.toInt;

public class ContourMatrixInitializer {

	public int[][] initializeMatrix(int fromRowObject, int fromColumnObject, int toRowObject, int toColumnObject, Image originalImage,
			List<ImagePosition> lIn, List<ImagePosition> lOut) {
		int height = toInt(originalImage.getHeight());
		int width = toInt(originalImage.getWidth());
		int matrix[][] = new int[height][width];

		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				matrix[row][column] = BACKGROUND;
			}
		}

		setWithEdges(lIn, lOut, matrix);
		setWithObject(fromRowObject, fromColumnObject, toRowObject, toColumnObject, matrix, OBJECT);
		return matrix;
	}

	private void setWithObject(int fromRowObject, int fromColumnObject, int toRowObject, int toColumnObject, int[][] matrix, int object) {
		for (int row = fromRowObject; row <= toRowObject; row++) {
			for (int column = fromColumnObject; column <= toColumnObject; column++) {
				matrix[row][column] = object;
			}
		}
	}

	private void setWithEdges(List<ImagePosition> lIn, List<ImagePosition> lOut, int matrix[][]) {
		lIn.forEach(imagePosition -> matrix[imagePosition.getRow()][imagePosition.getColumn()] = L_IN);
		lOut.forEach(imagePosition -> matrix[imagePosition.getRow()][imagePosition.getColumn()] = L_OUT);
	}
}