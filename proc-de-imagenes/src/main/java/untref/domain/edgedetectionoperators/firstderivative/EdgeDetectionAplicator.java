package untref.domain.edgedetectionoperators.firstderivative;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import untref.domain.TemporalColor;
import untref.service.MaskApplicationService;

import static untref.domain.utils.ImageValuesTransformer.toInt;

public class EdgeDetectionAplicator {

	private MaskApplicationService maskApplicationService;

	public EdgeDetectionAplicator(MaskApplicationService maskApplicationService) {
		this.maskApplicationService = maskApplicationService;
	}

	public Image applyEdgeDetectionOperator(Image image, int operatorFx[][], int operatorFy[][], int limitThresholdForGradientMagnitude, int offsetI,
			int offsetJ) {
		int width = toInt(image.getWidth());
		int height = toInt(image.getHeight());
		WritableImage imageWithEdges = new WritableImage(width, height);
		PixelReader pixelReader = image.getPixelReader();
		PixelWriter pixelWriter = imageWithEdges.getPixelWriter();

		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				TemporalColor fx = calculateDerivative(image, row, column, pixelReader, operatorFx, offsetI, offsetJ);
				TemporalColor fy = calculateDerivative(image, row, column, pixelReader, operatorFy, offsetI, offsetJ);
				TemporalColor gradientMagnitude = calculateGradientMagnitude(fx, fy);
				pixelWriter.setColor(column, row, assignColor(gradientMagnitude, limitThresholdForGradientMagnitude));
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
			return 0;
		} else {
			return 255;
		}
	}

	private TemporalColor calculateDerivative(Image image, int row, int column, PixelReader pixelReader, int prewittOperator[][], int offsetI,
			int offsetJ) {
		return maskApplicationService.applyMask(image, row, column, pixelReader, prewittOperator, offsetI, offsetJ);
	}
}