package untref.service;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import java.util.Optional;

public interface ImageIOService {
	Optional<Image> openImage(FileChooser fileChooser);

	void saveImage(FileChooser fileChooser, Image image);
}
