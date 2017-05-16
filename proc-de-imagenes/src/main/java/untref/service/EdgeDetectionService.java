package untref.service;

import javafx.scene.image.Image;
import untref.domain.edgedetectionoperators.firstderivative.EdgeDetectionFirstDerivativeOperator;
import untref.domain.edgedetectionoperators.secondderivative.detectors.EdgeDetector;

public interface EdgeDetectionService {

	Image detectEdgeWithFirstDerivative(Image image, EdgeDetectionFirstDerivativeOperator firstDerivativeOperator,
			Integer limitThresholdForGradientMagnitude);

	Image detectEdgeWithLaplacian(Image image, EdgeDetector edgeDetector);

	Image detectEdgeWithMarrHildreth(Image image, EdgeDetector edgeDetector);
}