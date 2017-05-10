package untref.service;

import javafx.scene.image.Image;
import untref.domain.TemporalColor;

public interface ImageDerivativeService {
	TemporalColor[][] calculateLaplacian(Image image, int width, int height);

	TemporalColor[][] calculateConvolution(int[][] gausseanLaplacianOperator, Image image, int width, int height);
}