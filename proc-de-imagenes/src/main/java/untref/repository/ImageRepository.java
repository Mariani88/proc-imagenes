package untref.repository;

import javafx.scene.image.Image;
import untref.controllers.RawImage;

import java.io.File;

public interface ImageRepository {
	Image findImage(File file,RawImage rawImage);

	Image findImageWithFormat(File file);

	void storeImage(Image image, File file);
}
