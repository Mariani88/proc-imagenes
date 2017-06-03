package untref.service;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import untref.domain.GrayScaleFunctionsContainer;
import untref.domain.TemporalColor;



public interface ImageArithmeticOperationService {

	Image plusImages(Image image, Image image2);

	Image subtractImages(Image image, Image image2);

	Image multiplyImages(Image image, Image image2);

	Image multiplyImageByScalar(double scalar, Image image);

	WritableImage parseToImageWithNoise(TemporalColor[][] temporalImageData, int maxWidth, int maxHeight,
			GrayScaleFunctionsContainer grayScaleFunctionsContainer);

	GrayScaleFunctionsContainer obtainFunctionsForExceededRGB(TemporalColor[][] temporalImageData);
}