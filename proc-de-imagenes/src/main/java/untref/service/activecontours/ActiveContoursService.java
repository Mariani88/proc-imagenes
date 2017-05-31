package untref.service.activecontours;

import javafx.scene.image.Image;
import untref.domain.ImagePosition;

public interface ActiveContoursService {
	Image initializeActiveContours(Image image, ImagePosition imagePosition, ImagePosition imagePosition2);
}
