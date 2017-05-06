package untref.domain.edgedetectionoperators.secondderivative;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import untref.domain.TemporalColor;
import untref.service.MaskApplicationService;
import untref.service.MaskApplicationServiceImpl;

import static untref.domain.utils.ImageValuesTransformer.getOrEmpty;
import static untref.domain.utils.ImageValuesTransformer.toInt;

public class LaplacianOperator implements EdgeDetectionSecondDerivativeOperator {

	private static final int LAPLACIAN_OPERATOR[][] = { { 0, -1, 0 }, { -1, 4, -1 }, { 0, -1, 0 } };
	private final MaskApplicationService maskApplicationService;

	public LaplacianOperator() {
		maskApplicationService = new MaskApplicationServiceImpl();
	}

	@Override
	public Image detectEdges(Image image) {
		int width = toInt(image.getWidth());
		int height = toInt(image.getHeight());
		WritableImage imageWithEdges = new WritableImage(width, height);
		PixelReader pixelReader = image.getPixelReader();
		TemporalColor imageLaplacian[][] = new TemporalColor[height][width];
		int offsetI = 1;
		int offsetJ = 1;

		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				imageLaplacian[row][column] = maskApplicationService.applyMask(image, row, column, pixelReader, LAPLACIAN_OPERATOR, offsetI,
						offsetJ);
			}
		}
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