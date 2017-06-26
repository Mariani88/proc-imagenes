package untref.eventhandlers;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import untref.edge.service.hough.circle.StageInitHoughCircle;
import untref.sift.Sift;
import untref.sift.StageInitSift;

public class SiftEventHandler implements EventHandler<ActionEvent>{

	private ImageView imageResultView;
	private ImageView imageView;

	public SiftEventHandler(ImageView imageView, ImageView imageResultView) {
		this.imageResultView = imageResultView;
		this.imageView = imageView;
	}

	@Override
	public void handle(ActionEvent arg0) {
		StageInitSift stage =new StageInitSift();
		stage.start(imageView.getImage(), imageResultView.getImage());
		
	}

}
