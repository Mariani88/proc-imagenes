package untref.service;

import javafx.scene.image.Image;

public interface ImageArithmeticOperationService {

	Image plusImages(Image image, Image image2);

	Image subtractImages(Image image, Image image2);

	Image multiplyImages(Image image, Image image2);

	Image multiplyImageByScalar(double scalar, Image image);
}