package untref.service;

import javafx.scene.image.Image;
import untref.domain.edgedetectionoperators.firstderivative.EdgeDetectionFirstDerivativeOperator;
import untref.domain.edgedetectionoperators.secondderivative.EdgeDetectionSecondDerivativeOperator;
import untref.domain.edgedetectionoperators.secondderivative.LaplacianWithSlopeEvaluationOperator;

public interface EdgeDetectionService {

	Image detectEdge(Image image, EdgeDetectionFirstDerivativeOperator firstDerivativeOperator,
			Integer limitThresholdForGradientMagnitude);

	Image detectEdge(Image image, EdgeDetectionSecondDerivativeOperator secondDerivativeOperator);
}