package untref.eventhandlers;

import javafx.scene.image.ImageView;
import untref.controllers.nodeutils.ImageSetter;
import untref.service.CreateThresholdService;

public class CreateThresholdHandler {

	ImageView imageView;
	ImageView imageResultView;
	int value;

	public CreateThresholdHandler(ImageView imageView, ImageView imageResultView, int value) {
		this.imageView = imageView;
		this.imageResultView = imageResultView;
		this.value = value;
	}

	public void handle() {
		CreateThresholdService threshold = new CreateThresholdService();
		ImageSetter.set(imageResultView, threshold.getImageThreshold(imageView.getImage(), value));
	}
}
