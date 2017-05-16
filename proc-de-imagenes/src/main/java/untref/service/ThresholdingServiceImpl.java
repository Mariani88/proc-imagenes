package untref.service;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import untref.domain.GrayProbability;
import untref.domain.OtsuMethodResult;
import untref.domain.ThresholdingResult;

import java.util.HashMap;
import java.util.Map;

import static untref.domain.utils.ImageValuesTransformer.toGrayScale;
import static untref.domain.utils.ImageValuesTransformer.toInt;

public class ThresholdingServiceImpl implements ThresholdingService {

	private static final int ZERO = 0;
	private final ImageStatisticService imageStatisticService;
	private static final int LIMIT_SCALE_GRAY = 255;
	private static final int TOTAL_COLORS = 256;

	public ThresholdingServiceImpl(ImageStatisticService imageStatisticService) {
		this.imageStatisticService = imageStatisticService;
	}

	@Override
	public Image getImageThreshold(Image image, int valueThreshold) {
		ImageGetColorRGB colorRgb = new ImageGetColorRGBImpl(image);
		WritableImage imageOut = new WritableImage((int) image.getWidth(), (int) image.getHeight());
		PixelWriter pixelWriter = imageOut.getPixelWriter();

		for (int i = ZERO; i < image.getWidth(); i++) {
			for (int j = ZERO; j < image.getHeight(); j++) {
				if (colorRgb.getGrayAverage(i, j) < valueThreshold) {
					pixelWriter.setColor(i, j, Color.BLACK);
				} else {
					pixelWriter.setColor(i, j, Color.WHITE);
				}
			}
		}
		return imageOut;
	}

	@Override
	public ThresholdingResult obtainThresholdByBasicGlobalMethod(Image image, Double initialThreshold, Double deltaThreshold) {
		double beforeThreshold = ZERO;
		double actualThreshold = beforeThreshold + deltaThreshold + 1;
		int iterations = ZERO;

		while (!isLowerOrEqualsToDelta(beforeThreshold, actualThreshold, deltaThreshold)) {
			beforeThreshold = actualThreshold;
			actualThreshold = calculateActualThreshold(image, actualThreshold);
			iterations++;
		}

		Image thresholdingimage = getImageThreshold(image, toInt(actualThreshold));
		return new ThresholdingResult(thresholdingimage, toInt(actualThreshold), iterations);
	}

	@Override
	public OtsuMethodResult obtainThresholdByOtsuMethod(Image image) {
		Map<Integer, GrayProbability> grayProbabilityByGray = imageStatisticService.calculateGrayProbabilityByGray(image);
		int optimalThreshold = ZERO;
		double maxStandardDesviation = ZERO;

		for (int threshold = ZERO; threshold < TOTAL_COLORS; threshold++) {
			Map<Integer, GrayProbability> class1 = calculateC(ZERO, threshold, grayProbabilityByGray);
			Map<Integer, GrayProbability> class2 = calculateC(threshold, TOTAL_COLORS, grayProbabilityByGray);
			double w1 = calculateW(class1);
			double w2 = calculateW(class2);
			double class1Average = calculateAverage(class1, w1);
			double class2Average = calculateAverage(class2, w2);
			double totalAverage = w1 * class1Average + w2 * class2Average;
			double standardDeviation = calculateStandardDesviation(class1Average, w1, class2Average, w2, totalAverage);

			if (maxStandardDesviation < standardDeviation) {
				maxStandardDesviation = standardDeviation;
				optimalThreshold = threshold;
			}
		}

		return new OtsuMethodResult(optimalThreshold, getImageThreshold(image, optimalThreshold));
	}

	private double calculateStandardDesviation(double c1Average, double w1, double c2Average, double w2, double totalAverage) {
		return w1 * Math.pow(c1Average - totalAverage, 2) + w2 * Math.pow(c2Average - totalAverage, 2);
	}

	private double calculateAverage(Map<Integer, GrayProbability> clazz, double w) {
		double average = ZERO;

		for (int gray = TOTAL_COLORS - clazz.size(); gray < clazz.size(); gray++) {
			GrayProbability grayProbability = clazz.get(gray);
			average += grayProbability.getProbability() * grayProbability.getPixels() / w;
		}

		return average;
	}

	private Map<Integer, GrayProbability> calculateC(int initialPosition, int finalPosition, Map<Integer, GrayProbability> grayProbabilityByGray) {
		Map<Integer, GrayProbability> clazz = new HashMap<>(finalPosition - initialPosition);

		for (int index = initialPosition; index < finalPosition; index++) {
			clazz.put(index, grayProbabilityByGray.get(index));
		}
		return clazz;
	}

	private double calculateW(Map<Integer, GrayProbability> clazz) {
		double w = ZERO;

		for (int index = TOTAL_COLORS - clazz.size(); index < clazz.size(); index++) {
			w += clazz.get(index).getProbability();
		}

		return w;
	}

	private double calculateActualThreshold(Image image, double actualThreshold) {
		int width = toInt(image.getWidth());
		int height = toInt(image.getHeight());
		PixelReader pixelReader = image.getPixelReader();
		int sumG1 = ZERO;
		int sumG2 = ZERO;
		int totalG1 = ZERO;
		int totalG2 = ZERO;

		for (int row = ZERO; row < height; row++) {
			for (int column = ZERO; column < width; column++) {
				int grayValue = toGrayScale(pixelReader.getColor(column, row));
				if (grayValue < actualThreshold) {
					sumG1 += grayValue;
					totalG1++;
				} else {
					sumG2 += grayValue;
					totalG2++;
				}
			}
		}

		return (average(sumG1, totalG1) + average(sumG2, totalG2)) / 2;
	}

	private int average(int sumG, int totalG) {
		if (totalG == ZERO) {
			return ZERO;
		}

		return sumG / totalG;
	}

	private boolean isLowerOrEqualsToDelta(double beforeThreshold, double actualThreshold, Double deltaThreshold) {
		return deltaThreshold.compareTo(Math.abs(actualThreshold - beforeThreshold)) >= ZERO;
	}
}