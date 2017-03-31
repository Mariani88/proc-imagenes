package untref.service;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;

public interface ImageIOService {
	Image openImage(FileChooser fileChooser);

	void saveImage(FileChooser fileChooser, Image image);
}
