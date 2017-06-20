package untref.domain.utils;

public class MatrixUtils {

	public static double getPositionOrEmpty(double[][] matrix, int row, int column) {
		if (existPosition(matrix, row, column)) {
			return matrix[row][column];
		}

		return 0;
	}

	private static boolean existPosition(double[][] matrix, int row, int column) {
		return contains(matrix.length, row) && contains(matrix[0].length, column);
	}

	private static boolean contains(int length, int value) {
		return 0 < value && value < length;
	}
}