package untref.eventhandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

public class CopyImageNewWindowsHandler  implements EventHandler<ActionEvent> {

	ImageView imageView;
	public CopyImageNewWindowsHandler(ImageView imageView) {
		
		this.imageView=imageView;
		
	}

	@Override
	public void handle(ActionEvent arg0) {
		WritableImage writableImage = imageView.snapshot(new SnapshotParameters(), null);
		untref.controllers.DrawingSelect a = new untref.controllers.DrawingSelect();
		a.start(writableImage);
	}
}
