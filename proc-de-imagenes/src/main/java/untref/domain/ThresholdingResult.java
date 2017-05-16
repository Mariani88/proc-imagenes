package untref.domain;

import javafx.scene.image.Image;

public class ThresholdingResult {

	private final Image image;
	private final Integer detectedThreshold;
	private final Integer iterations;

	public ThresholdingResult(Image thresholdingimage, Integer detectedThreshold, Integer iterations) {
		this.image = thresholdingimage;
		this.detectedThreshold = detectedThreshold;
		this.iterations = iterations;
	}

	public Image getImage() {
		return image;
	}

	public Integer getDetectedThreshold() {
		return detectedThreshold;
	}

	public Integer getIterations() {
		return iterations;
	}
}