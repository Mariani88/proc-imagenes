package untref.eventhandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import untref.controllers.DrawingSelect;

public class CopyImageNewWindowsHandler implements EventHandler<ActionEvent> {

	private ImageView imageView;

	public CopyImageNewWindowsHandler(ImageView imageView) {
		this.imageView = imageView;
	}

	@Override
	public void handle(ActionEvent arg0) {
		WritableImage writableImage = imageView.snapshot(new SnapshotParameters(), null);
		DrawingSelect a = new DrawingSelect();
		a.start(writableImage);
	}
}
