package untref.domain.edgedetectionoperators.secondderivative.detectors;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import untref.domain.TemporalColor;

import static untref.domain.utils.ImageValuesTransformer.getOrEmpty;

public class CrossByZeroDetector implements EdgeDetector {

	public WritableImage detectEdges(TemporalColor[][] imageLaplacian, int width, int height) {
		WritableImage imageWithEdges = new WritableImage(width, height);
		detectEdgesByRow(imageWithEdges, imageLaplacian, width, height);
		detectEdgesByColumn(imageWithEdges, imageLaplacian, width, height);
		return imageWithEdges;
	}

	private void detectEdgesByColumn(WritableImage imageWithEdges, TemporalColor[][] imageLaplacian, int width, int height) {
		PixelWriter pixelWriter = imageWithEdges.getPixelWriter();

		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				TemporalColor pixelLaplacian = imageLaplacian[row][column];
				TemporalColor nextPixelLaplacian = getOrEmpty(imageLaplacian, row + 1, column);
				TemporalColor nextNextPixelLaplacian = getOrEmpty(imageLaplacian, row + 2, column);
				Color color = assignColor(pixelLaplacian, nextPixelLaplacian, nextNextPixelLaplacian);
				pixelWriter.setColor(column, row, color);
			}
		}
	}

	private void detectEdgesByRow(WritableImage imageWithEdges, TemporalColor[][] imageLaplacian, int width, int height) {
		PixelWriter pixelWriter = imageWithEdges.getPixelWriter();

		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				TemporalColor pixelLaplacian = imageLaplacian[row][column];
				TemporalColor nextPixelLaplacian = getOrEmpty(imageLaplacian, row, column + 1);
				TemporalColor nextNextPixelLaplacian = getOrEmpty(imageLaplacian, row, column + 2);
				Color color = assignColor(pixelLaplacian, nextPixelLaplacian, nextNextPixelLaplacian);
				pixelWriter.setColor(column, row, color);
			}
		}
	}

	private Color assignColor(TemporalColor pixelLaplacian, TemporalColor nextPixelLaplacian, TemporalColor nextNextPixelLaplacian) {
		int red = evaluateCrossByZero(pixelLaplacian.getRed(), nextPixelLaplacian.getRed(), nextNextPixelLaplacian.getRed());
		int green = evaluateCrossByZero(pixelLaplacian.getGreen(), nextPixelLaplacian.getGreen(), nextNextPixelLaplacian.getGreen());
		int blue = evaluateCrossByZero(pixelLaplacian.getBlue(), nextPixelLaplacian.getBlue(), nextNextPixelLaplacian.getBlue());
		return Color.rgb(red, green, blue);
	}

	private int evaluateCrossByZero(int gray, int nextGray, int nextNextGray) {
		boolean negativeAndPositive = gray < 0 && 0 < nextGray;
		boolean positiveAndNegative = 0 < gray && nextGray < 0;
		boolean negativeZeroPositive = gray < 0 && nextGray == 0 && 0 < nextNextGray;
		boolean positiveZeroNegative = 0 < gray && nextGray == 0 && nextNextGray < 0;

		boolean crossByZero = negativeAndPositive || positiveAndNegative || negativeZeroPositive || positiveZeroNegative;

		if (crossByZero) {
			return 255;
		} else
			return 0;
	}
}