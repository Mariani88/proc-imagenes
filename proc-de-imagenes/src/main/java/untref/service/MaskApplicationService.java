package untref.service;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import untref.domain.TemporalColor;

public interface MaskApplicationService {

	TemporalColor applyMask(Image image, int row, int column, PixelReader pixelReader, int prewittOperator[][], int offsetI, int offsetJ);
}