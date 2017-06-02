package untref.eventhandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import untref.controllers.RawImage;
import untref.controllers.nodeutils.ImageSetter;
import untref.service.ImageIOService;

import java.util.Optional;

public class OpenImageEventHandler implements EventHandler<ActionEvent> {

	private FileChooser fileChooser;
	private ImageView imageView;
	private ImageIOService imageIOService;
	private RawImage rawImage;

	public OpenImageEventHandler(FileChooser fileChooser, ImageView imageView, ImageIOService imageIOService,
			RawImage rawImage) {
		this.fileChooser = fileChooser;
		this.imageView = imageView;
		this.imageIOService = imageIOService;
		this.rawImage = rawImage;
	}

	public void handle(ActionEvent event) {
		Optional<Image> image = imageIOService.openImage(fileChooser, rawImage);
		image.ifPresent(image1 -> ImageSetter.set(imageView, image1));
	}
}