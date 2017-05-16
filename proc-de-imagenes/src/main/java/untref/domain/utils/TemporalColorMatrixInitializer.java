package untref.domain.utils;

import untref.domain.TemporalColor;

public class TemporalColorMatrixInitializer {

	public TemporalColor[][] initialize(int width, int height, int initialValue) {
		TemporalColor temporalColorMatrix[][] = new TemporalColor[height][width];

		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				temporalColorMatrix[row][column] = new TemporalColor(initialValue, initialValue, initialValue);
			}
		}

		return temporalColorMatrix;
	}
}