package untref.domain;

import javafx.scene.image.Image;

public class OtsuMethodResult {

	private Integer threshold;
	private Image image;

	public OtsuMethodResult(int threshold, Image image) {
		this.threshold = threshold;
		this.image = image;
	}

	public Image getImage() {
		return image;
	}

	public Integer getThreshold() {
		return threshold;
	}
}
