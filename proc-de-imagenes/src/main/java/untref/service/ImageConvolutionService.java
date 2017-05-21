package untref.service;

import javafx.scene.image.Image;
import untref.domain.TemporalColor;

public interface ImageConvolutionService {
	TemporalColor[][] calculateConvolution(double[][] gausseanLaplacianOperator, Image image, int width, int height);


}
