package untref.domain.edge.edgedetectionoperators.firstderivative;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import untref.domain.TemporalColor;
import untref.service.ImageDerivativeService;
import untref.service.ImageDerivativeServiceImpl;
import untref.service.MaskApplicationService;

import static untref.domain.utils.ImageValuesTransformer.toInt;

public class EdgeDetectionAplicator {

	private static final int EDGE = 255;
	private static final int NOT_EDGE = 0;
	private final ImageDerivativeService imageDerivativeService;

	public EdgeDetectionAplicator(MaskApplicationService maskApplicationService) {
		imageDerivativeService = new ImageDerivativeServiceImpl(maskApplicationService);
	}

	public Image applyEdgeDetectionOperator(Image image, int operatorFx[][], int operatorFy[][], int thresholdForGradientMagnitude, int offsetI,
			int offsetJ) {
		int width = toInt(image.getWidth());
		int height = toInt(image.getHeight());
		WritableImage imageWithEdges = new WritableImage(width, height);
		PixelReader pixelReader = image.getPixelReader();
		PixelWriter pixelWriter = imageWithEdges.getPixelWriter();

		for (int row = NOT_EDGE; row < height; row++) {
			for (int column = NOT_EDGE; column < width; column++) {
				TemporalColor fx = imageDerivativeService.calculateDerivative(image, row, column, pixelReader, operatorFx, offsetI, offsetJ);
				TemporalColor fy = imageDerivativeService.calculateDerivative(image, row, column, pixelReader, operatorFy, offsetI, offsetJ);
				TemporalColor gradientMagnitude = calculateGradientMagnitude(fx, fy);
				pixelWriter.setColor(column, row, assignColor(gradientMagnitude, thresholdForGradientMagnitude));
			}
		}

		return imageWithEdges;
	}

	public Image applyEdgeDetectionForHorizontalEdge(Image image, int[][] operatorFx, Integer limitThresholdForDerivative, int offsetI, int
			offsetJ) {
		int width = toInt(image.getWidth());
		int height = toInt(image.getHeight());
		WritableImage imageWithEdges = new WritableImage(width, height);
		PixelReader pixelReader = image.getPixelReader();
		PixelWriter pixelWriter = imageWithEdges.getPixelWriter();

		for (int row = NOT_EDGE; row < height; row++) {
			for (int column = NOT_EDGE; column < width; column++) {
				TemporalColor fx = imageDerivativeService.calculateDerivative(image, row, column, pixelReader, operatorFx, offsetI, offsetJ);
				pixelWriter.setColor(column, row, assignColor(fx, limitThresholdForDerivative));
			}
		}

		return imageWithEdges;
	}

	public Image applyEdgeDetectionForVerticalEdge(Image image, int[][] operatorFy, Integer limitThresholdForDerivative, int offsetI, int offsetJ) {
		int width = toInt(image.getWidth());
		int height = toInt(image.getHeight());
		WritableImage imageWithEdges = new WritableImage(width, height);
		PixelReader pixelReader = image.getPixelReader();
		PixelWriter pixelWriter = imageWithEdges.getPixelWriter();

		for (int row = NOT_EDGE; row < height; row++) {
			for (int column = NOT_EDGE; column < width; column++) {
				TemporalColor fy = imageDerivativeService.calculateDerivative(image, row, column, pixelReader, operatorFy, offsetI, offsetJ);
				pixelWriter.setColor(column, row, assignColor(fy, limitThresholdForDerivative));
			}
		}

		return imageWithEdges;
	}

	private TemporalColor calculateGradientMagnitude(TemporalColor fx, TemporalColor fy) {
		int gradientMagnitudeRed = toInt(Math.sqrt(Math.pow(fx.getRed(), 2) + Math.pow(fy.getRed(), 2)));
		int gradientMagnitudeGreen = toInt(Math.sqrt(Math.pow(fx.getGreen(), 2) + Math.pow(fy.getGreen(), 2)));
		int gradientMagnitudeBlue = toInt(Math.sqrt(Math.pow(fx.getBlue(), 2) + Math.pow(fy.getBlue(), 2)));
		return new TemporalColor(gradientMagnitudeRed, gradientMagnitudeGreen, gradientMagnitudeBlue);
	}

	private Color assignColor(TemporalColor gradientMagnitude, Integer limitThresholdForGradientMagnitude) {
		int red = assignGrayValue(gradientMagnitude.getRed(), limitThresholdForGradientMagnitude);
		int green = assignGrayValue(gradientMagnitude.getGreen(), limitThresholdForGradientMagnitude);
		int blue = assignGrayValue(gradientMagnitude.getBlue(), limitThresholdForGradientMagnitude);
		return Color.rgb(red, green, blue);
	}

	private int assignGrayValue(int gradientMagnitudeForChannel, Integer limitThresholdForGradientMagnitude) {
		if (gradientMagnitudeForChannel <= limitThresholdForGradientMagnitude) {
			return NOT_EDGE;
		} else {
			return EDGE;
		}
	}
}