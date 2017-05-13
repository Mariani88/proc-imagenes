package untref.eventhandlers;

import javafx.scene.image.ImageView;
import untref.controllers.nodeutils.ImageSetter;
import untref.service.ThresholdingService;
import untref.service.ThresholdingServiceImpl;

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
		ThresholdingService threshold = new ThresholdingServiceImpl();
		ImageSetter.set(imageResultView, threshold.getImageThreshold(imageView.getImage(), value));
	}
}
