package untref.repository;

import javafx.scene.image.Image;

import java.io.File;

public interface ImageRepository {
	Image findImage(File file);

	void storeImage(Image image, File file);
}
