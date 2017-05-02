package untref.service;

import javafx.scene.image.Image;
import untref.domain.TemporalColor;

public interface ImageStatisticService {
	TemporalColor calculateColorAverage(Image image);

	TemporalColor calculateColorStandardDeviation(Image image, TemporalColor colorAverage);
}
