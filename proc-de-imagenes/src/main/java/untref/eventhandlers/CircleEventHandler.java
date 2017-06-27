package untref.eventhandlers;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import untref.edge.service.hough.StageInitHough;
import untref.edge.service.hough.circle.StageInitHoughCircle;

import untref.sift.Sift;

public class CircleEventHandler implements EventHandler<ActionEvent>{
	ImageView imageView;
	ImageView imageResultView;

	public CircleEventHandler(ImageView imageView, ImageView imageResultView) {
		this.imageResultView = imageResultView;
		this.imageView = imageView;
	}

	@Override
	public void handle(ActionEvent arg0) {
		

		StageInitHoughCircle houghStage = new StageInitHoughCircle();
		houghStage.start(imageView.getImage(), imageResultView);
	}
}
