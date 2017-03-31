package untref.service;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import untref.repository.ImageRepository;

import java.io.File;

public class ImageIOServiceImpl implements ImageIOService {
	private ImageRepository imageRepository;

	public ImageIOServiceImpl(ImageRepository imageRepository) {
		this.imageRepository = imageRepository;
	}

	@Override
	public Image openImage(FileChooser fileChooser) {
		File file = fileChooser.showOpenDialog(null);
		return imageRepository.findImage(file);
	}
}
