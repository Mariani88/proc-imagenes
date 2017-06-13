package untref.eventhandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import untref.edge.service.canny.StageInitCanny;
import untref.edge.service.hough.StageInitHough;

public class StrainghtEventHandler implements EventHandler<ActionEvent>{
	ImageView imageView;
	ImageView imageResultView;

	public StrainghtEventHandler(ImageView imageView, ImageView imageResultView) {
		this.imageResultView = imageResultView;
		this.imageView = imageView;
	}

	@Override
	public void handle(ActionEvent arg0) {

		StageInitHough houghStage = new StageInitHough();
		houghStage.start(imageView.getImage(), imageResultView);
	}
}
