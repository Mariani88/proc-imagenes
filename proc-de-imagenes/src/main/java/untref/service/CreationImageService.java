package untref.service;

import javafx.scene.image.Image;

public interface CreationImageService {

	Image createBinaryImageWithCenterQuadrate(int width, int height);

	Image createBinaryImageWithCenterCircle(int width, int height);

	Image createImageWithGrayDegree(int width, int height);
	
	Image createImageWithColorDegree(int width, int height);
}
