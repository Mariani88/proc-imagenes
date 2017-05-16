package untref.eventhandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import untref.filters.services.StageMaskService;

public class CreateMedianWeightedHandler implements EventHandler<ActionEvent> {
	ImageView imageView;
	ImageView imageResultView;

	public CreateMedianWeightedHandler(ImageView imageView, ImageView imageResultView) {
		this.imageResultView = imageResultView;
		this.imageView = imageView;
	}

	@Override
	public void handle(ActionEvent arg0) {

		StageMaskService medianaFilter = new StageMaskService();
		medianaFilter.startMedia(true, imageView.getImage(), imageResultView,true,false,false);
	}
}