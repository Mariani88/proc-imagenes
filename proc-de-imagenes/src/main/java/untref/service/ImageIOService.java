package untref.service;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import untref.controllers.RawImage;

import java.util.Optional;

public interface ImageIOService {
	Optional<Image> openImage(FileChooser fileChooser,RawImage rawImage);

	void saveImage(FileChooser fileChooser, Image image);
}
