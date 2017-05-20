package untref.service;

import javafx.scene.image.Image;
import untref.domain.edge.edgedetectionoperators.firstderivative.EdgeDetectionFirstDerivativeOperator;
import untref.domain.edge.edgedetectionoperators.firstderivative.PrewittOperator;
import untref.domain.edge.edgedetectionoperators.secondderivative.detectors.EdgeDetector;

public interface EdgeDetectionService {

	Image detectEdgeWithFirstDerivative(Image image, EdgeDetectionFirstDerivativeOperator firstDerivativeOperator,
			Integer limitThresholdForGradientMagnitude);

	Image detectEdgeWithLaplacian(Image image, EdgeDetector edgeDetector);

	Image detectEdgeWithMarrHildreth(Image image, EdgeDetector edgeDetector);

	Image detectHorizontalEdgeWithFirstDerivative(Image image, PrewittOperator prewittOperator, Integer limitThresholdForGradientMagnitude);

	Image detectVerticalEdgeWithFirstDerivative(Image image, PrewittOperator prewittOperator, Integer limitThreshold);
}