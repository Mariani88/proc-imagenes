package untref.eventhandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import untref.edge.service.canny.StageInitCanny;

public class CreateCannyEdgeHandler  implements EventHandler<ActionEvent>{
	ImageView imageView;
	ImageView imageResultView;

	public CreateCannyEdgeHandler(ImageView imageView, ImageView imageResultView) {
		this.imageResultView = imageResultView;
		this.imageView = imageView;
	}

	@Override
	public void handle(ActionEvent arg0) {

		StageInitCanny cannyStage = new StageInitCanny();
		cannyStage.start(imageView.getImage(), imageResultView);
	}

}
