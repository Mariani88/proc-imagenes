package untref.service;

import javafx.scene.image.Image;
import untref.domain.edgedetectionoperators.firstderivative.EdgeDetectionFirstDerivativeOperator;
import untref.domain.edgedetectionoperators.secondderivative.EdgeDetectionSecondDerivativeOperator;

public class EdgeDetectionServiceImpl implements EdgeDetectionService {

	@Override
	public Image detectEdge(Image image, EdgeDetectionFirstDerivativeOperator firstDerivativeOperator, Integer limitThresholdForGradientMagnitude) {
		return firstDerivativeOperator.detectEdge(image, limitThresholdForGradientMagnitude);
	}

	@Override
	public Image detectEdge(Image image, EdgeDetectionSecondDerivativeOperator secondDerivativeOperator) {
		return secondDerivativeOperator.detectEdges(image);
	}
}