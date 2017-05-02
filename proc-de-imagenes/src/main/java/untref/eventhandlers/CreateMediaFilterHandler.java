package untref.eventhandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import untref.filters.services.StageMaskService;

public class CreateMediaFilterHandler implements EventHandler<ActionEvent> {
	ImageView imageView;
	ImageView imageResultView;

	public CreateMediaFilterHandler(ImageView imageView, ImageView imageResultView) {
		this.imageResultView = imageResultView;
		this.imageView = imageView;
	}

	@Override
	public void handle(ActionEvent arg0) {

		StageMaskService mediaFilter = new StageMaskService();
		mediaFilter.startMedia(false, imageView.getImage(), imageResultView,false,false,false);
	}
}
