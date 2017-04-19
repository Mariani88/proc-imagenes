package untref.service;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ImageEditionServiceImpl implements ImageEditionService {

	@Override
	public Image modifyPixelValue(Image image, String aX, String aY, String pixelValue) {
		PixelReader pixelReader = image.getPixelReader();
		int width = (int) image.getWidth();
		int height = (int) image.getHeight();
		WritableImage writableImage = new WritableImage(width, height);
		PixelWriter pixelWriter = writableImage.getPixelWriter();

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				pixelWriter.setColor(x, y, pixelReader.getColor(x, y));
			}
		}

		int x = toInt(aX);
		int y = toInt(aY);
		pixelWriter.setArgb(x, y, toInt(pixelValue));
		return writableImage;
	}

	@Override
	public double[] RGBtoHSV(double r, double g, double b) {

		double h, s, v;
		double min, max, delta;

		min = Math.min(Math.min(r, g), b);
		max = Math.max(Math.max(r, g), b);

		// V
		v = max;

		delta = max - min;

		// S
		if (max != 0)
			s = delta / max;
		else {
			s = 0;
			h = -1;
			return new double[] { h, s, v };
		}
		// H
		if (r == max)
			h = (g - b) / delta; // between yellow & magenta
		else if (g == max)
			h = 2 + (b - r) / delta; // between cyan & yellow
		else
			h = 4 + (r - g) / delta; // between magenta & cyan

		h *= 60; // degrees

		if (h < 0)
			h += 360;

		return new double[] { h, s, v };
	}

	//TODO testear.
	@Override
	public Image transformToNegative(Image image) {
		WritableImage writableImage = new WritableImage((int) image.getWidth(), (int) image.getHeight());
		PixelWriter pixelWriter = writableImage.getPixelWriter();
		PixelReader pixelReader = image.getPixelReader();

		for (int row = 0; row < image.getHeight(); row++) {
			for (int column = 0; column < image.getWidth(); column++) {
				pixelWriter.setColor(column, row, transformToNegativeColor(pixelReader.getColor(column, row)));
			}
		}

		return writableImage;
	}

	private Color transformToNegativeColor(Color color) {
		return Color.rgb(calculateNegativeScaleGray(color.getRed()), calculateNegativeScaleGray(color.getGreen()),
				calculateNegativeScaleGray(color.getBlue()));
	}

	private int calculateNegativeScaleGray(double grayScale) {
		return (int) (255 - grayScale * 255);
	}

	private int toInt(String text) {
		return Integer.parseInt(text);
	}
}