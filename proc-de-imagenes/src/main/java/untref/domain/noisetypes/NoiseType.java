package untref.domain.noisetypes;

import javafx.scene.image.Image;
import untref.domain.TemporalColor;
import untref.service.ImageArithmeticOperationService;

public interface NoiseType {

	TemporalColor[][] initializeNoiseMatrix(int width, int height);

	Image applyNoise(Image image, TemporalColor[][] noiseMatrix, ImageArithmeticOperationService imageArithmeticOperationService);
}
