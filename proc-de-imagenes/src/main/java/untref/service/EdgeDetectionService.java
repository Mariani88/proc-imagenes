package untref.service;

import javafx.scene.image.Image;
import untref.domain.edgedetectionoperators.EdgeDetectionOperator;

public interface EdgeDetectionService {

	Image detectEdge(Image image, EdgeDetectionOperator edgeDetectionOperator, Integer limitThresholdForGradientMagnitude);
}