package untref.domain;

import static untref.domain.utils.ImageValuesTransformer.toDouble;

public class GrayProbability {

	private int gray;
	private final int totalPixels;
	private double probability;
	private int pixels;

	public GrayProbability(int gray, int totalPixels, int pixels) {
		this.gray = gray;
		this.totalPixels = totalPixels;
		this.pixels = pixels;
		this.probability = calculateProbability();
	}

	public int getGray() {
		return gray;
	}

	public double getProbability() {
		return probability;
	}

	public int getPixels() {
		return pixels;
	}

	public void updatePixelsAndProbability() {
		this.pixels++;
		this.probability = calculateProbability();
	}

	private double calculateProbability() {
		return toDouble(pixels) / toDouble(totalPixels);
	}
}