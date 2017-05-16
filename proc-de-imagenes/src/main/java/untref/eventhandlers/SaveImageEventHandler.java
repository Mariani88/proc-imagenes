package untref.eventhandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import untref.service.ImageIOService;

public class SaveImageEventHandler implements EventHandler<ActionEvent> {

	private FileChooser fileChooser;
	private ImageView imageView;
	private ImageIOService imageIOService;

	public SaveImageEventHandler(FileChooser fileChooser, ImageView imageView, ImageIOService imageIOService) {
		this.fileChooser = fileChooser;
		this.imageView = imageView;
		this.imageIOService = imageIOService;
	}

	public void handle(ActionEvent event) {
		imageIOService.saveImage(fileChooser, imageView.getImage());
	}
}