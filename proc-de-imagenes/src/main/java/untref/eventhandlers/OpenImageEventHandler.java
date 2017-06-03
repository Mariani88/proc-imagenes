package untref.eventhandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import untref.controllers.RawImage;
import untref.controllers.nodeutils.ImageSetter;
import untref.controllers.nodeutils.settertype.SetterType;
import untref.service.ImageIOService;

import java.util.Optional;

public class OpenImageEventHandler implements EventHandler<ActionEvent> {

	private FileChooser fileChooser;
	private ImageView imageView;
	private ImageIOService imageIOService;
	private final SetterType setterType;
	private RawImage rawImage;

	public OpenImageEventHandler(FileChooser fileChooser, ImageView imageView, ImageIOService imageIOService, RawImage rawImage,
			SetterType setterType) {
		this.fileChooser = fileChooser;
		this.imageView = imageView;
		this.imageIOService = imageIOService;
		this.rawImage = rawImage;
		this.setterType = setterType;
	}

	public void handle(ActionEvent event) {
		Optional<Image> image = imageIOService.openImage(fileChooser, rawImage);
		image.ifPresent(image1 -> setterType.setImage(imageView, image1));
	}
}