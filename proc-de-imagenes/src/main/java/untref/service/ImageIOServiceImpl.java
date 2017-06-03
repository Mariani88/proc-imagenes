package untref.service;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import untref.controllers.RawImage;
import untref.repository.ImageRepository;

import java.io.File;
import java.util.Optional;

public class ImageIOServiceImpl implements ImageIOService {
	private ImageRepository imageRepository;

	public ImageIOServiceImpl(ImageRepository imageRepository) {
		this.imageRepository = imageRepository;
	}

	@Override
	public Optional<Image> openImage(FileChooser fileChooser,RawImage rawImage) {
		Optional<File> file = Optional.ofNullable(fileChooser.showOpenDialog(null));
		return file.map(file1 -> imageRepository.findImage(file1,rawImage));
	}

	@Override
	public void saveImage(FileChooser fileChooser, Image image) {
		File file = fileChooser.showSaveDialog(null);
		imageRepository.storeImage(image, file);
	}
}