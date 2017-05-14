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

	private final ImageStatisticService imageStatisticService;
	private static final int LIMIT_SCALE_GRAY = 255;

	public ThresholdingServiceImpl(ImageStatisticService imageStatisticService) {
		this.imageStatisticService = imageStatisticService;
	}

	@Override
	public Image getImageThreshold(Image image, int valueThreshold) {
		ImageGetColorRGB colorRgb = new ImageGetColorRGBImpl(image);
		WritableImage imageOut = new WritableImage((int) image.getWidth(), (int) image.getHeight());
		PixelWriter pixelWriter = imageOut.getPixelWriter();

		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
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
		double beforeThreshold = 0;
		double actualThreshold = beforeThreshold + deltaThreshold + 1;
		int iterations = 0;

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
		int optimalThreshold = 0;
		double maxStandardDesviation = 0;

		for (int threshold = 0; threshold < 256; threshold++) {
			Map<Integer, GrayProbability> c1 = calculateC(0, threshold, grayProbabilityByGray);
			Map<Integer, GrayProbability> c2 = calculateC(threshold, LIMIT_SCALE_GRAY + 1, grayProbabilityByGray);
			double w1 = calculateW(c1);
			double w2 = calculateW(c2);
			double c1Average = calculateAverage(c1, w1);
			double c2Average = calculateAverage(c2, w2);
			double totalAverage = w1 * c1Average + w2 * c2Average;
			double standardDesviation = calculateStandardDesviation(c1Average, w1, c2Average, w2, totalAverage);

			if (maxStandardDesviation < standardDesviation) {
				maxStandardDesviation = standardDesviation;
				optimalThreshold = threshold;
			}
		}

		return new OtsuMethodResult(optimalThreshold, getImageThreshold(image, optimalThreshold));
	}

	private double calculateStandardDesviation(double c1Average, double w1, double c2Average, double w2, double totalAverage) {
		return w1 * Math.pow(c1Average - totalAverage, 2) + w2 * Math.pow(c2Average - totalAverage, 2);
	}

	private double calculateAverage(Map<Integer, GrayProbability> clazz, double w) {
		double average = 0;

		for (int gray = 256 - clazz.size(); gray < clazz.size(); gray++) {
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
		double w = 0;

		for (int index = 256 - clazz.size(); index < clazz.size(); index++) {
			w += clazz.get(index).getProbability();
		}

		return w;
	}

	private double calculateActualThreshold(Image image, double actualThreshold) {
		int width = toInt(image.getWidth());
		int height = toInt(image.getHeight());
		PixelReader pixelReader = image.getPixelReader();
		int sumG1 = 0;
		int sumG2 = 0;
		int totalG1 = 0;
		int totalG2 = 0;

		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
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
		if (totalG == 0) {
			return 0;
		}

		return sumG / totalG;
	}

	private boolean isLowerOrEqualsToDelta(double beforeThreshold, double actualThreshold, Double deltaThreshold) {
		return deltaThreshold.compareTo(Math.abs(actualThreshold - beforeThreshold)) >= 0;
	}
}