package untref.domain.edgedetectionoperators.secondderivative;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import untref.domain.TemporalColor;
import untref.service.ImageDerivativeService;
import untref.service.ImageDerivativeServiceImpl;
import untref.service.MaskApplicationServiceImpl;

import static untref.domain.utils.ImageValuesTransformer.getOrEmpty;
import static untref.domain.utils.ImageValuesTransformer.toInt;
import static untref.domain.utils.ImageValuesTransformer.toRGBScale;

public class LaplacianWithSlopeEvaluationOperator implements EdgeDetectionSecondDerivativeOperator {

	private final Double maxSlopePercent;
	private int maxRedSlope;
	private int maxGreenSlope;
	private int maxBlueSlope;
	private ImageDerivativeService imageDerivativeService;

	public LaplacianWithSlopeEvaluationOperator(Double maxSlopePercent) {
		this.maxSlopePercent = maxSlopePercent;
		this.imageDerivativeService = new ImageDerivativeServiceImpl(new MaskApplicationServiceImpl());
		maxRedSlope = 0;
		maxGreenSlope = 0;
		maxBlueSlope = 0;
	}

	@Override
	public Image detectEdges(Image image) {
		int width = toInt(image.getWidth());
		int height = toInt(image.getHeight());
		TemporalColor[][] imageLaplacian = imageDerivativeService.calculateLaplacian(image, width, height);
		//TemporalColor[][] slopes = calculateSlopes(imageLaplacian, width, height);
		TemporalColor slopes[][] = new TemporalColor[height][width];
		calculateSlopesByRow(imageLaplacian, slopes);
		WritableImage imageWithEdges = new WritableImage(width, height);
		PixelWriter pixelWriter = imageWithEdges.getPixelWriter();
		applyEdges(slopes, width, height, pixelWriter);
		//return applyEdges(slopes, width, height);
		calculateSlopesByColumn(imageLaplacian, slopes);
		applyEdgesWithCondition(slopes, width, height, pixelWriter, imageWithEdges.getPixelReader());


		return imageWithEdges;
	}

	private void applyEdgesWithCondition(TemporalColor[][] slopes, int width, int height, PixelWriter pixelWriter, PixelReader pixelReader) {

		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				int red = applyEdge(slopes[row][column].getRed(), calculateThreshold(maxRedSlope));
				int green = applyEdge(slopes[row][column].getGreen(), calculateThreshold(maxGreenSlope));
				int blue = applyEdge(slopes[row][column].getBlue(), calculateThreshold(maxBlueSlope));

				Color color = pixelReader.getColor(column, row);
				TemporalColor temporalColor = new TemporalColor(toRGBScale(color.getRed()), toRGBScale(color.getGreen()),toRGBScale(color.getBlue()));


				if(temporalColor.getRed() != 255 || temporalColor.getGreen() !=255 || temporalColor.getBlue()!=255){
					pixelWriter.setColor(column, row, Color.rgb(red, green, blue));
				}
			}
		}

	}

	private TemporalColor[][] calculateSlopes(TemporalColor[][] imageLaplacian, int width, int height) {
		TemporalColor slopes[][] = new TemporalColor[height][width];
		slopes = calculateSlopesByRow(imageLaplacian, slopes);
		slopes = calculateSlopesByColumn(imageLaplacian, slopes);
		return slopes;
	}

	private TemporalColor[][] calculateSlopesByColumn(TemporalColor[][] imageLaplacian, TemporalColor[][] slopes) {
		for (int row = 0; row < imageLaplacian.length; row++) {
			for (int column = 0; column < imageLaplacian[row].length; column++) {
				TemporalColor pixelLaplacian = getOrEmpty(imageLaplacian, row, column);
				TemporalColor nextPixelLaplacian = getOrEmpty(imageLaplacian, row + 1, column);
				TemporalColor nextNextPixelLaplacian = getOrEmpty(imageLaplacian, row + 2, column);
				TemporalColor slope = calculateSlope(pixelLaplacian, nextPixelLaplacian, nextNextPixelLaplacian);
				slopes[row][column] = slope;
			}
		}
		return slopes;
	}

	private TemporalColor[][] calculateSlopesByRow(TemporalColor[][] imageLaplacian, TemporalColor[][] slopes) {
		for (int row = 0; row < imageLaplacian.length; row++) {
			for (int column = 0; column < imageLaplacian[row].length; column++) {
				TemporalColor pixelLaplacian = getOrEmpty(imageLaplacian, row, column);
				TemporalColor nextPixelLaplacian = getOrEmpty(imageLaplacian, row, column + 1);
				TemporalColor nextNextPixelLaplacian = getOrEmpty(imageLaplacian, row, column + 2);
				TemporalColor slope = calculateSlope(pixelLaplacian, nextPixelLaplacian, nextNextPixelLaplacian);
				slopes[row][column] = slope;
			}
		}
		return slopes;
	}

	private TemporalColor calculateSlope(TemporalColor pixelLaplacian, TemporalColor nextPixelLaplacian, TemporalColor nextNextPixelLaplacian) {
		int redSlope = calculateSlopeForChannel(pixelLaplacian.getRed(), nextPixelLaplacian.getRed(), nextNextPixelLaplacian.getRed());
		int greenSlope = calculateSlopeForChannel(pixelLaplacian.getGreen(), nextPixelLaplacian.getGreen(), nextNextPixelLaplacian.getGreen());
		int blueSlope = calculateSlopeForChannel(pixelLaplacian.getBlue(), nextPixelLaplacian.getBlue(), nextNextPixelLaplacian.getBlue());
		evaluateMaxsSlopes(redSlope, greenSlope, blueSlope);
		return new TemporalColor(redSlope, greenSlope, blueSlope);
	}

	private void evaluateMaxsSlopes(int redSlope, int greenSlope, int blueSlope) {
		maxRedSlope = Math.max(maxRedSlope, redSlope);
		maxGreenSlope = Math.max(maxGreenSlope, greenSlope);
		maxBlueSlope = Math.max(maxBlueSlope, blueSlope);
	}

	private int calculateSlopeForChannel(int gray, int nextGray, int nextNextGray) {
		boolean negativeAndPositive = gray < 0 && nextGray > 0;
		boolean positiveAndNegative = gray > 0 && nextGray < 0;
		boolean positiveZeroNegative = gray > 0 && nextGray == 0 && nextNextGray < 0;
		boolean negativeZeroPositive = gray < 0 && nextGray == 0 && nextNextGray > 0;
		int slope = 0;

		if (negativeAndPositive || positiveAndNegative) {
			slope = Math.abs(gray) + Math.abs(nextGray);
		} else if (negativeZeroPositive || positiveZeroNegative) {
			slope = Math.abs(gray) + Math.abs(nextNextGray);
		}

		return slope;
	}

	private void applyEdges(TemporalColor[][] slopes, int width, int height, PixelWriter pixelWriter) {

		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				int red = applyEdge(slopes[row][column].getRed(), calculateThreshold(maxRedSlope));
				int green = applyEdge(slopes[row][column].getGreen(), calculateThreshold(maxGreenSlope));
				int blue = applyEdge(slopes[row][column].getBlue(), calculateThreshold(maxBlueSlope));
				pixelWriter.setColor(column, row, Color.rgb(red, green, blue));
			}
		}


	}

	private int calculateThreshold(int maxGraySlope) {
		return toInt(maxSlopePercent * maxGraySlope)/100;
	}

	private int applyEdge(int gray, int maxSlope) {
		int grayValue = 0;

		if (maxSlope < gray) {
			grayValue = 255;
		}
		return grayValue;
	}
}