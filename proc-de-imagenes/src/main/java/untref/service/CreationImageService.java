package untref.service;

import javafx.scene.image.Image;
import untref.service.colorbands.SpecificBand;

public interface CreationImageService {

	Image createBinaryImageWithCenterQuadrate(int width, int height);

	Image createBinaryImageWithCenterCircle(int width, int height);

	Image createImageWithGrayDegree(int width, int height);
	
	Image createImageWithColorDegree(int width, int height);

	Image createImageWithSpecificColorBand(Image image, SpecificBand specificBand);
}
