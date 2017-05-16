package untref.service;

import javafx.scene.image.Image;
import untref.domain.GrayProbability;
import untref.domain.TemporalColor;

import java.util.Map;

public interface ImageStatisticService {
	TemporalColor calculateColorAverage(Image image);

	TemporalColor calculateColorStandardDeviation(Image image, TemporalColor colorAverage);

	Map<Integer, GrayProbability> calculateGrayProbabilityByGray(Image image);
}
