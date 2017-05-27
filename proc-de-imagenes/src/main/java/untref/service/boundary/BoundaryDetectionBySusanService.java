package untref.service.boundary;

import javafx.scene.image.Image;
import untref.domain.susan.ImageElementSusan;

public interface BoundaryDetectionBySusanService {

	Image detect(Image image, ImageElementSusan imageElementSusan, Integer threshold, Double accumulateDelta);
}
