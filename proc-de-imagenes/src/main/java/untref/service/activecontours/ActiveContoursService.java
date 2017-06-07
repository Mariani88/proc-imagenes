package untref.service.activecontours;

import javafx.scene.image.Image;
import untref.domain.Contour;
import untref.domain.ImagePosition;

public interface ActiveContoursService {
	Contour initializeActiveContours(Image image, ImagePosition imagePosition, ImagePosition imagePosition2);

	Contour adjustContours(Contour contour, Double colorDelta);
}
