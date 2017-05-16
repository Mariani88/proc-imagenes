package untref.eventhandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import untref.filters.services.StageMaskService;

public class CreateHighPassEdgeHandler implements EventHandler<ActionEvent> {
	ImageView imageView;
	ImageView imageResultView;

	public CreateHighPassEdgeHandler(ImageView imageView, ImageView imageResultView) {
		this.imageResultView = imageResultView;
		this.imageView = imageView;
	}

	@Override
	public void handle(ActionEvent arg0) {

		StageMaskService highPass = new StageMaskService();
		highPass.startMedia(false, imageView.getImage(), imageResultView,false,true,false);
	}
}