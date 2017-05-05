package untref.eventhandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import untref.filters.services.StageMaskService;

public class CreateGaussianHandler implements EventHandler<ActionEvent> {

	ImageView imageView;
	ImageView imageResultView;

	public CreateGaussianHandler(ImageView imageView, ImageView imageResultView) {
		this.imageResultView = imageResultView;
		this.imageView = imageView;
	}

	@Override
	public void handle(ActionEvent arg0) {

		StageMaskService gaussianFilter = new StageMaskService();
		gaussianFilter.startMedia(false, imageView.getImage(), imageResultView, false, false, true);
	}

}
