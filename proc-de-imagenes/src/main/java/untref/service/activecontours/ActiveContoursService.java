package untref.service.activecontours;

import javafx.scene.image.Image;
import untref.domain.activecontours.Contour;
import untref.domain.ImagePosition;

public interface ActiveContoursService {
	Contour initializeActiveContours(Image image, ImagePosition imagePosition, ImagePosition imagePosition2);

	Contour adjustContours(Contour contour, Double colorDelta);

	Contour applyContourToNewImage(Contour contour, Image image);

	Contour adjustContoursAutomatically(Contour contour, Double colorDelta, Double reductionTolerance, int expandSize);
}
