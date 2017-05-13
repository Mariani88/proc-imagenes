package untref.service;

import javafx.scene.image.Image;
import untref.domain.ThresholdingResult;

public interface ThresholdingService {
	Image getImageThreshold(Image image, int valueThreshold);

	ThresholdingResult obtainThresholdByBasicGlobalMethod(Image image, Double initialThreshold, Double deltaThreshold);
}
