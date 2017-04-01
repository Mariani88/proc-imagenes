package untref.eventhandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import untref.service.ImageIOService;

import java.util.Optional;

public class OpenImageEventHandler implements EventHandler<ActionEvent> {

	private FileChooser fileChooser;
	private ImageView imageView;
	private ImageIOService imageIOService;

	public OpenImageEventHandler(FileChooser fileChooser, ImageView imageView, ImageIOService imageIOService) {
		this.fileChooser = fileChooser;
		this.imageView = imageView;
		this.imageIOService = imageIOService;
	}

	public void handle(ActionEvent event) {
		Optional<Image> image = imageIOService.openImage(fileChooser);
		image.ifPresent(image1 -> imageView.setImage(image1));
	}
}