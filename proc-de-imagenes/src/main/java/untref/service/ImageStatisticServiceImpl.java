package untref.service;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import untref.domain.TemporalColor;

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
}