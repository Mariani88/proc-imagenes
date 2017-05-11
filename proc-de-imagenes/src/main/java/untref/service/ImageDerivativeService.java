package untref.service;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import untref.domain.TemporalColor;

public interface ImageDerivativeService {
	TemporalColor calculateDerivative(Image image, int row, int column, PixelReader pixelReader, int prewittOperator[][], int offsetI, int offsetJ);

	TemporalColor[][] calculateLaplacian(Image image, int width, int height);
}