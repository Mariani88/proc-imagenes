package untref.domain.utils;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import untref.domain.TemporalColor;

import static untref.utils.ImageValidator.existPosition;

public class ImageValuesTransformer {

	private static final int TOTAL_COLORS = 255;
	private static final TemporalColor DEFAULT_TEMPORAL_COLOR = new TemporalColor(0, 0, 0);

	public static int toInt(double value) {
		return (int) value;
	}

	public static double toDouble(int value) {
		return (double) value;
	}

	public static int toRGBScale(double grayValue) {
		return toInt(grayValue * TOTAL_COLORS);
	}

	public static TemporalColor getPositionColorOrEmpty(int row, int column, Image image, PixelReader pixelReader) {
		TemporalColor temporalColor = DEFAULT_TEMPORAL_COLOR;

		if (existPosition(image, row, column)) {
			Color color = pixelReader.getColor(column, row);
			temporalColor = new TemporalColor(toRGBScale(color.getRed()), toRGBScale(color.getGreen()), toRGBScale(color.getBlue()));
		}

		return temporalColor;
	}

	public static TemporalColor getOrEmpty(TemporalColor[][] temporalColors, int row, int column) {
		if (existPosition(temporalColors, row, column)) {
			return temporalColors[row][column];
		} else {
			return DEFAULT_TEMPORAL_COLOR;
		}
	}

	public static int toGrayScale(Color color) {
		int red = toRGBScale(color.getRed());
		int green = toRGBScale(color.getGreen());
		int blue = toRGBScale(color.getBlue());
		return (red + green + blue) / 3;
	}

	public static int toGrayScaleOrEmpty(int row, int column, Image image) {
		int gray = 0;
		PixelReader pixelReader = image.getPixelReader();

		if (existPosition(image, row, column)) {
			gray = toGrayScale(pixelReader.getColor(column, row));
		}
		return gray;
	}
}