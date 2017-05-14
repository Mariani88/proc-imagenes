package untref.service;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class ImageGetColorRGBImpl implements ImageGetColorRGB {
	private double red;
	private double green;
	private double blue;
	private Image image;
	private int totalPixel;
	private double gray;

	public ImageGetColorRGBImpl(Image image) {
		this.image = image;
	}

	private void initialize() {
		this.red = 0;
		this.blue = 0;
		this.green = 0;
		this.gray = 0;
		this.totalPixel = 0;

	}

	private void getColors() {
		int width = (int) image.getWidth();
		int height = (int) image.getHeight();
		this.initialize();

		PixelReader reader = image.getPixelReader();

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {

				Color color = reader.getColor(x, y);
				this.red += (color.getRed());
				this.green += (color.getGreen());
				this.blue += (color.getBlue());
				this.gray += ((color.getRed() + color.getGreen() + color.getBlue()) / 3);

				this.totalPixel++;
			}
		}
	}

	@Override
	public Color getAverageChannelsRGB() {
		this.getColors();
		Color totalColor = Color.color(red / this.totalPixel, green / this.totalPixel, blue / this.totalPixel);

		return totalColor;
	}

	@Override
	public Double getAverageGrey() {
		this.getColors();

		return (double) (this.gray / this.totalPixel);
	}

	@Override
	public Double getTotalValueChannelR() {
		this.getColors();
		return this.red;
	}

	@Override
	public Double getTotalValueChannelG() {
		this.getColors();
		return this.green;
	}

	@Override
	public Double getTotalValueChannelB() {
		this.getColors();
		return this.blue;
	}

	@Override
	public int getTotalPixel() {
		this.getColors();
		return this.totalPixel;
	}

	@Override
	public Double getValueChannelR(int x, int y) {

		PixelReader reader = image.getPixelReader();

		return reader.getColor(x, y).getRed();

	}

	@Override
	public Double getValueChannelG(int x, int y) {

		PixelReader reader = image.getPixelReader();

		return reader.getColor(x, y).getGreen();
	}

	@Override
	public Double getValueChannelB(int x, int y) {
		PixelReader reader = image.getPixelReader();

		return reader.getColor(x, y).getBlue();
	}

	@Override
	public int getGrayAverage(int x, int y) {
		int promedio;
		promedio = (int) ((this.getValueChannelB(x, y) * 255 + this.getValueChannelG(x, y) * 255
				+ this.getValueChannelR(x, y) * 255) / 3);

		return promedio;
	}

}
