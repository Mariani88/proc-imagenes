package untref.service;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import untref.domain.GrayProbability;
import untref.domain.TemporalColor;

import java.util.HashMap;
import java.util.Map;

import static untref.domain.utils.ImageValuesTransformer.toGrayScale;
import static untref.domain.utils.ImageValuesTransformer.toInt;

public class ImageStatisticServiceImpl implements ImageStatisticService {

	private static final int LIMIT_SCALE_GRAY = 255;

	@Override
	public TemporalColor calculateColorAverage(Image image) {
		double amountPixels = toInt(image.getWidth() * image.getHeight());
		int redSummary = 0;
		int greenSummary = 0;
		int blueSummary = 0;
		PixelReader pixelReader = image.getPixelReader();

		for (int row = 0; row < toInt(image.getHeight()); row++) {
			for (int column = 0; column < toInt(image.getWidth()); column++) {
				Color color = pixelReader.getColor(column, row);
				redSummary = redSummary + toInt(color.getRed() * LIMIT_SCALE_GRAY);
				greenSummary = greenSummary + toInt(color.getGreen() * LIMIT_SCALE_GRAY);
				blueSummary = blueSummary + toInt(color.getBlue() * LIMIT_SCALE_GRAY);
			}
		}

		return new TemporalColor(toInt(redSummary / amountPixels), toInt(greenSummary / amountPixels), toInt(blueSummary / amountPixels));
	}

	@Override
	public TemporalColor calculateColorStandardDeviation(Image image, TemporalColor colorAverage) {
		PixelReader pixelReader = image.getPixelReader();
		int redSummary = 0;
		int greenSummary = 0;
		int blueSummary = 0;
		double amountPixels = toInt(image.getWidth() * image.getHeight());

		for (int row = 0; row < toInt(image.getHeight()); row++) {
			for (int column = 0; column < toInt(image.getWidth()); column++) {
				Color color = pixelReader.getColor(column, row);
				redSummary = redSummary + (int) Math.pow(toInt(LIMIT_SCALE_GRAY * color.getRed()) - colorAverage.getRed(), 2);
				greenSummary = greenSummary + (int) Math.pow(toInt(LIMIT_SCALE_GRAY * color.getGreen()) - colorAverage.getGreen(), 2);
				blueSummary = blueSummary + (int) Math.pow(toInt(LIMIT_SCALE_GRAY * color.getBlue()) - colorAverage.getBlue(), 2);
			}
		}

		int redStandardDeviation = toInt(Math.sqrt(redSummary / amountPixels));
		int greenStandardDeviation = toInt(Math.sqrt(greenSummary / amountPixels));
		int blueStandardDeviation = toInt(Math.sqrt(blueSummary / amountPixels));
		return new TemporalColor(redStandardDeviation, greenStandardDeviation, blueStandardDeviation);
	}

	@Override
	public Map<Integer, GrayProbability> calculateGrayProbabilityByGray(Image image) {
		int width = toInt(image.getWidth());
		int height = toInt(image.getHeight());
		PixelReader pixelReader = image.getPixelReader();
		int totalPixels = width * height;
		Map<Integer, GrayProbability> grayProbabilityByGray = createGrayProbabilityByGray(totalPixels);

		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				int gray = toGrayScale(pixelReader.getColor(column, row));
				GrayProbability grayProbability = grayProbabilityByGray.get(gray);
				grayProbability.updatePixelsAndProbability();
				grayProbabilityByGray.put(gray, grayProbability);
			}
		}

		return grayProbabilityByGray;
	}

	private HashMap<Integer, GrayProbability> createGrayProbabilityByGray(int totalPixels) {
		HashMap<Integer, GrayProbability> grayProbabilityByGray = new HashMap<>(LIMIT_SCALE_GRAY + 1);

		for (int gray = 0; gray <= LIMIT_SCALE_GRAY; gray++) {
			grayProbabilityByGray.put(gray, new GrayProbability(gray, totalPixels, 0));
		}

		return grayProbabilityByGray;
	}
}